# Build and Release — Luma Translate

> **Update (on-device architecture):** the Android app no longer uses a
> backend or any remote translation provider. Translation is performed
> on-device with Google ML Kit; language packs are optional, consent-based
> downloads. Sections below that describe a backend, Gemini, provider keys,
> or server-side rate limits apply only to the legacy `server/` project and
> are not part of the shipped app. Current behavior: `docs/MODELS.md`,
> `docs/PRIVACY.md` (top section), `docs/analysis/`.


## 1. Release principle

Release only a build whose tested behavior matches its product description.

A successful build does not prove that screen capture, OCR, translation,
overlay permissions, privacy behavior, or translation quality work
correctly.

## 2. Release prerequisites

Before preparing a release candidate:

- Complete mandatory acceptance criteria in `docs/ROADMAP.md`.
- Run relevant tests from `docs/TESTING.md`.
- Review privacy and security requirements.
- Test the core workflow on a physical Android device.
- Record known limitations.
- Verify that public claims match the implementation.

## 3. Backend preparation

The backend must have:

- Production environment configuration
- Required provider credentials configured outside Git
- HTTPS enabled
- Request validation and size limits
- Timeouts and rate limiting
- Safe error responses
- Logs that exclude translation content and secrets
- A documented procedure to disable translation if abuse or cost issues occur

Do not launch an unrestricted paid translation endpoint.

## 3b. Debug signing identity and update compatibility (read before installing a new debug build on a device that already has one)

A committed, shared `android/app/debug.keystore` pins every debug build —
local or CI — to one stable signing identity (`storePassword`/`keyPassword`
= `android`, `keyAlias` = `androiddebugkey`, the same conventional
placeholder credentials Android's own implicit default debug config uses).
This guarantees that **from the point this keystore was introduced onward**,
every new debug build can be installed directly over the previous one
without uninstalling first — that is the whole point of the fix (see
`docs/AGENT_PROGRESS.md`, Bug 1).

**What this does NOT do:** retroactively match whatever certificate is
currently on a device that already has an older debug build installed.
Before this fix, the project had no explicit `signingConfigs`, so every CI
run signed its debug APK with a different, randomly auto-generated
certificate that was never saved anywhere — it existed only in that one
GitHub Actions runner's ephemeral filesystem and was discarded when the
runner was torn down. There is no way to recover or reconstruct it. If a
build signed with this newly-committed `debug.keystore` is installed over
an existing installation signed with one of those old, lost, random
certificates, Android will still refuse the install
(`INSTALL_FAILED_UPDATE_INCOMPATIBLE`) **once**, and the device will need
its current Luma Translate (debug) install removed manually that one time.
Every install after that — as long as `android/app/debug.keystore` stays
committed and unchanged — will update cleanly in place.

Do not reinterpret this as "the fix didn't work." It is a one-time,
unavoidable consequence of the previous certificate having been randomly
generated and never persisted; it is not a sign that the new, pinned
keystore is itself unstable.

The application ID for debug builds is `com.luma.translate.debug`
(`applicationIdSuffix = ".debug"` in `android/app/build.gradle.kts`, unchanged
by this pass) — distinct from the release application ID,
`com.luma.translate`. Both continue to use the same package name as before;
neither was changed by this fix.

**To check in advance whether a given device will need that one-time
reinstall:** compare the committed keystore's certificate against whatever
is currently installed.

```
# This repository's committed debug.keystore (fixed, will not change
# unless the file itself is replaced):
keytool -list -v -keystore android/app/debug.keystore -storepass android
#  SHA256: CE:C4:AE:DE:E8:DA:20:D7:D2:83:FC:8A:AF:C7:DD:FB:18:EF:84:5A:E6:F4:6D:10:43:6D:04:D5:7E:88:1E:C6

# The certificate actually signing the APK currently on a device (requires
# adb and the app's APK pulled from the device, or `apksigner verify
# --print-certs` on a copy of the installed APK):
adb shell pm path com.luma.translate.debug
adb pull <path-from-above> /tmp/installed.apk
apksigner verify --print-certs /tmp/installed.apk
```

If the SHA256 fingerprints differ, the next install from this repository
will require removing the existing app first (one time only, per the
explanation above). If they already match, it will update in place
immediately.

## 4. Android release build

From `android/`:

```bash
./gradlew test
./gradlew assembleDebug
```

For a release candidate, configure signing securely and run:

```bash
./gradlew assembleRelease
```

The release build may require signing configuration that is not present in
a fresh repository. Follow the project's actual Gradle configuration. Never
commit private signing keys or passwords.

## 5. APK verification

Before distribution:

- Confirm the APK was generated by the expected build.
- Verify the package name and version.
- Install it on the target Android test device.
- Test the core translation workflow.
- Review the merged manifest and requested permissions.
- Check that the APK does not contain provider credentials.
- Measure the APK file size.
- Record the Android versions and devices tested.
- Confirm the backend URL points to the intended environment.

Do not publish an APK with a development endpoint or test credentials.

## 6. Installation and upgrade checks

Test:

- Fresh installation
- First launch
- Permission requests
- Permission denial and recovery
- Core translation
- Network unavailable
- App restart
- Upgrade from the previous test build when applicable
- Uninstallation

Document any behavior that differs by Android version.

## 7. Privacy and claims review

Before release, confirm that:

- Ordinary translation does not upload screenshots.
- Recognized text is sent only as required for translation.
- Provider data handling has been reviewed.
- Logs do not contain translation text by default.
- Any optional persistent storage is documented.
- The privacy explanation reflects actual data flow.
- Marketing claims are supported by verified behavior.

Approved slogan direction:

- "Your Screen. Your Language. Your Privacy."
- "Understand more. Wait less."

These slogans must not be accompanied by unsupported guarantees such as
perfect accuracy, zero retention everywhere, or complete offline
translation.

## 8. Release notes

Every release should include:

- Version
- Main changes
- Tested devices or Android versions
- Known limitations
- Permission requirements
- Network requirements
- Any material privacy changes

## 9. Release decision

Do not release if a known critical issue affects:

- User consent
- Secret protection
- Unexpected screenshot transmission
- Translation request handling
- Core app stability
- Required permissions or capture lifecycle

A blocked release is preferable to a misleading or unsafe release.
