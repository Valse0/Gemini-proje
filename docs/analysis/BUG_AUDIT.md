# Bug Audit (screenshots Hata1–4 + code review)

Priority P0 (blocks use) – P3 (polish). Confidence: High = proven by evidence in repo/screenshots; Medium = strongly indicated;
Low = hypothesis needing device data. Nothing below was reproduced on a device from this sandbox.

| ID | P | Confidence | Summary | Status |
|---|---|---|---|---|
| BUG-01 | P0 | High | Translation always fails on a physical phone: backend default is the emulator alias `http://10.0.2.2:3000` | Fixed (runtime Server settings, fail-fast NOT_CONFIGURED) |
| BUG-02 | P1 | High | Raw exception text (LAN IP, ports) shown to users | Fixed (FailureKind messages, class-name-only logs) |
| BUG-03 | P0 | Medium | Hata3 install error "package conflicts with existing package" | Diagnosis only; needs device output (below) |
| BUG-04 | P1 | High | No hosted backend is configured anywhere; users cannot point the app at one | Fixed in-app; hosting remains the owner's decision |
| BUG-05 | P2 | High | POST_NOTIFICATIONS declared but never requested (Android 13+ stop-notification may be hidden) | Fixed (asked in context after Start; denial safe) |
| BUG-06 | P2 | High | Text > 2000 chars guaranteed HTTP 400 for whole screen; blank blocks sent | Fixed (trim/skip) |
| BUG-07 | P3 | High | Response body not closed on error paths | Fixed (`use {}`) |
| BUG-08 | P3 | Medium | Fixed 1080x1920/400dpi capture parameters | Open |
| BUG-09 | P2 | Medium | Debug cleartext only for 10.0.2.2/localhost; a LAN http server is blocked even in debug | Documented; use HTTPS |

## Hata1 / Hata2 / Hata4 (same root cause)
- Message seen: "Translation failed: failed to connect to /10.0.2.2 (port 3000) from /192.168.1.3 (port 60628) after 5000ms".
- Evidence: BuildConfig default URL + OkHttp connectTimeout 5000 ms match exactly; source IP 192.168.1.3 shows a real phone.
- Root cause: 10.0.2.2 only resolves inside the Android emulator. Not a timeout problem, so timeouts were **not** increased.
- Fix: see BUG-01/02. Verified by JVM tests (`TranslationFailureTest`), not on a device.

## Hata3 – install conflict
- Message: "Paket, mevcut bir paketle çakıştığından uygulama yüklenemedi" (generic package-conflict).
- Most likely cause (Medium): the installed copy was signed with a different certificate (CI previously generated a random debug key per run). Alternatives: same applicationId from a different source, or lower versionCode (Low).
- Cannot be proven without: `adb install -r app.apk` output (INSTALL_FAILED_UPDATE_INCOMPATIBLE vs VERSION_DOWNGRADE), `apksigner verify --print-certs` for both APKs, `adb shell dumpsys package <id> | grep versionCode`.
- Done: committed shared debug keystore so future debug builds are stable; versionCode monotonic. **Not done (needs approval):** uninstalling the old app, creating/replacing any release key, changing the package name. The committed debug keystore is a new identity, so one reinstall is likely needed once.
