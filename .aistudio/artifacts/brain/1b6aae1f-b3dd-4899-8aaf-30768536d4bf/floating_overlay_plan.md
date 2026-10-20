# Luma Translate Floating Overlay Implementation Plan

The current implementation initiates screen capture but lacks the UI to trigger the capture-and-translate cycle *on-demand* while the user is inside other applications.

## Plan

1.  **Overlay Window Service**: Create a new Foreground Service (or integrate into `CaptureService`) that uses `WindowManager` to display a floating view (bubble) over other applications.
2.  **Service Lifecycle**: 
    - Start the floating bubble when `CaptureService` is initialized (after user grants MediaProjection permission).
    - Remove the bubble when `CaptureService` is stopped.
3.  **UI Component**: Create a minimal Compose-based `OverlayView` that displays the "Luma" button.
4.  **Interaction Flow**:
    - User taps floating bubble -> Bubble triggers `CaptureService.captureCurrentFrame()` -> Service sends bitmap to API -> Service displays translation result overlay.
5.  **Permissions**: Ensure `SYSTEM_ALERT_WINDOW` is properly requested and utilized.

---

## 2. Technical Implementation Detail

```text
┌───────────────────────┐
│     Overlay Service   │◄──────┐
├───────────────────────┤       │
│ • WindowManager       │       │
│ • ComposeView         │       │
└───────────┬───────────┘       │
            │                   │
            ▼                   │
┌───────────────────────┐       │
│   Floating Bubble UI  ├───────┘
└───────────┬───────────┘
            │ (User Tap)
            ▼
┌───────────────────────┐
│    CaptureService     │
├───────────────────────┤
│ • captureFrame()      │
└───────────┬───────────┘
            │
            ▼
┌───────────────────────┐
│     Backend API       │
└───────────────────────┘
```
