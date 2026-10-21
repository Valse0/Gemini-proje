
---

## 6. `docs/ROADMAP.md`

Dosya yolu: `docs/ROADMAP.md`

Bu dosya geliştirme sırasını ve her fazın hangi koşulda tamamlanacağını belirler. Gemini’nin bütün projeyi tek seferde üretmeye çalışmasını önleyen en önemli belgelerden biri budur.

```markdown
# Roadmap and Phase Gates — Luma Translate

## General rules

Implement one phase at a time.

A phase is complete only when:

- Its acceptance criteria have been checked.
- Relevant tests have passed.
- The build succeeds.
- The final diff has been reviewed.
- Remaining limitations are documented.

Physical-device requirements cannot be satisfied by a successful VPS build.

Do not start the next phase when a mandatory acceptance criterion fails.
If a requirement depends on missing hardware or credentials, document the
blocker and complete only the independently testable work.

## Phase 0 — Repository and environment

### Goal

Establish a reproducible repository and verify the development environment.

### Tasks

- Create the Android project using Kotlin and Jetpack Compose.
- Create a minimal TypeScript backend project.
- Add `.gitignore`.
- Add project scripts and basic build instructions.
- Record actual tool versions.
- Confirm the project can be built without committing credentials.

### Commands

Inspect the environment:

```bash
git status --short
git --version
node --version
npm --version
java -version

Inspect Android SDK availability:

echo "$ANDROID_HOME"
echo "$ANDROID_SDK_ROOT"

After project setup, from android/:

./gradlew assembleDebug
./gradlew test

From server/:

npm ci
npm run build
npm test

If a command is not yet available because its script has not been created,
add it as part of this phase or document the exact blocker.
Acceptance criteria
Android project builds successfully.
Backend installs and builds successfully.
Required scripts exist and run.
No secrets are tracked by Git.
README build instructions match the repository.
Project versions and known environment constraints are documented.
Do not
Implement all product features at once.
Add a database or account system.
Add unrelated dependencies.
Claim device behavior has been tested.
Phase 1 — Premium UI foundation
Goal
Create a real, responsive application shell with a coherent visual system.
Tasks
Build the main Compose screen.
Establish typography, spacing, colors, surface styles, and shapes.
Create the primary translation action.
Create initial language controls.
Add clear empty, loading, and error states.
Add a privacy explanation screen or section.
Ensure all visible controls have defined behavior.
Acceptance criteria
Android debug build succeeds.
Main screen renders without crashes.
Layout works on the available test device and at different screen sizes.
Text remains readable over all backgrounds.
Loading, empty, and error states are distinguishable.
No decorative button falsely implies an implemented feature.
UI tests or suitable Compose tests cover the critical screen states.
Device checks
Open and close the app repeatedly.
Check keyboard and system-bar insets where applicable.
Check small-screen layout and touch target usability.
Check dark and light appearance if both are supported.
Phase 2 — Backend translation contract
Goal
Implement a secure translation API with a replaceable provider interface.
Tasks
Add POST /v1/translate.
Validate request fields and language codes.
Add a configurable text-length limit.
Add provider timeouts and rate limiting.
Add a provider interface and the first working provider implementation.
Configure the Gemini API key only on the backend if Gemini is selected.
Normalize provider failures.
Add unit and API-level tests.
Provide a safe health endpoint if operationally useful.
Acceptance criteria
Valid requests return a documented response shape.
Empty and oversized text are rejected.
Unsupported language codes are rejected clearly.
Missing credentials fail safely.
Provider errors do not leak keys or stack traces.
Requests and responses do not log translation content by default.
Rate limits and timeouts are tested.
The provider can be replaced without changing the Android UI contract.
Real-provider translation has been tested using authorized credentials.
Commands
From server/:
npm ci
npm run build
npm test
Run a local request only after the server has been configured:
curl -sS -X POST http://127.0.0.1:3000/v1/translate \
  -H 'Content-Type: application/json' \
  -d '{"text":"こんにちは","sourceLanguage":"ja","targetLanguage":"en"}'
Do not use a production secret in shell history or paste credentials into
the repository.
Do not
Put the provider key in the Android application.
Build an unrestricted translation proxy.
Log raw requests for debugging.
Claim provider quality is proven by one successful example.
Phase 3 — On-device OCR
Goal
Recognize screen text locally before introducing capture automation.
Tasks
Integrate an appropriate on-device OCR implementation.
Support Japanese, Korean, Chinese, and English where the selected
implementation supports them.
Normalize OCR output and handle empty results.
Preserve bounding boxes if they are useful for later display.
Release OCR resources correctly.
Create a small test corpus of representative screenshots with permission
to use them.
Acceptance criteria
OCR works on a known set of representative examples.
The test corpus covers all initial source languages.
Empty images and OCR failures do not crash the app.
OCR processing does not require a network connection when the selected
model is available locally.
No OCR text is logged by default.
Performance is measured on the available hardware.
Model-download or availability limitations are documented.
Device checks
Test screenshots containing:
Clean printed text
Small text
Mixed-language content
Dark and light backgrounds
Manga/manhwa speech bubbles
Game dialogue and menus
OCR support for an individual language must not be claimed until it has
been tested with representative examples.
Phase 4 — Screen capture and permissions
Goal
Capture a user-selected screen through the official Android permission flow.
Tasks
Integrate MediaProjection.
Request user consent through Android's official flow.
Implement lifecycle handling and a clear stop action.
Process captured images locally.
Handle revoked permission, interrupted sessions, and unsupported screens.
Follow the Android SDK's current foreground-service requirements.
Acceptance criteria
Capture begins only after the user grants consent.
The app provides a clear way to stop capture.
Permission denial and revocation are handled safely.
Captured frames remain on-device during ordinary translation.
Secure or blocked screens fail gracefully.
Capture resources are released when the session stops.
The feature passes physical-device tests.
Device checks
Grant and deny permission.
Start and stop capture.
Switch between supported applications.
Revoke permissions where possible.
Test after returning from the background.
Confirm that protected screens are not bypassed.
Do not claim this phase is complete based on emulator or VPS tests alone.
Phase 5 — End-to-end translation
Goal
Connect local OCR, the backend, and the translation result UI.
Tasks
Send recognized text and required language settings to the backend.
Display translated output in a readable result surface.
Add loading, cancellation where practical, and failure handling.
Add request timeouts.
Prevent accidental duplicate submissions.
Measure OCR, network, and provider timings without logging text.
Acceptance criteria
A known screen can be captured and translated end-to-end.
The screenshot is not uploaded for ordinary translation.
The app handles empty OCR output without unnecessary provider calls.
Network failure produces a useful message.
Provider failure does not crash the application.
Results correspond to the submitted text and selected target language.
The flow is verified on a physical Android device.
Translation examples are reviewed for meaning, not just successful API
responses.
Phase 6 — Floating overlay
Goal
Provide a convenient user-controlled translation action over supported apps.
Tasks
Add the overlay permission explanation.
Guide the user to system settings when permission is needed.
Implement a floating control with an explicit dismiss action.
Connect the control to the established capture/OCR/translation flow.
Handle app switching and service lifecycle.
Prevent accidental obstruction of important screen controls.
Acceptance criteria
Overlay appears only after required permission is granted.
Overlay can be dismissed and restored through a clear user action.
Tapping it starts the expected translation flow.
Permission denial and revocation are handled.
No uncontrolled capture continues after the user stops the feature.
Behavior is verified on a physical Android device.
Battery and responsiveness are observed during repeated use.
Phase 7 — Language and quality hardening
Goal
Improve reliability across the initial languages and real-world content.
Tasks
Evaluate Japanese, Korean, Chinese, and English source text.
Evaluate common target languages, including English, Spanish, and Turkish.
Add source-language override where useful.
Improve handling of malformed or empty provider output.
Create regression examples for previously incorrect translations.
Review text selection and grouping behavior.
Acceptance criteria
The test corpus covers every advertised initial language.
Known regressions have automated tests where practical.
Unsupported provider language pairs are handled honestly.
UI language and translation target language are separate concepts.
Performance and quality limitations are documented.
Phase 8 — Release candidate
Goal
Prepare a testable, distributable APK with honest product claims.
Tasks
Complete security and privacy reviews.
Build a release APK.
Verify package identity and versioning.
Test installation and upgrade behavior.
Check permissions and app disclosures.
Measure APK size.
Produce release notes and a known-limitations list.
Acceptance criteria
Release build succeeds.
APK installs on the target test device.
Core translation flow passes physical-device testing.
No provider secrets are found in the APK or repository.
Privacy disclosures match actual data flows.
No known critical crash or data-leak issue remains.
Slogan and product page make no unsupported claims.
Build instructions and release notes are complete.
Future work — only after the core release
Possible future work:
Manga/manhwa reading order and bubble grouping
Game-specific glossary and consistent terminology
Optional translation history, only after privacy review
Offline translation engine evaluation
More target languages
Improved text-region selection
Optional accessibility-driven workflows
These are not release requirements until explicitly prioritized.

