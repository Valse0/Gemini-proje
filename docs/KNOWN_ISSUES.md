# Known Issues — Luma Translate

This list distinguishes **confirmed-by-review** issues (read in the source,
not run) from **confirmed-by-test** issues. Nothing here should be read as
"will be fixed automatically" — each needs its own scoped task.

As of this pass, this repository could not be built (Android or backend) in
the working environment: there was no network access to the Android SDK
distribution, Google's Maven repository, the Gradle plugin portal, or the
npm registry. Every item below marked "code review only" was found by
reading the source, not by compiling or running it. See
`docs/VERIFICATION_REPORT.md` for exactly what was and wasn't run.

## Highest priority

### 1. The end-to-end translation flow is not wired together

**Status:** confirmed by code review (pre-existing, not introduced this pass).

`android/app/src/main/java/com/luma/translate/pipeline/TranslationPipeline.kt`
and `ocr/OcrManager.kt` are fully implemented, with their own contract unit
tests (`PipelineContractTest.kt`, `OcrContractTest.kt`). But neither class is
ever instantiated anywhere in the app outside those tests:

```
grep -rln "TranslationPipeline(" android/app/src/main   # only the file itself
grep -rln "OcrManager(" android/app/src/main            # no results
```

`capture/CaptureService.kt`'s overlay-tap callback is a stub:

```kotlin
overlayManager = FloatingOverlayManager(this) {
    val bitmap = captureCurrentFrame()
    // To be implemented: trigger OCR and translation flow
}
```

**Effect:** tapping the floating overlay button currently does nothing.
Phase 5 ("End-to-end translation") and Phase 6 ("Floating overlay") are not
actually connected yet, even though their individual pieces (capture, OCR,
pipeline, overlay rendering) each exist and are individually testable.

**Recommended next step:** wire `CaptureService`'s capture callback to
`OcrManager.processImage()` → `TranslationPipeline.execute()`, then surface
the `PipelineResult` back through the overlay (or a result Activity/
Composable) instead of leaving it as a local variable. This is Phase 5/6
work per `docs/ROADMAP.md` and was intentionally not attempted blind in this
pass, since it cannot be verified without a working Android build
environment and a device.

### 2. Floating overlay used a ComposeView with no ViewTree owners

**Status:** fixed this pass by code review; **not verified by a build or
device test.**

`ui/overlay/FloatingOverlayManager.kt` added a `ComposeView` straight to
`WindowManager` from a `Service`. Per documented Compose behavior, a
`ComposeView` needs a `ViewTreeLifecycleOwner`, `ViewTreeViewModelStoreOwner`,
and `ViewTreeSavedStateRegistryOwner` attached before it can compose
content; a bare `Service` provides none of them, so this would have failed
at runtime (not at compile time) the first time Phase 6 was tested on a
device. A minimal `OverlayLifecycleOwner` was added and wired in. See
`CHANGELOG.md` for detail. **This needs a real device test** before being
treated as resolved — the fix is standard, documented Android/Compose
practice, but was written without a compiler available to confirm it builds.

### 3. `isCapturing` UI state is set optimistically, not from the actual result

**Status:** confirmed by code review (pre-existing, not introduced this pass).

In `MainActivity.kt`:

```kotlin
onStartCapture = { requestScreenCapture(); isCapturing = true },
```

`isCapturing` is set to `true` immediately when the user taps "start",
before the system's screen-capture consent dialog has even been answered.
If the user **denies** consent, `mediaProjectionLauncher`'s callback never
starts `CaptureService` (its `if (result.resultCode == RESULT_OK)` guard is
correct), but the UI still believes capture is active. A user who denies
the system dialog would see a UI stuck in a "capturing" state with no
actual capture running.

**Recommended next step:** move the `isCapturing = true` assignment inside
the `mediaProjectionLauncher` callback's `RESULT_OK` branch, and add an
explicit UI path for "user denied the system consent dialog" (distinct from
the app's own overlay-permission explanation).

## Medium priority

### 4. `TranslationPipeline` translates OCR blocks sequentially, not concurrently

**Status:** confirmed by code review.

`execute()` loops over `ocrResult.blocks` and calls the blocking
`translateTextViaBackend()` once per block, one at a time. For a screen with
many text regions (e.g. a game menu or manga page with several speech
bubbles), this means total latency scales linearly with the number of
regions instead of being bounded by the slowest single request. Not
incorrect, but worth measuring against the Phase 5/7 performance
requirements once the pipeline is actually wired in (see issue #1) — this
is real latency, not a correctness bug, so it was left as-is rather than
restructured blind.

### 5. `androidTest/` is referenced by Gradle but does not exist

**Status:** confirmed by review.

`app/build.gradle.kts` declares `androidTestImplementation` / `debugImplementation`
Compose UI test dependencies (Espresso, `ui-test-junit4`, `ui-test-manifest`),
and `docs/ROADMAP.md` Phase 1 calls for "UI tests or suitable Compose tests"
covering critical screen states, but there is no
`android/app/src/androidTest/` directory in the repository — only
`src/test/` (JVM unit tests) exists. The Gradle dependencies will resolve
and sit unused until on-device/instrumented UI tests are actually added.

### 6. `gemini-3.8-flash` model name could not be independently confirmed

**Status:** unverified, not changed.

`server/src/config.ts` and `server/src/services/gemini-provider.ts` both
hardcode `gemini-3.8-flash` as the Gemini model id. A web search for this
exact model name did not return authoritative Google documentation
confirming it as a real, current, generally-available model id (as opposed
to, for instance, a typo of a differently-numbered model). This was **not**
changed, per the "do not invent versions or claim compatibility without
verification" rule in `AGENTS.md` — guessing a "corrected" model name would
be worse than leaving the existing, presumably deliberately chosen, value
in place. **Action needed:** confirm the exact model id against Google's
current Gemini API documentation before the next real-provider test (Phase
2 acceptance criterion: "Real-provider translation has been tested using
authorized credentials").

## Lower priority / informational

### 7. Root web app (`src/`, `package.json` name `react-example`)

Renamed the package name to `luma-translate-web-companion` (see
CHANGELOG). The components under `src/components/` (AcceptanceSuiteDashboard,
AndroidSimulator, EndToEndRunner, OcrVisualizer, ReleaseDashboard,
SettingsDashboard) were read at a structural level (file sizes, imports) but
not reviewed line-by-line in this pass, per the mission's explicit
instruction to treat this app as a diagnostic companion and not spend
substantial effort redesigning it. No changes were made to its component
code.

### 8. CI build is unverified end-to-end

The GitHub Actions workflow fix in this pass (see CHANGELOG) addresses the
specific, logged root cause of run #21 (`sdkmanager --licenses` failing
inside `android-actions/setup-android@v3`) by using the Android SDK already
present on GitHub's `ubuntu-latest` runner image instead. This could not be
exercised in this environment (no access to GitHub Actions or any CI
runner). **The next push to `main`/`master`, or a manual
`workflow_dispatch` run, is required to confirm the fix actually produces a
green build.**

## Already-good, explicitly checked

To avoid re-litigating these in a future pass:

- No hardcoded API keys, secrets, or credential-shaped strings were found
  anywhere in tracked source (`grep` scan of `.ts`, `.kt`, `.json`,
  `.properties`, `.xml` for key/secret-shaped patterns — see
  `docs/VERIFICATION_REPORT.md`).
- No `.env` files are tracked by Git; `.env.example` contains only
  placeholder values.
- The backend never forwards raw provider errors or stack traces to
  clients (`routes/translate.ts` catches and normalizes all provider
  errors into a generic `TRANSLATION_FAILED` / 502 response).
- The backend's request validation (`middleware/validation.ts`) rejects
  empty text, oversized text, and malformed language codes before any
  provider call.
- The Gradle wrapper jar and scripts are present and look structurally
  intact (valid zip, executable `gradlew`); the project's declared
  `distributionUrl` points at Gradle 8.6, which is compatible with the
  declared AGP 8.4.2 / Kotlin 2.0.0.
