package com.luma.translate.capture

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.luma.translate.MainActivity
import com.luma.translate.ocr.OcrManager
import com.luma.translate.pipeline.PipelineResult
import com.luma.translate.pipeline.TranslationPipeline
import com.luma.translate.translation.SupportedLanguages
import com.luma.translate.ui.overlay.FloatingOverlayManager
import com.luma.translate.ui.overlay.OverlayUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Foreground Service managing Android MediaProjection screen capture.
 * Follows Android 14+ foreground service requirements (FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION)
 * and provides explicit user controls to stop capture at any time.
 */
class CaptureService : Service() {

    companion object {
        const val CHANNEL_ID = "luma_capture_channel"
        const val NOTIFICATION_ID = 2001
        const val ACTION_START_CAPTURE = "com.luma.translate.ACTION_START_CAPTURE"
        const val ACTION_STOP_CAPTURE = "com.luma.translate.ACTION_STOP_CAPTURE"
        const val EXTRA_RESULT_CODE = "extra_result_code"
        const val EXTRA_RESULT_DATA = "extra_result_data"
        const val EXTRA_WIDTH = "extra_width"
        const val EXTRA_HEIGHT = "extra_height"
        const val EXTRA_DPI = "extra_dpi"
        // Bug 2/5/6: previously MainActivity had nowhere to tell the service
        // which language pair the user picked, so the pipeline (once it
        // existed) would have had no way to honor the on-screen selection.
        const val EXTRA_SOURCE_LANGUAGE = "extra_source_language"
        const val EXTRA_TARGET_LANGUAGE = "extra_target_language"
        private const val TAG = "CaptureService"
    }

    private val binder = LocalBinder()
    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null
    private var imageReader: ImageReader? = null
    private var isCapturing = false
    private var overlayManager: FloatingOverlayManager? = null

    private var sourceLanguage: String = SupportedLanguages.DEFAULT_SOURCE_CODE
    private var targetLanguage: String = SupportedLanguages.DEFAULT_TARGET_CODE

    // Bug 5/6: the end-to-end pipeline that was previously never
    // instantiated anywhere in the app. OCR stays strictly on-device
    // (OcrManager, ML Kit); only recognized text + language codes go to the
    // backend (TranslationPipeline), per docs/PRIVACY.md.
    private val ocrManager = OcrManager()
    private val translationPipeline = TranslationPipeline(ocrManager)

    // Service-scoped coroutine scope for the pipeline's async work. Tied to
    // the service's own lifecycle (cancelled in stopCapture()/onDestroy()),
    // never leaked past a capture session.
    private var serviceJob: Job? = null
    private var serviceScope: CoroutineScope? = null

    inner class LocalBinder : Binder() {
        fun getService(): CaptureService = this@CaptureService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_CAPTURE -> {
                val resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, 0)
                val resultData = intent.getParcelableExtra<Intent>(EXTRA_RESULT_DATA)
                val width = intent.getIntExtra(EXTRA_WIDTH, 1080)
                val height = intent.getIntExtra(EXTRA_HEIGHT, 1920)
                val dpi = intent.getIntExtra(EXTRA_DPI, 400)
                sourceLanguage = intent.getStringExtra(EXTRA_SOURCE_LANGUAGE)
                    ?: SupportedLanguages.DEFAULT_SOURCE_CODE
                targetLanguage = intent.getStringExtra(EXTRA_TARGET_LANGUAGE)
                    ?: SupportedLanguages.DEFAULT_TARGET_CODE

                if (resultData != null) {
                    startForegroundWithNotification()
                    initMediaProjection(resultCode, resultData, width, height, dpi)
                }
            }
            ACTION_STOP_CAPTURE -> {
                stopCapture()
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Screen Translation Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows active screen capture status with stop control"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun startForegroundWithNotification() {
        val stopIntent = Intent(this, CaptureService::class.java).apply {
            action = ACTION_STOP_CAPTURE
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val openAppIntent = Intent(this, MainActivity::class.java)
        val openAppPendingIntent = PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(this)
        }

        val notification = notificationBuilder
            .setContentTitle("Luma Translate Active")
            .setContentText("Screen translation is ready. Tap overlay to translate.")
            .setSmallIcon(android.R.drawable.ic_menu_camera)
            .setContentIntent(openAppPendingIntent)
            .addAction(android.R.drawable.ic_delete, "Stop Capture", stopPendingIntent)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun initMediaProjection(
        resultCode: Int,
        resultData: Intent,
        width: Int,
        height: Int,
        dpi: Int
    ) {
        val projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        mediaProjection = projectionManager.getMediaProjection(resultCode, resultData)

        mediaProjection?.registerCallback(object : MediaProjection.Callback() {
            override fun onStop() {
                super.onStop()
                stopCapture()
            }
        }, null)

        imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2)
        virtualDisplay = mediaProjection?.createVirtualDisplay(
            "LumaScreenCapture",
            width,
            height,
            dpi,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            imageReader?.surface,
            null,
            null
        )
        
        val job = SupervisorJob()
        serviceJob = job
        serviceScope = CoroutineScope(Dispatchers.Main.immediate + job)

        overlayManager = FloatingOverlayManager(this) {
            runTranslationCycle()
        }
        overlayManager?.show()

        isCapturing = true
    }

    /**
     * Bug 5/6 fix: this is what the bubble tap now actually does -
     * previously `captureCurrentFrame()` was called and its result
     * discarded, so tapping the bubble had no observable effect at all,
     * which is exactly what was reported ("bubble tap does nothing" /
     * "app never translates anything").
     *
     * Full chain: grab the latest frame from the already-running
     * MediaProjection capture -> on-device ML Kit OCR
     * (translationPipeline/ocrManager) -> backend translation call -> real
     * result (or a real, specific error) shown in the overlay. No fake
     * success state is ever shown: every branch below reflects an actual
     * outcome of the pipeline.
     */
    private fun runTranslationCycle() {
        val manager = overlayManager ?: return
        val scope = serviceScope ?: return

        val bitmap = captureCurrentFrame()
        if (bitmap == null) {
            manager.updateState(
                OverlayUiState.Error("Couldn't capture the screen. Try again in a moment.")
            )
            return
        }

        manager.updateState(OverlayUiState.Processing)

        scope.launch {
            val result = try {
                translationPipeline.execute(
                    bitmap = bitmap,
                    sourceLanguage = sourceLanguage,
                    targetLanguage = targetLanguage
                )
            } catch (e: Exception) {
                Log.e(TAG, "Translation pipeline threw unexpectedly", e)
                PipelineResult.GeneralError(e)
            }

            // The overlay may have been torn down (stopCapture) while the
            // network/OCR work above was in flight; guard against updating
            // a manager instance that's no longer attached to a window.
            if (overlayManager !== manager) return@launch

            when (result) {
                is PipelineResult.Success -> manager.updateState(OverlayUiState.Success(result.regions))
                is PipelineResult.EmptyOcr -> manager.updateState(OverlayUiState.EmptyResult)
                is PipelineResult.NetworkError -> manager.updateState(
                    OverlayUiState.Error("Couldn't reach the translation backend: ${result.message}")
                )
                is PipelineResult.GeneralError -> manager.updateState(
                    OverlayUiState.Error(
                        result.error.message?.let { "Translation failed: $it" }
                            ?: "Translation failed unexpectedly."
                    )
                )
            }
        }
    }

    fun captureCurrentFrame(): Bitmap? {
        val image = imageReader?.acquireLatestImage() ?: return null
        return try {
            val planes = image.planes
            val buffer = planes[0].buffer
            val pixelStride = planes[0].pixelStride
            val rowStride = planes[0].rowStride
            val rowPadding = rowStride - pixelStride * image.width

            val bitmap = Bitmap.createBitmap(
                image.width + rowPadding / pixelStride,
                image.height,
                Bitmap.Config.ARGB_8888
            )
            bitmap.copyPixelsFromBuffer(buffer)
            bitmap
        } catch (e: Exception) {
            null
        } finally {
            image.close()
        }
    }

    fun stopCapture() {
        if (!isCapturing) return
        isCapturing = false

        serviceJob?.cancel()
        serviceJob = null
        serviceScope = null

        overlayManager?.remove()
        overlayManager = null

        virtualDisplay?.release()
        virtualDisplay = null

        imageReader?.close()
        imageReader = null

        mediaProjection?.stop()
        mediaProjection = null

        ocrManager.release()

        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onDestroy() {
        stopCapture()
        super.onDestroy()
    }
}
