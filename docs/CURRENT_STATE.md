# Luma Translate - Current State

## Architecture
MediaProjection frame -> ML Kit OCR (on-device) -> ML Kit Translation (on-device) -> Compose overlay.
Packages: `capture/` (service, consent), `ocr/`, `translation/` (translator, language-pack manager),
`pipeline/` (orchestration, failure classes), `ui/` (home, settings, models dialog, overlay, privacy), `data/` (SharedPreferences), `util/`.
`server/` and the web app are legacy/dev-only and not used by the Android app.

## Permissions (all requested in context, never at first launch)
| Permission | When | If denied |
|---|---|---|
| SYSTEM_ALERT_WINDOW | Tapping the main button without it (explanation is shown first; opens system overlay settings) | Message + the same button retries |
| Screen-capture consent (MediaProjection) | After Start | Message; Start retries |
| POST_NOTIFICATIONS (Android 13+) | Once, right after Start, before capture consent | Session still starts; notification may be hidden |
| INTERNET / ACCESS_NETWORK_STATE | Normal permissions; used only for optional language-pack downloads | n/a |
| FOREGROUND_SERVICE(+_MEDIA_PROJECTION) | Normal permissions for the capture service | n/a |

## Privacy
See top of `docs/PRIVACY.md`. No text logging, no analytics, no accounts.

## Support / feedback
Settings -> "Support the Developer" (https://ko-fi.com/hepyek) and "Send Feedback" (https://tally.so/r/aO9QkW)
open in the external browser via `ACTION_VIEW`; a toast is shown if no browser exists. No WebView.

## Build (GitHub Actions only; no APK was built by the authoring agent)
Workflow `.github/workflows/build-apk.yml` (push to main/master or manual):
unit tests (non-blocking step) -> `assembleDebug` -> verify APK exists -> optional release build.
Artifacts: `Luma-Translate-Debug-APK` (`android/app/build/outputs/apk/debug/*.apk`),
`Luma-Translate-Release-APK` (`.../release/*.apk`, unsigned unless secrets are set),
`Luma-Translate-Release-AAB`, `unit-test-reports`, and `build-failure-log-N` on failure.
Local: `cd android && ./gradlew testDebugUnitTest assembleDebug` (needs network for dependencies).

## Signing and update compatibility
- applicationId `com.luma.translate` (release), `com.luma.translate.debug` (debug). Unchanged.
- Debug builds use the committed non-secret `android/app/debug.keystore` (stable identity across CI runs).
- Release signing uses secrets `RELEASE_KEYSTORE_BASE64`, `RELEASE_STORE_PASSWORD`, `RELEASE_KEY_ALIAS`,
  `RELEASE_KEY_PASSWORD`; without them the release APK is unsigned.
- versionCode = GitHub `run_number` in CI (local fallback: minutes since epoch - do not mix local and CI installs).
- **Continuity is not verified:** the key that signed the APK on the test device was never available to this
  environment. Compare `apksigner verify --print-certs` of the installed and new APKs before expecting an in-place update.
  No key was created, replaced or removed, and no uninstall is recommended as an automatic fix.

## Known limitations / remaining tasks
See `docs/KNOWN_ISSUES.md` and `docs/analysis/REMEDIATION_PLAN.md`. Nothing here was compiled or run on a device.
