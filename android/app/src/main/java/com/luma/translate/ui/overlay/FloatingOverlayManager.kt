package com.luma.translate.ui.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.luma.translate.pipeline.TranslatedRegion
import com.luma.translate.ui.theme.EmeraldGreen
import com.luma.translate.ui.theme.RoseRed
import com.luma.translate.ui.theme.Slate100
import com.luma.translate.ui.theme.Slate700
import com.luma.translate.ui.theme.Slate800
import com.luma.translate.ui.theme.Slate900

/**
 * Minimal Lifecycle/ViewModelStore/SavedStateRegistry owner for a [ComposeView]
 * that is attached directly to [WindowManager] from a Service context.
 *
 * A Service has none of these owners by default. Without providing them,
 * adding a ComposeView via WindowManager.addView crashes as soon as Compose
 * tries to read composition locals, with an error such as
 * "ViewTreeLifecycleOwner not found from <ComposeView>". This was an
 * unverified correctness gap in the previous implementation: the overlay
 * code built, but per documented Compose/WindowManager requirements it would
 * fail at runtime before ever showing the floating control.
 */
private class OverlayLifecycleOwner :
    LifecycleOwner,
    ViewModelStoreOwner,
    SavedStateRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    private val store = ViewModelStore()

    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val viewModelStore: ViewModelStore get() = store
    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    fun start() {
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    fun stop() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        store.clear()
    }
}

/**
 * Visible state of the floating bubble, driven by [CaptureService] as it
 * runs OCR -> translation. Bug 4/5/6 fix: previously the bubble had exactly
 * one visual state and tapping it did nothing observable (the `onTrigger`
 * callback body was a stub with no UI feedback at all, and no result was
 * ever shown anywhere). Now every stage of the pipeline is reflected here.
 */
sealed class OverlayUiState {
    object Idle : OverlayUiState()
    object Processing : OverlayUiState()
    object EmptyResult : OverlayUiState()
    data class Success(val regions: List<TranslatedRegion>) : OverlayUiState()
    data class Error(val message: String) : OverlayUiState()
}

class FloatingOverlayManager(private val context: Context, private val onTrigger: () -> Unit) {
    private var windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var overlayView: View? = null
    private var lifecycleOwner: OverlayLifecycleOwner? = null

    // Backing state for the Compose content below. Mutating this from
    // outside Compose (CaptureService, on a background dispatcher result)
    // triggers recomposition of the already-attached overlay window, which
    // is how the bubble's visuals and result panel update live instead of
    // only reacting to the next show()/remove() cycle.
    private val uiState = mutableStateOf<OverlayUiState>(OverlayUiState.Idle)

    fun show() {
        if (overlayView != null) return

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 24
            y = 160
        }

        val owner = OverlayLifecycleOwner().also { it.start() }
        lifecycleOwner = owner

        val composeView = ComposeView(context).apply {
            setViewTreeLifecycleOwner(owner)
            setViewTreeViewModelStoreOwner(owner)
            setViewTreeSavedStateRegistryOwner(owner)
            setContent {
                val state by uiState
                OverlayContent(
                    state = state,
                    onBubbleClick = {
                        // Tapping while a result/error panel is showing
                        // dismisses it back to idle instead of re-triggering
                        // a brand-new capture (the panel's own tap target is
                        // smaller than the whole overlay, so this remains
                        // reachable either way).
                        if (state is OverlayUiState.Success || state is OverlayUiState.Error || state is OverlayUiState.EmptyResult) {
                            uiState.value = OverlayUiState.Idle
                        } else {
                            onTrigger()
                        }
                    },
                    onDismiss = { uiState.value = OverlayUiState.Idle }
                )
            }
        }

        overlayView = composeView
        windowManager.addView(overlayView, params)
    }

    /** Called by CaptureService as the pipeline progresses. */
    fun updateState(state: OverlayUiState) {
        uiState.value = state
    }

    fun remove() {
        overlayView?.let {
            windowManager.removeView(it)
            overlayView = null
        }
        lifecycleOwner?.stop()
        lifecycleOwner = null
    }
}

@Composable
private fun OverlayContent(
    state: OverlayUiState,
    onBubbleClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(horizontalAlignment = Alignment.End) {
        OverlayBubble(state = state, onClick = onBubbleClick)

        // Plain conditional rather than AnimatedVisibility: the latter lives
        // in the separate androidx.compose.animation artifact, which isn't
        // currently a project dependency (see AGENTS.md "Avoid upgrading
        // unrelated dependencies" / "do not add a library without a clear
        // need" - not worth a new dependency for a show/hide transition).
        if (state is OverlayUiState.Success || state is OverlayUiState.Error || state is OverlayUiState.EmptyResult) {
            Box(modifier = Modifier.padding(top = 8.dp)) {
                ResultPanel(state = state, onDismiss = onDismiss)
            }
        }
    }
}

/**
 * The floating control itself. Deliberately restrained (48dp - a real
 * touch-target minimum, not the previous 56dp "glowing circle"): a flat
 * tonal surface with a small glyph/spinner rather than a bright gradient
 * blob, per AGENTS.md's "restrained glass effects... not excessive blur,
 * glow" rule and the bug report calling the old bubble "oversized and
 * artificial-looking."
 */
@Composable
private fun OverlayBubble(state: OverlayUiState, onClick: () -> Unit) {
    val backgroundColor = when (state) {
        is OverlayUiState.Idle -> Slate900.copy(alpha = 0.92f)
        is OverlayUiState.Processing -> Slate800.copy(alpha = 0.96f)
        is OverlayUiState.Success -> EmeraldGreen
        is OverlayUiState.EmptyResult -> Slate700
        is OverlayUiState.Error -> RoseRed
    }

    Surface(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        color = backgroundColor,
        shape = CircleShape,
        shadowElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            when (state) {
                is OverlayUiState.Processing -> CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
                is OverlayUiState.Success -> Text("OK", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                is OverlayUiState.EmptyResult -> Text("—", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                is OverlayUiState.Error -> Text("!", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                is OverlayUiState.Idle -> Text("文A", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

/**
 * Small card showing the actual pipeline result (or error) under the
 * bubble. Bug 5/6: previously there was no result surface at all - the
 * stub discarded the captured bitmap - so even a perfectly working pipeline
 * would have had nowhere to display its output. Tapping it (or the bubble
 * again) dismisses it.
 */
@Composable
private fun ResultPanel(state: OverlayUiState, onDismiss: () -> Unit) {
    Surface(
        modifier = Modifier
            .widthIn(max = 240.dp)
            .heightIn(max = 280.dp)
            .clickable { onDismiss() },
        color = Slate900.copy(alpha = 0.96f),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 6.dp
    ) {
        when (state) {
            is OverlayUiState.Success -> {
                if (state.regions.isEmpty()) {
                    EmptyResultBody()
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.regions) { region ->
                            Column {
                                Text(
                                    text = region.originalText,
                                    color = Slate100.copy(alpha = 0.6f),
                                    fontSize = 11.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = region.translatedText,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 4,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
            is OverlayUiState.EmptyResult -> EmptyResultBody()
            is OverlayUiState.Error -> {
                Row(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = state.message,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
            else -> Unit
        }
    }
}

@Composable
private fun EmptyResultBody() {
    Box(modifier = Modifier.padding(12.dp)) {
        Text(
            text = "No text recognized here. Point the overlay at a region with on-screen text and tap again.",
            color = Slate100,
            fontSize = 13.sp
        )
    }
}
