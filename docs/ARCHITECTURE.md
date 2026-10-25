# Architecture — Luma Translate

## 1. Architecture goals

The architecture must support:

- Native Android screen capture
- On-device OCR
- A floating translation control
- A replaceable translation provider
- A polished, responsive user interface
- Minimal collection and retention of user data
- Automated tests and physical-device verification

The architecture must remain small until real product requirements justify
additional complexity.

## 2. System overview

```text
Android device
    |
    | User grants screen-capture consent
    v
MediaProjection capture
    |
    v
Local image processing
    |
    v
On-device OCR
    |
    | Recognized text only
    | + source/target language settings
    v
Luma backend over HTTPS
    |
    v
Request validation and rate limiting
    |
    v
Translation service interface
    |
    v
Configured translation provider
    |
    v
Validated translation response
    |
    v
Android translation result UI
```

Ordinary translation requests must not upload screenshots.

## 3. Android application

Package namespace: `com.luma.translate`

Use Kotlin and Jetpack Compose.

### Main responsibilities

- **`MainActivity.kt`** — Android entry point.
- **`app/`** — Application setup and navigation.
- **`ui/theme/`** — Colors, typography, shapes, spacing, and reusable visual
  tokens.
- **`ui/home/`** — Main screen and primary actions.
- **`ui/settings/`** (or `settings/`) — Language, appearance, and privacy
  settings.
- **`ui/overlay/`** — User-facing overlay configuration and status UI.
- **`capture/`** — MediaProjection consent and capture lifecycle.
- **`ocr/`** — On-device OCR interface and implementation.
- **`translation/`** — Backend client and translation models.
- **`overlay/`** — Floating control lifecycle and rendering (currently
  implemented under `ui/overlay/`; see note below).
- **`data/`** — Small local preferences repository (not yet created; add
  only when a real local-preferences need exists).
- **`core/`** — Shared result/error types and configuration that is safe to
  ship (not yet created; add only when shared types are actually needed
  across packages).

Create a class only when it owns a real responsibility. Do not create a
generic framework for every feature.

> **Current implementation note:** the floating-control UI currently lives
> under `ui/overlay/` (`FloatingOverlayManager.kt`) rather than a separate
> top-level `overlay/` package, and `data/` and `core/` do not exist yet.
> That split is acceptable for the current size of the app; consolidate or
> split further only when a package's responsibilities actually demand it,
> per the simplicity rule above.

### UI and domain separation

Compose screens should not directly manage MediaProjection callbacks, OCR
recognizers, or HTTP request details.

Use small state holders or ViewModels where needed. Do not introduce a large
state-management framework for a small application.

Represent user-visible states explicitly, including:

- Idle
- Waiting for permission
- Capturing
- Recognizing text
- Translating
- Showing result
- No text found
- Network failure
- Provider failure
- Permission revoked

### Screen capture

Use the Android MediaProjection consent flow.

The user must initiate capture and be able to stop it. Follow current
Android SDK requirements for foreground services and MediaProjection
lifecycle handling.

Do not assume that all applications permit screenshots. Secure windows,
DRM, and operating-system restrictions may prevent capture.

### OCR

Define a small OCR boundary so the implementation can be tested
independently of screen capture.

The OCR component should:

- Accept an image or supported image region.
- Return recognized text and useful bounding-box information when
  available.
- Support Japanese, Korean, Chinese, and English where the chosen OCR
  implementation and model configuration permit.
- Release recognizer resources when no longer needed.
- Handle no-text and OCR-failure states.
- Avoid logging recognized content.

Do not send screenshots to the backend for OCR.

### Overlay

The overlay should:

- Appear only after the user enables it and grants required permission.
- Have a clear dismiss or stop action.
- Avoid obstructing important screen controls.
- Handle app switching and permission revocation.
- Explain unsupported or blocked capture cases.
- Avoid persistent capture when the feature is not active.

## 4. Backend

Use TypeScript and Node.js.

Keep the first backend small. A database and user-account system are not
required for the initial release.

Suggested responsibilities:

- `index.ts`: server startup
- `routes/translate.ts`: translation endpoint and request validation
- `services/translation-provider.ts`: provider interface and implementation
- `middleware/rate-limit.ts`: request throttling
- `config.ts`: environment configuration validation

The exact file layout may change if the implementation stays simpler without
compromising the security boundaries. (The current implementation splits
this further into `app.ts`, `routes/health.ts`, `routes/translate.ts`,
`services/gemini-provider.ts`, `services/mock-provider.ts`, and
`middleware/validation.ts` + `middleware/rate-limiter.ts` — consistent with
this goal, not a deviation from it.)

### Translation provider interface

The route must depend on a provider interface rather than on a specific
provider SDK.

The interface should express the minimum required operation:

- Input: validated source text, source-language hint if supplied, and
  target language.
- Output: translated text and only necessary non-sensitive metadata.
- Failure: a normalized error category.

The initial provider can use Gemini, but replacing it must not require
rewriting Android networking or UI code.

Do not allow clients to select arbitrary provider URLs or pass credentials.

### API contract

Initial endpoint: `POST /v1/translate`

Example request:

```json
{
  "text": "こんにちは",
  "sourceLanguage": "ja",
  "targetLanguage": "en"
}
```

Example successful response:

```json
{
  "translatedText": "Hello"
}
```

The contract is illustrative and should be implemented consistently.

Requirements:

- Validate request shape and supported language codes.
- Reject empty or oversized text.
- Set a documented maximum request size.
- Apply request timeouts.
- Apply reasonable rate limits.
- Return structured, non-sensitive errors.
- Do not include provider secrets or raw provider error payloads.
- Do not log request text or translated output by default.
- Do not automatically retry non-idempotent operations without a clear
  bounded retry policy.

Choose the initial text limit based on tests and provider constraints.
Document the selected value in code and tests rather than duplicating
unrelated magic numbers.

## 5. Provider and language behavior

Support language codes consistently across Android and the backend.

Initial source languages:

- Japanese: `ja`
- Korean: `ko`
- Chinese: `zh`, with the chosen script behavior documented
- English: `en`

Initial target languages should include common languages, beginning with
English, Spanish, and Turkish, then expanding based on provider support and
testing.

Do not assume that every provider supports every language pair equally.

Source-language auto-detection may be offered where reliable. Allow a
manual override when detection is uncertain.

Keep provider prompts and output parsing inside the provider
implementation. Treat provider output as untrusted input and validate it
before returning it.

## 6. Data storage

Initial release:

- No account system
- No server-side translation history
- No database unless a demonstrated requirement appears
- Local preferences only when necessary
- No default persistent OCR text storage

Store only the settings needed to provide the intended experience.

## 7. Configuration and secrets

Server-side configuration may include:

- `PORT`
- `GEMINI_API_KEY`
- Rate-limit settings
- Provider timeout settings
- Other documented deployment values only when needed

Validate required configuration at startup.

Keep `.env` files out of Git. Provide `.env.example` containing placeholder
names and non-secret examples only.

Never ship provider keys in Android resources, BuildConfig fields, source
code, or the APK.

## 8. Observability

Log operational events without sensitive text.

Acceptable examples:

- Request accepted or rejected
- Provider latency
- Normalized error category
- HTTP status
- Rate-limit event

Do not log screenshots, recognized text, translated text, authorization
headers, API keys, or raw provider responses.

Avoid collecting device identifiers or persistent tracking identifiers
unless a reviewed requirement makes them necessary.

## 9. Failure handling

The app must handle:

- Permission denied or revoked
- Capture unavailable
- No text recognized
- OCR failure
- Network unavailable
- Request timeout
- Rate limit exceeded
- Provider unavailable
- Invalid provider response
- Unsupported language pair

Errors shown to users should be understandable and must not expose secrets
or internal stack traces.

## 10. Architecture decision rule

When choosing between two approaches, prefer the one that:

- Meets the current acceptance criteria.
- Preserves privacy and security.
- Is easier to test on the VPS and an Android device.
- Adds fewer dependencies and less code.
- Can be extended later without premature abstractions.

Do not implement future manga or game intelligence before the basic
screen-to-translation workflow works reliably.
