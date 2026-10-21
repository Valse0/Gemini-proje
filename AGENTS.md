
---

## 3. `AGENTS.md`

Dosya yolu: `AGENTS.md`

```markdown
# Luma Translate — Agent Rules

These rules apply to every AI coding agent working on this repository.

## 1. Think before coding

Before editing files:

1. Read `README.md`.
2. Read `GEMINI.md` when using Gemini CLI.
3. Read `docs/ARCHITECTURE.md`.
4. Read the relevant phase in `docs/ROADMAP.md`.
5. Read relevant testing, privacy, and security requirements.
6. Inspect the existing implementation before proposing changes.
7. Identify the smallest change that satisfies the task.

Do not start by rewriting the repository or replacing its architecture.

## 2. Simplicity first

- Prefer the smallest working solution.
- Do not add a library without a clear need.
- Do not create abstractions that have no current use.
- Do not create empty placeholder classes to make the project look complete.
- Do not introduce dependency injection frameworks, databases, analytics,
  authentication, or complex state-management systems without an approved
  requirement.
- Avoid unnecessary refactoring.
- Preserve working code.
- Keep changes focused on the current phase.

## 3. Platform rules

- Android implementation: Kotlin and Jetpack Compose.
- Use native Android APIs for permissions, screen capture, and overlays.
- Do not use React Native, Flutter, or a web view as a substitute for the
  native screen-capture and overlay implementation.
- Backend: TypeScript on Node.js.
- Keep provider-specific translation code on the backend.
- Do not expose provider API keys to the Android application.

## 4. Required development cycle

For every meaningful task:

1. Inspect the current state.
2. State the intended change briefly.
3. Implement only that change.
4. Run relevant tests and build commands.
5. Inspect the final diff.
6. Report what changed, which commands ran, and which checks passed or failed.
7. Mention any test that could not be performed.

Never report a test as passing unless its command completed successfully.

## 5. Phase gates

Follow `docs/ROADMAP.md`.

- Do not begin a later phase while the current phase has failing mandatory
  acceptance criteria.
- A VPS build does not replace physical-device testing.
- A UI screenshot does not prove that a feature works.
- A mock response does not prove that real translation works.
- Do not mark overlay or screen capture as complete without testing the
  permission flow on an actual Android device.

If a phase is blocked by missing credentials or hardware, implement
testable boundaries and clearly report the remaining blocker.

## 6. Privacy rules

- OCR should execute on the device.
- Send only necessary recognized text and required translation parameters.
- Never upload screenshots as part of the ordinary translation request.
- Never log full recognized text or translated text by default.
- Never place secrets in source code, test fixtures, README files, APK
  resources, Gradle files, or Git history.
- Do not add persistent translation history unless explicitly approved.
- Do not add analytics or tracking without an approved design and disclosure.
- Do not claim complete offline translation unless it is implemented and
  tested without network access.

## 7. Screen capture and overlay

- Request screen-capture consent through the official Android flow.
- Explain why overlay permission is needed before sending the user to system
  settings.
- Do not silently start capture.
- Provide a clear way to stop capture and dismiss the floating control.
- Respect secure windows and capture restrictions imposed by Android or
  other applications.
- Do not attempt to bypass DRM, protected content, or operating-system
  security restrictions.
- Do not capture continuously unless the user has explicitly enabled a
  clearly disclosed feature that requires it.

## 8. Network and backend security

- Validate every request at the backend.
- Enforce request-size and text-length limits.
- Add timeouts and bounded retries.
- Use HTTPS in production.
- Do not expose raw provider errors, credentials, or stack traces to clients.
- Apply reasonable rate limits.
- Do not build an unrestricted public proxy to the translation provider.
- Never use client-supplied provider URLs or arbitrary destination URLs.
- Keep configuration in environment variables.
- Return structured errors that do not contain sensitive data.

## 9. UI and design rules

The application should feel premium, modern, and intentional.

- Use a consistent design system for spacing, typography, surfaces, and colors.
- Prefer restrained glass effects over excessive blur, glow, and gradients.
- Keep text contrast accessible.
- Support system insets and different screen sizes.
- Add animation only when it communicates state or improves perceived quality.
- Respect reduced-motion and accessibility needs where practical.
- Do not let visual effects block input or make controls hard to read.
- Do not create decorative buttons that do nothing.
- Loading, success, empty, permission-denied, offline, and error states must
  be designed intentionally.

## 10. Dependencies and versions

- Inspect existing versions before changing build tooling.
- Avoid upgrading unrelated dependencies.
- Use official and maintained libraries where possible.
- Explain any new dependency and why it is necessary.
- Keep the Gradle wrapper and project configuration reproducible.
- Do not invent versions or claim compatibility without verification.

## 11. Testing

Follow `docs/TESTING.md`.

At minimum, run the relevant backend tests, Android unit tests, and builds.
When changes affect permissions, capture, OCR, overlays, or rendering,
identify the physical-device tests required.

Add regression tests for fixed bugs where practical.

## 12. Git hygiene

- Do not run destructive Git commands without explicit authorization.
- Do not rewrite shared history.
- Do not delete user work to simplify implementation.
- Do not commit secrets, generated credentials, local SDK paths, build
  outputs, or local `.env` files.
- Review `git status` and `git diff` before suggesting a commit.
- Keep commits small and descriptive.

## 13. Completion report

Every task report must include:

- Summary of changes
- Files changed
- Commands executed
- Tests and build results
- Device tests still required
- Known limitations
- Whether the current phase acceptance criteria passed

Be direct. Do not hide failures behind phrases such as "mostly complete."
