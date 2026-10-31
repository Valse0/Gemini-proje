# Privacy Policy and Data Handling — Luma Translate

## Current behavior (on-device architecture)

- **OCR:** on-device (Google ML Kit text recognition). Screenshots are never uploaded.
- **Translation:** on-device (Google ML Kit Translation). Recognized and translated
  text is not sent to any server, and is not logged or stored.
- **Network use:** only the optional language-pack download, started only after the
  user taps Download (Wi-Fi only by default). The download fetches model files; it does
  not upload screen text.
- **Google ML Kit** may contact Google for model downloads/updates and may send
  performance/usage metrics under Google's ML Kit terms. The SDK states that input
  text is processed on-device and not sent to Google servers. Verify the current
  terms before each release; the app owner is responsible for the disclosures those
  terms require.
- **No accounts, no API keys, no analytics, no translation history.**
- **Support / Feedback** links open https://ko-fi.com/hepyek and https://tally.so/r/aO9QkW in
  the external browser; no data is sent by the app itself.
- **Not yet tested on a physical device.**

The sections below describe the earlier backend design and apply only to the legacy `server/` project.


## 1. Purpose

This document defines the intended privacy behavior of Luma Translate.
It is an engineering requirement, not a substitute for legal review or a
final public privacy policy.

The implementation and any public privacy statements must match actual
behavior.

## 2. Core principles

- Process screen images locally for ordinary OCR.
- Send only the recognized text and parameters required for translation.
- Do not upload screenshots for ordinary translation.
- Do not retain translation content by default.
- Do not log OCR text or translated text by default.
- Keep provider credentials on the backend.
- Request sensitive permissions only when needed.
- Explain permissions in plain language.
- Do not add hidden tracking or analytics.

## 3. Data flow

### On the device

The app may temporarily process:

- A user-authorized screen frame
- OCR results
- Source and target language settings
- Translation output while displaying the result

Screen frames and OCR content should remain in memory for as long as
practical and be released when no longer needed.

Persistent local storage of screenshots or OCR content is not part of the
initial release.

### Sent to the backend

The ordinary translation request may include:

- Recognized text
- Target language
- Source-language hint when available
- Minimal request metadata required for operation and rate limiting

Do not send the screenshot or full screen image.

### Sent to a translation provider

The backend may send the recognized text and required language parameters
to the configured provider.

Users must be informed that online translation sends text to a third-party
translation service. The privacy characteristics and retention terms of
that service must be reviewed before release.

Do not claim that text never leaves the backend if the provider receives it.

## 4. Data not collected by default

The initial release should not require:

- A user account
- Contact lists
- Location access
- Microphone access
- Persistent device tracking identifiers
- Translation history stored on a server
- Advertising identifiers
- Analytics events containing text
- Screenshot uploads for ordinary translation

Any future feature that requires additional data must be reviewed,
documented, and disclosed before implementation.

## 5. Logs and retention

By default:

- Do not log screenshots.
- Do not log OCR text.
- Do not log translated text.
- Do not log API keys or authorization headers.
- Do not store translation requests in a database.
- Do not persist request bodies for debugging.

Operational logs may contain timestamps, status codes, normalized error
categories, and non-sensitive latency measurements.

Set a documented retention policy for operational logs in the deployment
environment. Do not claim zero retention unless every relevant component
and provider has been verified.

## 6. Permissions

### Screen capture

The app must use Android's official user-consent flow.

Explain that capture is used to recognize visible text for translation.
The user must be able to stop the session.

### Overlay

Explain why the floating control needs overlay permission. Request the
permission only when the user activates the feature.

Do not use overlay permission to deceive users, intercept unrelated input,
or display hidden controls.

## 7. Network security

- Use HTTPS for production communication.
- Do not embed translation-provider secrets in the APK.
- Validate requests on the backend.
- Limit request size and processing time.
- Avoid sending unnecessary metadata.
- Do not include sensitive text in error reports.

## 8. Offline behavior

On-device OCR may work without an active network connection if the required
OCR model is available locally.

Online translation requires a network connection. If an offline translation
engine is added in the future, it must be separately implemented, tested,
and documented.

Do not describe the initial application as providing full offline
translation.

## 9. Third-party provider review

Before release, review the configured translation provider's current:

- Data-processing terms
- Retention policy
- Training and data-use terms
- Supported language pairs
- Regional processing details, where applicable
- Security requirements
- Pricing and rate limits

Record the selected provider and relevant review date in the release
checklist.

Do not assume provider privacy settings are identical across products
or account types.

## 10. User-facing disclosures

Before the first online translation, the app should clearly explain that
recognized text is sent to the Luma backend and then to the configured
translation provider.

The wording must match the actual implementation.

Do not claim:

- "Your data never leaves your device."
- "Zero data retention everywhere."
- "100% private."
- "Completely offline translation."

unless the exact claim is technically true, verified, and appropriately
qualified.

## 11. Future privacy review

Before implementing translation history, analytics, user accounts, cloud
sync, automatic continuous capture, or offline language downloads, update
this document and review the data flow.

Privacy is a product requirement, not a marketing-only feature.
