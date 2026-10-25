# Security Requirements — Luma Translate

## 1. Scope

This document defines minimum security requirements for the Android app,
translation backend, build environment, and release process.

## 2. Secrets

- Provider API keys must exist only in the backend environment.
- Never place credentials in Kotlin files, Android resources, BuildConfig,
  committed Gradle properties, test fixtures, or the APK.
- Never commit `.env` files.
- `.env.example` may contain placeholder names but no real credentials.
- Do not print secrets to terminal logs.
- Do not ask users to paste production secrets into GitHub issues or source
  files.

If a credential is exposed, revoke or rotate it immediately. Removing the
visible string from a later commit does not invalidate a leaked credential.

## 3. Backend request validation

The backend must:

- Validate JSON structure.
- Validate source and target language codes.
- Reject empty text.
- Enforce a configurable maximum text length and body size.
- Reject malformed input safely.
- Use bounded timeouts.
- Apply rate limits.
- Avoid arbitrary provider URLs and open-proxy behavior.
- Return normalized errors.

Do not trust client-side validation alone.

## 4. Provider integration

- Use a server-side provider interface.
- Keep credentials in environment variables.
- Set provider request timeouts.
- Validate the returned structure.
- Treat provider output as untrusted data.
- Do not expose raw provider responses or internal stack traces.
- Do not follow URLs or execute instructions found in translated content.
- Avoid unbounded retries that could cause cost or resource spikes.

## 5. Abuse and cost controls

A public endpoint must not allow unlimited use of a paid provider.

Before public deployment, implement appropriate controls such as:

- Request-size limits
- Rate limiting
- Provider timeouts
- Concurrency limits where appropriate
- Basic abuse monitoring without storing translation content
- A documented emergency switch to disable translation

Do not assume an in-memory rate limiter provides reliable protection
across multiple backend instances or restarts. Document its limitations.

Do not expose a paid provider key through a public client-side request.

## 6. Transport and deployment

- Use HTTPS in production.
- Keep server dependencies reasonably current.
- Do not expose development servers as production services.
- Do not publish debug endpoints that reveal configuration.
- Restrict backend access to the minimum required ports.
- Keep deployment secrets outside the repository.
- Do not claim the service is hardened without reviewing its actual
  deployment configuration.

## 7. Android security

- Request only necessary permissions.
- Use the official MediaProjection consent flow.
- Do not bypass secure-window or DRM restrictions.
- Avoid unnecessary exported Android components.
- Keep intent filters and service exposure minimal.
- Use the narrowest suitable permissions and service types supported by
  the target Android SDK.
- Do not persist screenshots or OCR text without an approved requirement.

Review the merged Android manifest before release.

## 8. Dependency safety

- Prefer maintained libraries from reputable sources.
- Avoid adding dependencies for trivial functionality.
- Review new permissions and transitive dependencies.
- Use reproducible dependency declarations where practical.
- Do not perform broad dependency upgrades without a reason and tests.

## 9. Repository hygiene

Before a commit or release:

```bash
git status --short
git diff --check
git diff
```

Use an available secret-scanning tool where practical.
Never run destructive Git commands merely to make the repository clean.
Do not rewrite shared history without explicit authorization.

## 10. Error handling and logging

- Do not return stack traces to the client.
- Do not log translation text or provider credentials.
- Do not log authorization headers.
- Use normalized error categories.
- Keep operational logs useful without collecting content.

## 11. Security acceptance criteria

Before a release candidate:
* No provider secret is embedded in the APK.
* No real credentials are committed.
* Input validation is covered by tests.
* Request size and timeout limits exist.
* Rate limiting is implemented and tested.
* Provider failures are normalized.
* Production traffic uses HTTPS.
* Ordinary translation does not upload screenshots.
* Sensitive text is not logged by default.
* Android permissions and exported components have been reviewed.
* Known security limitations are documented.
