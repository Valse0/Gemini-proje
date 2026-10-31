# Translation Engine and Language Packs

## Engine
Google **ML Kit Translation** (`com.google.mlkit:translate:17.0.3`), running on-device.
OCR uses ML Kit text recognition (Latin, Japanese, Korean, Chinese recognizers).
No backend, API key, account or paid API is involved in the core flow.

## Evaluation (what was verified vs. not)
| Item | Result | Source |
|---|---|---|
| ja, ko, zh, en, es, tr are in ML Kit's supported-language table | Verified | developers.google.com/ml-kit/language/translation/translation-language-support |
| Text is processed on-device, not sent to Google servers | Stated by Google's ML Kit terms/privacy section | developers.google.com/ml-kit/terms |
| Language packs are downloaded on demand, can be listed/deleted via `RemoteModelManager` | Verified in Google's guide | developers.google.com/ml-kit/language/translation/android |
| Pack size | Guide says "around 30MB" per language; the SDK exposes **no** size API | same guide |
| Non-English pairs (e.g. ja -> tr) use English as an intermediate language | **Not verified** (not stated in the guide). The app does not rely on it: it downloads via `Translator.downloadModelIfNeeded` for the chosen pair. |
| Translation quality for ja/ko/zh | **Not tested** (needs real examples on a device) |
| Commercial-use terms | The terms page fetched does not state pricing or attribution rules. Owner must confirm before monetizing. |

### Larger optional model - not implemented
The brief allows a larger optional model "if it offers a meaningful quality improvement".
No such model was added: it would need a separate runtime (e.g. ONNX/CTranslate2), a download host, per-model
license review (some well-known multilingual models are non-commercial) and size metadata, and no quality
comparison could be run in this environment. The consent/Settings flow is generic enough to be extended later.

## Supported pairs (UI)
Source (OCR + translation): Japanese, Korean, Chinese (Simplified), English.
Target: English, Spanish, Turkish. Any source x target combination is offered. Same-language selection passes text through.
Language *support* is verified from Google's table; per-pair behavior on a device is untested.

## Optional download process
1. Nothing downloads at install or first launch.
2. When the user taps **Start** (or "Download language packs") for a pair that isn't installed, a dialog explains the
   benefit, that it is optional, that screen text is not uploaded, the size statement above, and a Wi-Fi-only switch.
3. **Download** starts it; **Not Now** dismisses (a session is not started because there is no other engine).
4. Before starting: checks internet, Wi-Fi-only (unmetered network), and free storage (150 MB safety margin - not a model size).
5. Failures show a retryable message; interrupted downloads can be retried (ML Kit resumes/rewrites as it sees fit).
6. Settings -> "Offline language packs": per-language Download / Remove, Wi-Fi-only switch, installed status.
7. Removing a pack invalidates every pair that depends on it (removing English invalidates all).

Storage is managed by ML Kit in app-private storage; it is removed when the app is uninstalled or via Remove.
Pack updates are handled by ML Kit; Luma has no separate update UI.

## Known limitation vs. the brief
"Display its actual download size from reliable metadata" cannot be met with ML Kit (no size API). The UI shows
Google's documented figure and states that the exact size isn't known beforehand. An alternative engine that
publishes sizes would be needed to fully meet that requirement.
