# Testing Strategy — Luma Translate

## 1. Testing principles

A successful build is necessary but not sufficient.

Tests must verify real behavior, not only that classes compile or screens
render. A mock provider can verify application behavior but cannot prove
real translation quality.

Separate results into:

- Automated tests passed
- VPS build passed
- Real-provider tests passed
- Physical-device tests passed
- Tests blocked or not yet performed

Never mark an unperformed test as passed.

## 2. Required checks by change type

### Android UI changes

- Build the Android application.
- Run relevant unit tests.
- Verify important loading, empty, and error states.
- Check layout and accessibility on a device when behavior may differ
  between the emulator and a physical device.

### OCR changes

- Test clean text and difficult text.
- Include Japanese, Korean, Chinese, and English examples.
- Check no-text and recognition-error behavior.
- Confirm OCR text is not written to logs.
- Measure processing time on actual hardware.

### Screen capture or overlay changes

- Build the app.
- Run applicable automated tests.
- Test permission granted, denied, and revoked.
- Test stopping capture and returning from the background.
- Verify the behavior on a physical Android device.

### Backend changes

From `server/`:

```bash
npm ci
npm run build
npm test
```

The scripts must exist and perform the documented checks.

### Translation provider changes

- Test valid input.
- Test invalid and oversized input.
- Test missing credentials.
- Test timeout and provider failure.
- Test the configured language pairs.
- Check that raw provider responses and secrets do not leak to clients.
- Verify that logs do not contain submitted or translated text by default.

## 3. Backend test matrix

The backend test suite should cover:

| Scenario                        | Expected behavior                               |
| -------------------------------- | ------------------------------------------------ |
| Valid translation request        | Successful response with translated text         |
| Missing text                     | Validation error                                  |
| Empty text                       | Validation error                                  |
| Oversized text                    | Rejected before provider call                     |
| Invalid language code            | Validation error                                  |
| Unsupported language pair        | Clear, normalized failure                         |
| Missing provider credential      | Safe configuration failure                        |
| Provider timeout                 | Bounded failure response                          |
| Provider returns invalid data    | Safe normalized error                             |
| Rate limit exceeded              | HTTP 429 or documented equivalent                 |
| Unexpected internal error        | Generic client error without stack trace          |
| Malformed JSON                   | Controlled client error                           |
| Repeated request                 | Does not trigger unbounded retries                |

Use deterministic mocks for unit tests. Use authorized credentials for
separate integration tests with the real provider.

## 4. OCR test corpus

Maintain a small, documented set of test images that may legally be used for
testing.

Cover:

- Japanese printed text
- Korean printed text
- Simplified and/or traditional Chinese, with the chosen scope documented
- English text
- Mixed-language text
- Small fonts
- Low contrast
- Dark backgrounds
- Manga/manhwa speech bubbles
- Game menus and dialogue
- Images containing no readable text

Do not add copyrighted screenshots to public test fixtures unless their use
and distribution are permitted. Prefer synthetic, public-domain, or
appropriately licensed examples.

For each example, record expected text or a human-reviewed reference
transcription. OCR quality should be evaluated against that reference.

Do not require exact OCR output when equivalent punctuation or whitespace
normalization is acceptable; define normalization explicitly.

## 5. Translation quality evaluation

Successful API responses do not prove accurate translation.

Create a small human-reviewed evaluation set containing representative
sentences and short passages for each supported source language.

Review:

- Meaning preservation
- Names and proper nouns
- Negation and intent
- Tone and politeness
- Dialogue context
- Game-specific terminology
- Manga/manhwa dialogue context
- Formatting and readability

Record serious mistranslations and add regression examples where practical.

Do not claim "perfect translation", "the most accurate translation", or
equivalent absolute quality statements.

## 6. Physical-device checklist

Run this checklist on a real Android device before release.

### Permissions and lifecycle

- App opens and closes without crashing.
- Screen-capture consent is requested at the appropriate time.
- Denying consent produces a clear explanation.
- Capture can be stopped explicitly.
- Permission revocation is handled.
- Overlay permission is requested only when needed.
- The user can dismiss the overlay.
- Returning from the background does not create duplicate services.

### OCR and translation

- Japanese sample translates.
- Korean sample translates.
- Chinese sample translates within the documented scope.
- English sample translates to a supported target language.
- No-text screen produces a useful empty state.
- Network loss produces a useful error.
- Provider failure does not crash the app.
- Result text is readable and not clipped.

### Privacy

- Ordinary translation sends recognized text rather than screenshots.
- Logs contain no recognized or translated content by default.
- APK and repository contain no provider credentials.
- No undisclosed analytics or translation history exists.
- Permission explanations match actual behavior.

### Performance and usability

- Cold launch time is measured.
- OCR time is measured.
- Backend and provider time are measured separately where possible.
- Repeated translation does not cause uncontrolled resource growth.
- Overlay controls remain usable.
- App handles device rotation or configuration changes as designed.

## 7. Security checks

Before release:

- Review `git diff`.
- Check tracked files for `.env` files and credential patterns.
- Review dependency changes.
- Confirm production endpoints use HTTPS.
- Verify request limits, rate limits, and timeouts.
- Verify provider error responses are sanitized.
- Confirm sensitive text is not logged.
- Inspect the generated APK for accidental secrets using available tools.

A secret scanner is useful but cannot guarantee that all secrets have been
found. If a secret was committed, removing it from the latest commit is not
enough; rotate it and follow the repository's incident procedure.

## 8. Test reports

For every phase, record:

- Date and commit identifier
- Commands executed
- Passed tests
- Failed tests
- Tests not run
- Device and Android version, where relevant
- Known limitations
- Acceptance criteria status

Do not report "all tests passed" when physical-device checks remain
outstanding.
