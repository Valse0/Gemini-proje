# Luma Translate

**Your Screen. Your Language. Your Privacy.**

Luma Translate is an Android screen-translation app designed to help
people understand text inside games, manga, apps, websites, and other
screen content without repeatedly switching between applications.

Our goal is to combine fast interaction, useful translation quality,
transparent privacy practices, and a polished interface.

## Product principles

- **Privacy by design:** OCR should run on the device whenever supported.
  Only the text required for translation should be sent to the translation
  backend. Screen images must not be uploaded for ordinary translation.
- **Quality before claims:** Translation quality must be evaluated using
  real Japanese, Korean, Chinese, and English examples.
- **Fast interaction:** Minimize the number of steps between seeing text
  and understanding it. Measure performance instead of promising
  unverified speeds.
- **Native Android:** Use Kotlin and Jetpack Compose, with native Android
  APIs for screen capture and floating overlays.
- **Small, maintainable architecture:** Prefer a working, testable
  implementation over unnecessary abstractions.
- **Honest product status:** A feature is not complete until its behavior
  has been verified on a real Android device.

## Product modes

The long-term product has three related use cases:

1. **General screen translation**
   Translate visible text in supported apps and websites.
2. **Manga and manhwa**
   Improve text grouping, reading order, and speech-bubble interpretation.
3. **Games**
   Translate dialogue, menus, and game-specific terminology consistently.

These are product goals, not claims that every mode is already implemented.
The first release should focus on a reliable screen-capture-to-translation
workflow before adding specialized behavior.

## Initial technical direction

- Android: Kotlin, Jetpack Compose, Android SDK
- OCR: Google ML Kit or another suitable on-device OCR implementation
- Screen capture: Android MediaProjection with explicit user consent
- Floating controls: Android overlay APIs, only after permission is granted
- Translation: a small backend with a replaceable provider interface
- Initial translation provider: Gemini API behind the provider interface,
  subject to successful integration and quality tests
- Backend: TypeScript and Node.js
- Build: Gradle for Android; npm scripts for the backend
- Source control: Git and GitHub

The translation provider must remain replaceable. Provider-specific code
must not be scattered across the Android application.

## Privacy commitments

The implementation must follow `docs/PRIVACY.md` and `docs/SECURITY.md`.

The intended default behavior is:

- OCR runs locally on the device.
- Only necessary recognized text and translation settings are sent to
  the backend.
- Screenshots are not uploaded for ordinary text translation.
- API credentials stay on the backend and are never embedded in the APK.
- Translation text is not written to application logs by default.
- Any optional analytics or persistent history requires explicit design,
  documentation, and user-facing disclosure.

Do not describe the app as completely offline: online translation requires
a network connection unless an offline translation engine is implemented.
Do not claim zero data retention until all relevant providers, servers,
logs, and operational settings have been verified.

## Repository layout

```text
luma-translate/
├── README.md
├── AGENTS.md
├── GEMINI.md
├── .gitignore
├── android/
│   ├── settings.gradle.kts
│   ├── build.gradle.kts
│   ├── gradle.properties
│   └── app/
│       ├── build.gradle.kts
│       └── src/
│           ├── main/
│           │   ├── AndroidManifest.xml
│           │   └── java/com/luma/translate/
│           │       ├── MainActivity.kt
│           │       ├── app/
│           │       ├── ui/
│           │       │   ├── theme/
│           │       │   ├── home/
│           │       │   ├── settings/
│           │       │   └── overlay/
│           │       ├── capture/
│           │       ├── ocr/
│           │       ├── translation/
│           │       ├── overlay/
│           │       ├── data/
│           │       └── core/
│           ├── test/
│           └── androidTest/
├── server/
│   ├── package.json
│   ├── tsconfig.json
│   └── src/
│       ├── index.ts
│       ├── routes/
│       ├── services/
│       ├── middleware/
│       └── config.ts
└── docs/
    ├── ARCHITECTURE.md
    ├── ROADMAP.md
    ├── TESTING.md
    ├── PRIVACY.md
    ├── SECURITY.md
    └── RELEASE.md
