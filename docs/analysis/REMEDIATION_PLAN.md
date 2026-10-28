# Remediation Plan

Order: build/install -> signing/version -> translation errors -> permissions/first launch -> stability/UX.

## Done in code (unverified on device; JVM tests written but not runnable offline here)
1. CI workflow: current action majors, hard-fail if debug APK missing, optional release keystore via job-level env.
2. `TranslationFailure.kt` + `TranslationPipeline`: classified failures, safe logging, fail-fast when URL is the emulator alias on a real device.
3. `PreferencesRepository.backendUrl` + Server settings dialog (HTTPS only in release; http allowed in debug builds).
4. `MainActivity`: POST_NOTIFICATIONS requested only after Start, once; no prompts at first launch; overlay and screen-capture flow unchanged.
5. `TranslationFailureTest`: classifier, URL validation, emulator alias.

## Needs the owner's approval / input
- Hosting the backend (HTTPS URL) – required for real translations.
- Uninstalling the old build on the phone (Hata3), or sharing adb/apksigner output for a precise diagnosis.
- Any release key creation, key replacement, or package-name change (none performed).
- Push/deploy (none performed).

## Open items
- BUG-08 capture size from display metrics; optional Quick Settings tile; permanent-denial deep link to app settings for overlay is handled by the system overlay screen, notification denial needs none.
- Run `./gradlew testDebugUnitTest assembleDebug` and `apksigner verify` where the network is available.
