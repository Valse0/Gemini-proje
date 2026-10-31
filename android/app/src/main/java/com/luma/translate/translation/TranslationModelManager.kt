package com.luma.translate.translation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.StatFs
import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.TranslatorOptions
import com.luma.translate.data.PreferencesRepository
import com.luma.translate.util.await
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Manages the on-device translation language packs (ML Kit Translation).
 *
 * Rules enforced here:
 *  - Nothing is downloaded unless a caller explicitly asks, and callers only
 *    ask after the user pressed "Download" (see ui/models/ModelDownloadDialog).
 *  - Downloads only fetch language packs; recognized text never leaves the
 *    device, before or after a download.
 *  - Failures (offline, metered network while Wi-Fi-only is on, low storage,
 *    interrupted download) end in [OpState.Failed] with a retryable message,
 *    never a crash.
 *
 * ML Kit's SDK does not expose a pack's size before or after download, so
 * this class cannot report one (see docs/MODELS.md).
 */
class TranslationModelManager(
    context: Context,
    private val prefs: PreferencesRepository
) {
    sealed class OpState {
        object Idle : OpState()
        data class Working(val message: String) : OpState()
        data class Failed(val message: String) : OpState()
    }

    private val appContext = context.applicationContext
    private val remote = RemoteModelManager.getInstance()
    private val lock = Mutex()

    private val _state = MutableStateFlow<OpState>(OpState.Idle)
    val state: StateFlow<OpState> = _state.asStateFlow()

    /** Luma language codes whose pack ML Kit reports as installed (as of the last [refresh]). */
    private val _installed = MutableStateFlow<Set<String>>(emptySet())
    val installed: StateFlow<Set<String>> = _installed.asStateFlow()

    fun clearFailure() {
        if (_state.value is OpState.Failed) _state.value = OpState.Idle
    }

    suspend fun refresh() {
        try {
            val models = remote.getDownloadedModels(TranslateRemoteModel::class.java).await()
            _installed.value = models.mapNotNull { MlKitLanguages.fromMlKit(it.language) }.toSet()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "Could not list language packs: ${e.javaClass.simpleName}")
        }
    }

    private fun pairKey(source: String, target: String) = "$source>$target"

    /**
     * True when the pair was downloaded with consent AND ML Kit still lists the
     * non-English pack(s). (English handling inside ML Kit isn't documented, so
     * it is covered by the consent-time download of the pair itself.)
     */
    suspend fun isPairReady(source: String, target: String): Boolean {
        if (source == target) return true
        if (pairKey(source, target) !in prefs.readyPairs) return false
        refresh()
        val needed = setOf(source, target) - "en"
        return _installed.value.containsAll(needed)
    }

    /** Downloads everything needed for [source] -> [target]. Call only after user consent. */
    suspend fun downloadPair(source: String, target: String, wifiOnly: Boolean): Boolean = lock.withLock {
        val src = MlKitLanguages.toMlKit(source)
        val tgt = MlKitLanguages.toMlKit(target)
        if (src == null || tgt == null) {
            _state.value = OpState.Failed("This language combination isn't supported.")
            return@withLock false
        }
        preflightProblem(wifiOnly)?.let { _state.value = OpState.Failed(it); return@withLock false }

        _state.value = OpState.Working("Downloading language packs...")
        val translator = Translation.getClient(
            TranslatorOptions.Builder().setSourceLanguage(src).setTargetLanguage(tgt).build()
        )
        try {
            translator.downloadModelIfNeeded(conditions(wifiOnly)).await()
            prefs.readyPairs = prefs.readyPairs + pairKey(source, target)
            refresh()
            _state.value = OpState.Idle
            true
        } catch (e: CancellationException) {
            _state.value = OpState.Idle
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "Pair download failed: ${e.javaClass.simpleName}")
            _state.value = OpState.Failed(DOWNLOAD_FAILED)
            false
        } finally {
            translator.close()
        }
    }

    /** Downloads one language pack (Settings). Call only after user consent. */
    suspend fun downloadLanguage(code: String, wifiOnly: Boolean): Boolean = lock.withLock {
        val ml = MlKitLanguages.toMlKit(code) ?: return@withLock false
        preflightProblem(wifiOnly)?.let { _state.value = OpState.Failed(it); return@withLock false }
        _state.value = OpState.Working("Downloading language pack...")
        try {
            remote.download(TranslateRemoteModel.Builder(ml).build(), conditions(wifiOnly)).await()
            refresh()
            _state.value = OpState.Idle
            true
        } catch (e: CancellationException) {
            _state.value = OpState.Idle
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "Language download failed: ${e.javaClass.simpleName}")
            _state.value = OpState.Failed(DOWNLOAD_FAILED)
            false
        }
    }

    /** Removes one language pack and invalidates every pair that depends on it. */
    suspend fun deleteLanguage(code: String): Boolean = lock.withLock {
        val ml = MlKitLanguages.toMlKit(code) ?: return@withLock false
        try {
            remote.deleteDownloadedModel(TranslateRemoteModel.Builder(ml).build()).await()
            // Removing English invalidates everything (it may be an intermediate language).
            prefs.readyPairs = if (code == "en") emptySet()
            else prefs.readyPairs.filterNot { code in it.split('>') }.toSet()
            refresh()
            _state.value = OpState.Idle
            true
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "Language removal failed: ${e.javaClass.simpleName}")
            _state.value = OpState.Failed("Couldn't remove the language pack. Please try again.")
            false
        }
    }

    private fun conditions(wifiOnly: Boolean): DownloadConditions =
        DownloadConditions.Builder().apply { if (wifiOnly) requireWifi() }.build()

    /** Returns a user-facing reason the download can't start right now, or null if it can. */
    private fun preflightProblem(wifiOnly: Boolean): String? {
        val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val caps = cm?.getNetworkCapabilities(cm.activeNetwork)
        if (caps == null || !caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            return "No internet connection. Connect to the internet to download the language pack. " +
                "Translation itself works offline once it's installed."
        }
        if (wifiOnly && !caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)) {
            return "Waiting for Wi-Fi: downloads are set to Wi-Fi only. Connect to Wi-Fi, or allow " +
                "mobile data for this download."
        }
        val free = try {
            StatFs(appContext.filesDir.absolutePath).availableBytes
        } catch (e: Exception) {
            Long.MAX_VALUE
        }
        if (free < MIN_FREE_BYTES) {
            return "Not enough free storage on this device. Free up some space and try again."
        }
        return null
    }

    private companion object {
        const val TAG = "TranslationModels"
        // A generic safety margin for free space, NOT the size of any model
        // (ML Kit doesn't expose model sizes).
        const val MIN_FREE_BYTES = 150L * 1024 * 1024
        const val DOWNLOAD_FAILED =
            "The download didn't finish. Check your connection and storage, then try again."
    }
}
