# Changelog

All notable changes to Luma Translate are recorded here. This log starts
from the first professional-transformation pass on top of commit
`b56cbea` ("Simplify Android SDK package installation"); earlier history is
the AI Studio / Gemini CLI scaffold that produced the repository.

## Unreleased — Foundation and CI repair pass

### Fixed

- **CI: Android SDK license-acceptance failure (`yml_hata.md` run #21).**
  `.github/workflows/build-apk.yml` used `android-actions/setup-android@v3`
  to re-download and configure its own `cmdline-tools` build, and the job
  was failing inside that action at the `sdkmanager --licenses` step before
  Gradle ever ran. GitHub's `ubuntu-latest` runner image already ships a
  pre-installed, license-pre-accepted Android SDK. The workflow now uses
  that preinstalled SDK directly (locating `ANDROID_SDK_ROOT`, re-accepting
  licenses defensively, and installing only the exact `platforms;android-34`
  / `build-tools;34.0.0` / `platform-tools` packages the Gradle config
  needs) instead of reinstalling a separate SDK through a third-party
  action. **Not yet verified**: this repository's sandbox has no network
  access to GitHub Actions or an Android SDK, so this fix is a code review
  / root-cause fix, not a confirmed green CI run. See
  `docs/VERIFICATION_REPORT.md`.
- **CI: unbounded auto-commit loop on failure.** The previous "Update
  yml_hata.md on failure" step committed and pushed directly to
  `main`/`master` on every failed run, and its fallback branch (used for any
  failure that happened before `build_error.log` existed, i.e. most SDK
  setup failures) wrote a **hardcoded, unchanging error message** rather
  than that run's actual log — meaning `yml_hata.md` could look like a live
  diagnostic file while silently being a static placeholder, and the
  workflow had `permissions: contents: write` to support this. The step now
  uploads the real build log as a workflow artifact instead of committing
  back to the repository, and the workflow's permissions were narrowed to
  `contents: read`. The historical `yml_hata.md` is kept for reference with
  a note explaining why it stopped being auto-updated.
- **Floating overlay: ComposeView without ViewTree owners.**
  `ui/overlay/FloatingOverlayManager.kt` added a `ComposeView` directly to
  `WindowManager` from a `Service` context. Per documented
  Jetpack Compose requirements, a `ComposeView` needs a
  `ViewTreeLifecycleOwner`, `ViewTreeViewModelStoreOwner`, and
  `ViewTreeSavedStateRegistryOwner` to render; a `Service` provides none of
  these by default, so the overlay would have crashed at render time with a
  "ViewTreeLifecycleOwner not found" style exception the first time a user
  reached Phase 6. Added a minimal `OverlayLifecycleOwner` that supplies all
  three and is started/stopped alongside the overlay view. **Not yet
  verified by a build** (see `docs/KNOWN_ISSUES.md`); this is a code-level
  fix based on documented Compose behavior, not a device-tested fix.

### Changed

- Renamed the root `package.json` name from the leftover AI Studio scaffold
  name `react-example` to `luma-translate-web-companion`. Verified no code,
  script, or config anywhere in the repository referenced the old name
  before renaming.
- Rewrote `README.md`'s "Repository layout" section to match what is
  actually in the repository (previous version listed `androidTest/`,
  `overlay/`, `data/`, and `core/` packages that do not exist, and omitted
  the web companion app, CI workflow, and the actual `pipeline/`,
  `release/` packages that do exist). Added an explicit "Keep the three
  project areas separate" section distinguishing the Android app (primary
  product), backend (translation API), and web app (dev/diagnostic
  companion, not the shipping product).
- Repaired `AGENTS.md`, `GEMINI.md`, and `docs/ARCHITECTURE.md`,
  `docs/PRIVACY.md`, `docs/RELEASE.md`, `docs/ROADMAP.md`,
  `docs/SECURITY.md`, `docs/TESTING.md`: all of these files, as cloned from
  the repository, contained a leftover "spec document" wrapper (literal
  Turkish headers like `Dosya yolu: ...` / `Bu dosya ...`, and in several
  files an opening ` ```markdown ` fence with **no closing fence**, meaning
  the rest of the document was technically one giant unclosed code block).
  `GEMINI.md` was truncated mid-sentence inside a code fence. All of these
  documents have been restored to clean, properly-fenced Markdown with
  their original substantive content preserved; `GEMINI.md`'s truncated
  command list was completed using the equivalent backend commands already
  documented elsewhere in the repo (`docs/ROADMAP.md` Phase 0), with an
  inline note explaining the repair.
- `docs/ARCHITECTURE.md`'s Android package list now matches the actual
  source tree (floating-control UI lives under `ui/overlay/`, not a
  top-level `overlay/`; `data/` and `core/` don't exist yet) instead of
  describing a layout the code doesn't follow.
- `docs/ARCHITECTURE.md`'s backend section now notes that the real file
  layout (`app.ts`, `routes/health.ts`, `middleware/validation.ts` +
  `rate-limiter.ts`, `services/gemini-provider.ts` + `mock-provider.ts`) is
  a legitimate, slightly more granular version of the suggested layout, not
  a deviation from it.

### Known limitations introduced or confirmed this pass

See `docs/KNOWN_ISSUES.md` for the full list. Highlights:

- The Android build could not be run in this environment (no network
  access to Google's Maven repository or any Android SDK component), so no
  Kotlin change in this pass — including the overlay fix above — has been
  compiled or tested. Flagged explicitly rather than claimed as working.
- The backend could not be `npm install`ed in this environment (no network
  access to the npm registry), so `npm test` / `npm run build` could not be
  run either, even though the backend code itself was reviewed and no
  defects were found in it.
- `capture/CaptureService.kt`'s overlay-tap callback is still a `// To be
  implemented` stub: `pipeline/TranslationPipeline.kt` and
  `ocr/OcrManager.kt` exist, have contract tests, but are never
  instantiated anywhere in the app. The capture → OCR → translate → overlay
  result flow described in `docs/ARCHITECTURE.md` is not yet wired
  end-to-end. This was **not** newly introduced by this pass; it was
  already true in the cloned repository and is called out here because it
  is the single biggest gap versus the product mission.
