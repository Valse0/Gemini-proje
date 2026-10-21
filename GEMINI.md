# Gemini CLI Instructions — Luma Translate

You are the implementation agent for Luma Translate.

Your task is to build a reliable native Android screen-translation
application with a small, secure backend. Work incrementally and verify
every meaningful change.

## Mandatory reading

Before making code changes, read:

1. `README.md`
2. `AGENTS.md`
3. `docs/ARCHITECTURE.md`
4. The active phase in `docs/ROADMAP.md`
5. Relevant sections of `docs/TESTING.md`, `docs/PRIVACY.md`,
   `docs/SECURITY.md`, and `docs/RELEASE.md`

`AGENTS.md` contains the mandatory coding, privacy, security, and testing
rules. These instructions supplement it; they do not replace it.

## First task in a new repository

Do not immediately generate the entire application.

First:

1. Inspect the repository and the current Git status.
2. Inspect available Java, Gradle, Android SDK, Node.js, and npm versions.
3. Check whether an Android project or backend already exists.
4. Compare the current state with Phase 0 in `docs/ROADMAP.md`.
5. Report missing prerequisites and propose the smallest next change.
6. Implement only the current phase unless explicitly instructed otherwise.

Do not overwrite existing work merely because the repository differs from
the proposed file tree.

## Product objective

A user should eventually be able to:

1. Open Luma Translate.
2. Grant the necessary permissions through clear, user-initiated flows.
3. Open a supported app or screen.
4. Tap a floating Luma control.
5. Have visible text recognized locally.
6. Send only the recognized text and required settings to the backend.
7. Receive a translation from the configured provider.
8. Read the result in a polished, accessible interface.
9. Stop the overlay or capture session clearly.

Implement this workflow in stages. Do not pretend the complete workflow
exists until it works on a real Android device.

## Technical constraints

- Android: Kotlin and Jetpack Compose.
- Screen capture: Android MediaProjection and official consent flow.
- OCR: on-device OCR, initially evaluate Google ML Kit.
- Overlay: native Android overlay APIs, subject to user permission.
- Backend: TypeScript and Node.js.
- Translation: a replaceable provider interface.
- Initial provider candidate: Gemini API, configured only on the backend.
- Network: HTTPS in production, request validation, bounded inputs,
  timeouts, and rate limits.
- No provider secret may be embedded in the APK.

Use the simplest architecture that meets the current requirements.

## Translation provider design

Android must call the Luma backend, not a third-party translation API
directly with a secret key.

The backend must depend on a provider interface rather than importing a
provider SDK throughout the application.

Keep these responsibilities separate:

- Request validation
- Translation route
- Provider interface
- Provider implementation
- Configuration and secrets
- Error mapping and rate limiting

The initial provider may use Gemini if its API integration, language
support, and output quality pass the relevant tests. If it cannot be used,
report the blocker and recommend the smallest viable alternative. Do not
silently change the privacy model or add an unapproved paid service.

## UI requirements

The design direction is premium and modern, not a generic settings template.

- English is the initial interface language.
- Use a coherent typography, spacing, color, and surface system.
- Use restrained translucent surfaces and subtle motion.
- Prioritize legibility and one-handed operation.
- Include clear permission, loading, empty, success, and failure states.
- Avoid excessive animations and expensive blur effects.
- All visible controls must perform their advertised actions.

Do not spend a phase polishing decorative screens while core behavior is
broken.

## Performance requirements

Measure real behavior before making performance claims.

Record relevant timings where practical:

- Cold launch
- OCR processing
- Backend round trip
- Translation response
- Overlay presentation

Do not log recognized text to measure latency. Use timestamps and
non-sensitive diagnostic metadata.

Do not promise fixed response times across all devices and networks.

## Commands and environment

Inspect the environment before using commands.

For Android, the expected commands from `android/` are:

```bash
./gradlew assembleDebug
./gradlew test
