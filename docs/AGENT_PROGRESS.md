# Agent Progress Log — Critical Bug Fix Pass

**Purpose of this file:** this work session may be interrupted mid-task
(quota/session limits). This file is the single source of truth for what
has actually been done, so a continuation — by me in a later session, or by
another agent/developer — can resume without re-auditing everything from
scratch or losing track of partial work. Update this file as each task
below changes state. Do not rely on chat history alone to know what's done;
rely on this file plus `git status`/`git diff` in the working copy.

**Working copy:** `/home/claude/valse0/gemini-proje` (cloned from
`https://github.com/Valse0/gemini-proje`, content currently matches
`origin/main` commit `364a0de` plus the changes logged below).

**Environment constraint (unchanged from the previous pass, re-confirmed):**
this sandbox has no network access to the Android SDK, Google/Maven Central,
the Gradle Plugin Portal, or the npm registry. No Android or backend build
can be run here. Every Kotlin/TypeScript change below is a careful code
review and edit, not a compiled/tested change, unless stated otherwise.

## Task status

| # | Task | Status | Notes |
|---|------|--------|-------|
| 1 | Sync working copy with remote | ✅ done | origin/main already contained the previous ZIP's fixes (verified byte-identical, file by file) — someone pushed the ZIP I delivered last pass. Working tree reconciled without a destructive git op (classifier blocked `reset --hard`/`merge --ff-only`; worked around by directly restoring the two new lockfiles from origin/main instead, since content already matched everywhere else). |
| 2 | Bug 1 — update/upgrade crash | ✅ fixed (code review, not build-verified) | See findings below. |
| 3 | Bug 2 — language selection | ✅ implemented (code review, not build-verified) | See findings below. |
| 4 | Bug 3 — overlay permission return flow | ✅ fixed (code review, not build-verified) | See findings below. |
| 5 | Bug 4 — bubble redesign | ✅ implemented (code review, not build/device-verified) | See findings below. |
| 6 | Bug 5/6 — wire end-to-end pipeline | ✅ implemented (code review, not build/device-verified) | Root cause of both "bubble does nothing" and "never translates". See findings below — three independent root causes found and fixed. |
| 7 | APK size review | ⏳ not started | Do last, after functionality. |
| 8 | Final docs + ZIP | ⏳ in progress | This pass: AGENT_PROGRESS.md updated, SESSION_HANDOFF.md created. ZIP not yet produced this session. |

## Findings log (append-only; newest at bottom)

<!-- Each entry: date, bug #, what was found, what was changed, confidence/verification level -->

### Bug 1 — update/upgrade crash — DIAGNOSED AND FIXED (code review, not build-verified)

**Root cause (high confidence):** `android/app/build.gradle.kts` had no
`signingConfigs` block at all. The `debug` build type therefore fell back to
Gradle's *implicit* default debug config, which signs with
`~/.android/debug.keystore` — auto-generated fresh if it doesn't exist.
Every GitHub Actions run starts on a brand-new `ubuntu-latest` runner with no
cached `~/.android`, so **every CI-built debug APK was signed with a
different, randomly generated certificate.** Android's package manager
refuses to install an update whose signing certificate doesn't match the
already-installed app (`INSTALL_FAILED_UPDATE_INCOMPATIBLE`), which forces
an uninstall before the new build can go on — this matches the reported
symptom exactly.

Secondary contributing bug: `versionCode` fell back to a hardcoded `1` for
any build without a `BUILD_NUMBER` env var (i.e. any local/non-CI build), so
two local builds, or a local build after a CI build, could also collide or
look like a downgrade (`INSTALL_FAILED_VERSION_DOWNGRADE`).

**Fix:**
1. Generated `android/app/debug.keystore` (offline, via `keytool`, using
   Android's own conventional debug credentials — not a secret). Added an
   explicit `signingConfigs.sharedDebug` and wired it to the `debug` build
   type, so every debug build (local or CI) now shares one stable identity.
2. Added `!android/app/debug.keystore` to `.gitignore` so it survives the
   existing `*.keystore` exclusion (it's intentionally committed).
3. `versionCode` now falls back to `minutes-since-epoch` instead of `1` for
   non-CI builds, so it's always monotonically increasing.
4. Added an optional, secrets-driven `signingConfigs.release` (only active
   when `RELEASE_STORE_FILE` Gradle property is supplied) so a real release
   signing key — never committed — can be wired in via CI secrets later;
   defaults to an unsigned release build, which is the safe default.
5. Updated `.github/workflows/build-apk.yml`'s release step to optionally
   decode a `RELEASE_KEYSTORE_BASE64` repo secret and pass it through, and
   removed the previous `ORG_GRADLE_PROJECT_shouldSign: false` env var,
   which didn't correspond to any property the build scripts actually read
   (dead configuration).
6. Bonus (zero extra cost): turned on `isShrinkResources = true` alongside
   the already-enabled `isMinifyEnabled` for the release build type — also
   helps APK size (task 7).

**Verification:** NOT build-tested (no Android SDK / network in this
sandbox — see `docs/VERIFICATION_REPORT.md`). The keystore itself was
generated and verified locally with `keytool -list -v` (confirmed valid,
correct alias/DN). The Gradle Kotlin DSL syntax was written carefully and
matches standard, widely-documented `signingConfigs` patterns, but has not
been run through `./gradlew`. **This needs a real `./gradlew assembleDebug`
+ a two-step install-then-update test on a device or emulator before being
treated as fully confirmed.**

### Bug 2 — language selection — IMPLEMENTED (code review, not build-verified)

**Root cause:** `ui/home/HomeScreen.kt` had `sourceLanguage`/`targetLanguage`
as hardcoded `remember { mutableStateOf("Japanese (ja)") }` /
`"English (en)"` strings — plain, non-clickable `Text`, no selector, no
persistence. `settings/SettingsContract.kt`'s `SettingsManager` existed but
was never instantiated anywhere in the app. Checked
`ocr/LanguageRecognizerFactory.kt` first: it already has real, dedicated
ML Kit recognizers for Japanese, Korean, Chinese, and Latin script (covers
English/Spanish/Turkish) — so the OCR engine was never actually limited to
Japanese-only; the limitation was 100% in the UI layer never exposing or
using what already existed underneath.

**Implemented:**
1. `translation/SupportedLanguages.kt` (new) — single source of truth for
   the 4 source languages (ja/ko/zh/en) and 3 target languages (en/es/tr),
   used by both the UI and (once wired in Bug 5/6) the pipeline call, so
   "selecting a language changes actual processing behavior" is true by
   construction rather than by convention.
2. `data/PreferencesRepository.kt` (new) — real persistence via plain
   `SharedPreferences` (no new dependency) for the previously-missing
   "persist across restarts" requirement. `SettingsManager` was left
   untouched (its own JVM unit tests pass a plain in-memory contract;
   didn't want to risk breaking a working, tested class for an unrelated
   fix).
3. `ui/home/HomeScreen.kt` — language cards are now clickable and open a
   `LanguagePickerDialog` (scrollable list; no search field — 3-4 items
   doesn't warrant one). Added `isCapturing: Boolean` and
   `captureDeniedMessage: String?` params, driven from `MainActivity`
   instead of a second, locally-duplicated, equally-optimistic
   `SessionState` enum that this file previously had (see Bug 3 note
   below — same root-cause pattern as Bug 1's versionCode issue: state
   assumed success before the system confirmed it).
4. `MainActivity.kt` — loads/saves language codes via
   `PreferencesRepository`; passes them to `HomeScreen`.

**Deliberate scope decision (documented per the brief's "architectural
freedom" requirement):** did NOT implement an "Auto-detect source language"
option. ML Kit's on-device recognizers are script-specific, not
language-detecting, and Japanese/Chinese text can both produce plausible
*but wrong* output from either recognizer (shared Han characters). A fake
"Auto" option would silently guess rather than reliably detect, which
conflicts with the explicit instruction to make unsupported/unreliable
combinations honest rather than papered over. The backend's own
`sourceLanguage: "auto"` validation path remains available for future use
once a real detection strategy (e.g. trying multiple recognizers and
comparing confidence) is implemented and tested.

**Verification:** NOT build-tested. Reviewed for consistency (every call
site now reads language codes from `SupportedLanguages`/`PreferencesRepository`
rather than a separate hardcoded string); no existing test file covers this
new code yet (see remaining-work note at the bottom of this file).

### Bug 3 — overlay permission return flow — FIXED (code review, not build-verified)

**Root cause:** `overlayPermissionLauncher`'s result callback was a no-op
(`{ }`). Returning to the app DID work at the OS level (standard Android
`ActivityResultContract` behavior), but nothing then re-checked permission
state in response to that specific return — it only ever got re-checked by
a generic `ON_RESUME` lifecycle observer, and even once re-checked, nothing
advanced the setup flow to the next step (screen-capture consent). The user
had to manually tap the button a second time, which reads as "it didn't
actually bring me back into the flow."

**Fix (in `MainActivity.kt`):**
1. The overlay-permission launcher callback now actually does something: it
   re-checks `Settings.canDrawOverlays()` and, if permission was granted
   **and** we were the ones who just asked for it (tracked via
   `awaitingOverlayPermissionResult`, reset immediately after use),
   automatically calls `requestScreenCapture()` to continue the flow - this
   is still an explicit system consent dialog the user must approve, so it
   does not silently start capture.
2. The generic `ON_RESUME` observer is kept for the separate case of the
   user revoking overlay permission from system settings while
   backgrounded, but does NOT auto-continue the flow (only the explicit
   launcher callback does), so an ordinary app switch-back never triggers
   an unexpected permission prompt — no loop, no repeated prompt.

**Verification:** NOT build/device-tested. This is exactly the kind of fix
AGENTS.md calls out as needing a physical-device test
("Do not mark overlay... complete without testing the permission flow on
an actual Android device") — flagged as such, not claimed as confirmed.

### Bonus fix found while wiring Bug 2/3 — optimistic capture state (was in KNOWN_ISSUES #3 from the previous pass)

`isCapturing` in `MainActivity` (and a parallel, locally-duplicated
`SessionState.ACTIVE_OVERLAY` in `HomeScreen.kt`) were previously set to
"active" immediately on button tap, before the system's screen-capture
consent dialog was answered. Denying that dialog left the UI stuck showing
an active session with nothing running. Fixed: `isCapturing` is now a
single source of truth owned by `MainActivity`, only set `true` inside the
`mediaProjectionLauncher` callback's `RESULT_OK` branch, and a denial now
shows a real error message (`captureDeniedMessage`) in the UI instead of a
silently wrong "active" state. `HomeScreen.kt`'s redundant `SessionState`
enum was removed entirely in favor of this single real state.

### Bug 6 (networking sub-cause) — cleartext HTTP blocked by default — FIXED (code review, not build-verified)

**Root cause (high confidence, additional/independent from the wiring gap
below):** `TranslationPipeline.kt`'s backend call defaulted to
`http://10.0.2.2:3000` (plain HTTP), and the app had **no Android Network
Security Config at all**. Since API 28 (this project's `targetSdk` is 34),
Android blocks cleartext (non-TLS) traffic by default unless a network
security config explicitly allows it for the destination host. Even with
every other part of the pipeline working, every backend call would have
failed immediately with a `CleartextNotPermittedException` — invisible to
the user, indistinguishable from "the app never translates anything."

**Fix:**
1. `android/app/src/debug/res/xml/network_security_config.xml` (new) —
   allows cleartext to `10.0.2.2` / `localhost` / `127.0.0.1` for **debug
   builds only**.
2. `android/app/src/release/res/xml/network_security_config.xml` (new) —
   `cleartextTrafficPermitted="false"` (the secure platform default),
   required so release's resource merge has a same-named resource to use.
3. `AndroidManifest.xml` — added
   `android:networkSecurityConfig="@xml/network_security_config"` to
   `<application>`.
4. `app/build.gradle.kts` — added `buildFeatures { buildConfig = true }`
   and a `BuildConfig.BACKEND_BASE_URL` field (default
   `http://10.0.2.2:3000`, overridable via `-PLUMA_BACKEND_URL=...` or
   `android/local.properties`, git-ignored) so the backend host is no
   longer hardcoded and a real device / production build can point at an
   HTTPS backend without a code change.
5. `TranslationPipeline.kt` — now defaults to `BuildConfig.BACKEND_BASE_URL`
   instead of the hardcoded emulator-only string.

**Verification:** NOT build-tested (same sandbox constraint). The XML and
manifest changes follow the standard, widely-documented Android Network
Security Config pattern. **Needs a real device/emulator test**: confirm a
debug build can reach a local dev backend over HTTP, and confirm a release
build (or any build pointed at a `https://` URL) still works with TLS.

### Bug 5/6 (core) — end-to-end pipeline wiring — IMPLEMENTED (code review, not build/device-verified)

**Root cause (confirmed by reading the code, not inferred):**
`CaptureService.kt`'s `initMediaProjection()` created the floating overlay
with `FloatingOverlayManager(this) { val bitmap = captureCurrentFrame();
// To be implemented: trigger OCR and translation flow }` — the captured
bitmap was thrown away and nothing else happened. `OcrManager` and
`TranslationPipeline` both existed as complete, working classes but were
**never instantiated anywhere in the app**. This is the direct cause of
both "bubble tap does nothing" (Bug 5) and "app never translates anything"
(Bug 6) — independent of the networking cause above, and independent of
whether OCR itself works.

**Implemented:**
1. `CaptureService.kt` — now owns one `OcrManager` and one
   `TranslationPipeline(ocrManager)` instance per service lifetime, plus a
   service-scoped `CoroutineScope` (`SupervisorJob` + `Dispatchers.Main.immediate`,
   created in `initMediaProjection()`, cancelled in `stopCapture()`). Added
   `EXTRA_SOURCE_LANGUAGE` / `EXTRA_TARGET_LANGUAGE` intent extras (already
   referenced by `MainActivity.kt` from the Bug 2 pass, but not previously
   defined on the service side — this was a compile error waiting to
   happen) so the language pair the user picked on the Home screen actually
   reaches the pipeline call, closing the loop from Bug 2.
2. New `runTranslationCycle()`: on bubble tap, captures the current frame;
   if that fails, shows a real (not fake) error state immediately. Otherwise
   sets the bubble to a `Processing` state, then launches the pipeline
   (OCR -> backend translate) on the service's coroutine scope, and maps
   every real `PipelineResult` branch (`Success`, `EmptyOcr`, `NetworkError`,
   `GeneralError`) to a corresponding overlay state. A stale in-flight
   callback is guarded against via an identity check (`overlayManager !==
   manager`) in case `stopCapture()` ran while the network call was still
   outstanding.
3. `stopCapture()` now also cancels the service's `CoroutineScope` and
   releases the shared `OcrManager`'s ML Kit recognizer, so no coroutine or
   native OCR resource outlives a capture session.
4. **Explicitly did NOT add any fake/simulated success response or
   animation** — every overlay state transition in `runTranslationCycle()`
   is driven by a real return value from `TranslationPipeline.execute()` or
   a real caught exception, per the brief's explicit instruction.

**Bug 4 (bubble redesign) — bundled with this fix since they share the same
file (`FloatingOverlayManager.kt`) and the result display is literally part
of the redesign:**
- Rewrote `FloatingOverlayManager.kt` to introduce `sealed class
  OverlayUiState` (`Idle`, `Processing`, `EmptyResult`, `Success(regions)`,
  `Error(message)`) as Compose state (`mutableStateOf`) owned by the
  manager, with a public `updateState()` the service calls from outside
  Compose.
- Bubble shrunk from a 56dp bright-blue "LUMA" circle to a 48dp (meets the
  real Android touch-target minimum) tonal surface using the app's existing
  design tokens (`ui/theme/Color.kt`: Slate900/Slate800/Slate700 for
  idle/processing/empty, `EmeraldGreen` for success, `RoseRed` for error) —
  restrained rather than glowing/cartoonish, per AGENTS.md's UI rules and
  the bug report's own description of the old bubble as "oversized and
  artificial."
- Added a small, dismissible result panel (rounded surface, max 240dp wide
  / 280dp tall, scrollable `LazyColumn` for multiple OCR regions) that
  appears under the bubble only in `Success` / `Error` / `EmptyResult`
  states, showing the actual original -> translated text per region (or the
  actual error message, or a real "no text recognized" message — never a
  placeholder). Tapping the bubble or the panel while it's showing dismisses
  it back to `Idle`.
- Deliberately did NOT add `AnimatedVisibility`/fade transitions: that API
  lives in the separate `androidx.compose.animation` artifact, which isn't
  currently a project dependency, and AGENTS.md says not to add a
  dependency without a clear need — a plain conditional composable is used
  instead.

**Verification:** NOT build/device-tested. `./gradlew :app:compileDebugKotlin
--offline` was attempted this session (both via the wrapper and the
preinstalled system Gradle 8.14.3) and fails before reaching any Kotlin
compilation, because AGP 8.4.2 cannot be resolved without network access to
Google's Maven repository (same sandbox constraint as every previous pass —
see docs/VERIFICATION_REPORT.md for the full environment probe). All
changes in this entry were verified by careful manual reading only:
confirmed import correctness, confirmed every referenced symbol
(`PipelineResult.*`, `OverlayUiState.*`, `BuildConfig.BACKEND_BASE_URL`,
`CaptureService.EXTRA_SOURCE_LANGUAGE`/`EXTRA_TARGET_LANGUAGE`) is actually
defined somewhere in the tree, and re-read every edited file in full after
editing. **This is the single most important thing to verify on a real
device before trusting this app**: install a debug build, grant both
permissions, point the bubble at on-screen text, tap it, and confirm a real
translated result appears (with a local/dev backend running and reachable,
or `LUMA_BACKEND_URL` pointed at a reachable one).
