# Competitor Technical Analysis (general approaches only)

Source: decompiled reference in `reference/bubble-translate/` (com.niven.translator v4.5.6, versionCode 131).
Scope: manifest, permissions, component list and general architecture. **No code, algorithms, UI, text, brand, keys,
tokens, certificates or endpoints were copied or reused, and no attempt was made to touch their signing material.**

Legend: **[O]** observed in the reference, **[I]** inference, **[U]** unverified.

## 1. What the competitor does
| Area | Finding |
|---|---|
| SDK range | [O] minSdk 24 – targetSdk 36 |
| Permissions | [O] 22 declared, incl. QUERY_ALL_PACKAGES, CAMERA, battery-optimization exemption, overlay, notifications |
| Capture | [O] separate consent activity (MediaProjection), FloatService with ImageReader + VirtualDisplay at a fixed 2000x2000 size, foreground service type mediaProjection |
| Entry points | [O] floating bubble, Quick Settings tile |
| Storage | [O] Room database |
| OCR | [O] ML Kit and tesseract references; [O] cloud OCR client classes -> [I] screenshots may be uploaded to a server |
| Monetization / telemetry | [O] ads, billing and analytics SDKs |
| Accessibility service | [O] not declared in manifest |

## 2. Approaches worth considering (re-implemented independently, not copied)
1. Consent in its own screen, with the capture service started only after consent. Luma already does this.
2. Quick Settings tile as a second, low-friction entry point (future, P3).
3. Capture size derived from real display metrics instead of a fixed square. Luma currently passes 1080x1920/400dpi defaults (see BUG_AUDIT BUG-08).

## 3. Weaknesses we deliberately do NOT adopt
- Broad permissions (QUERY_ALL_PACKAGES, CAMERA, battery exemption) with no clear core need -> larger privacy/Play-policy surface.
- [I] Cloud OCR: screen images leave the device. Luma's model is on-device OCR, text-only to the backend (docs/PRIVACY.md).
- Ad/analytics SDKs inside a screen-reading app.

## 4. Luma gaps found by the comparison
| Gap | Status |
|---|---|
| No user-configurable server address; emulator-only default | Fixed this session (Server settings) |
| Raw exception text shown to the user | Fixed (classified messages) |
| POST_NOTIFICATIONS never requested | Fixed (contextual request) |
| Fixed capture size | Open (BUG-08) |
| No quick-settings tile | Open, P3 |

Nothing here was verified on a device; the reference app was only statically inspected.
