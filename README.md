# Luma Translate

**Your Screen. Your Language. Your Privacy.**

Luma Translate is an Android screen-translation app designed to help
people understand text inside games, manga, apps, websites, and other
screen content without repeatedly switching between applications.

Our goal is to combine fast interaction, useful translation quality,
transparent privacy practices, and a polished interface.

> **Current architecture (supersedes older backend wording in this repository):**
> the Android app translates **on-device** (Google ML Kit OCR + ML Kit
> Translation). It needs no server, VPS, API key, account or paid API. Optional
> language packs are downloaded only after the user taps Download. The
> `server/` project is no longer used by the app. See `docs/MODELS.md`,
> `docs/PRIVACY.md` and `docs/analysis/`.

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
- Translation: Google ML Kit on-device Translation (optional, consent-based
  language-pack downloads; works offline once installed)
- `server/` (TypeScript/Node.js): legacy optional translation API, not used by the app
- Build: Gradle for Android (built by GitHub Actions); npm scripts for the legacy server/web app
- Source control: Git and GitHub

The translation provider must remain replaceable. Provider-specific code
must not be scattered across the Android application.

## Privacy commitments

The implementation must follow `docs/PRIVACY.md` and `docs/SECURITY.md`.

The implemented behavior is:

- OCR runs locally on the device.
- Translation runs locally on the device; recognized text is not sent to any server.
- Screenshots are never uploaded.
- No API keys or credentials exist in the app. The only network use is the
  optional, user-approved language-pack download.
- Translation text is not written to application logs by default.
- Any optional analytics or persistent history requires explicit design,
  documentation, and user-facing disclosure.

Translation works offline once the needed language packs are installed. The
first use of a language pair needs a one-time download, so the app is not
"offline from the first launch". Google ML Kit may exchange non-content
diagnostic/usage metrics under its own privacy terms. This has not been
tested on a physical device yet (see `docs/KNOWN_ISSUES.md`).

## Repository layout

This reflects what is actually in the repository today, not an aspirational
target. `androidTest/` is referenced by Gradle (Espresso/Compose UI test
dependencies) but does not exist yet — see `docs/TESTING.md` and the
known-issues list for that gap.

```text
luma-translate/
├── README.md
├── AGENTS.md
├── GEMINI.md
├── CHANGELOG.md
├── .gitignore
├── .github/workflows/build-apk.yml   # CI: debug/release APK + AAB build
├── android/                          # Primary product: native Kotlin/Compose app
│   ├── settings.gradle.kts
│   ├── build.gradle.kts
│   ├── gradle.properties
│   ├── gradle/libs.versions.toml
│   └── app/
│       ├── build.gradle.kts
│       ├── proguard-rules.pro
│       └── src/
│           ├── main/
│           │   ├── AndroidManifest.xml
│           │   └── java/com/luma/translate/
│           │       ├── MainActivity.kt
│           │       ├── app/              # Application class
│           │       ├── ui/
│           │       │   ├── theme/
│           │       │   ├── home/
│           │       │   ├── overlay/       # Compose UI for the floating control
│           │       │   └── privacy/
│           │       ├── capture/           # MediaProjection + permission flow
│           │       ├── ocr/               # ML Kit on-device text recognition
│           │       ├── pipeline/           # OCR -> translation orchestration
│           │       ├── translation/       # ML Kit translator + language-pack manager
│           │       ├── util/              # Task await helper, external links
│           │       ├── settings/
│           │       └── release/
│           └── test/                      # JVM unit tests (no androidTest/ yet)
├── server/                           # Legacy optional translation API (unused by the app)
│   ├── package.json
│   ├── tsconfig.json
│   ├── test/translate.test.ts
│   └── src/
│       ├── index.ts
│       ├── app.ts
│       ├── config.ts
│       ├── routes/            # /health, /v1/translate, /v1/provider-info
│       ├── services/          # provider interface, Gemini + mock providers
│       └── middleware/        # request validation, rate limiting
├── src/, index.html, vite.config.ts, server.ts, package.json
│                                    # React/Vite web companion: build/test/OCR
│                                    # diagnostic dashboards, NOT the shipping
│                                    # product. See "Keep the three project
│                                    # areas separate" below.
└── docs/
    ├── ARCHITECTURE.md
    ├── ROADMAP.md
    ├── TESTING.md
    ├── PRIVACY.md
    ├── SECURITY.md
    ├── RELEASE.md
    ├── VERIFICATION_REPORT.md
    └── KNOWN_ISSUES.md
```

## Keep the three project areas separate

- **Android (`android/`)** is the primary, user-facing product. Prioritize
  its buildability, permissions, lifecycle handling, OCR, translation, and
  overlay behavior over anything else in this repository.
- **Legacy server (`server/`)** is a small translation API that the app no
  longer calls. It is kept for reference; do not reintroduce it as a
  requirement for the core translation flow.
- **Web app (`src/`, `index.html`, root `vite.config.ts`/`package.json`)** is
  a development, testing, and diagnostic companion (acceptance-suite,
  Android-behavior simulation, OCR visualization, release and settings
  dashboards). It is not the shipping product and should not be grown into
  one, merged with the Android UI, or used to introduce a second UI
  framework into the Android app.
