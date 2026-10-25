# Verification Report — Luma Translate

Date: 2026-10-24/25
Environment: ephemeral cloud sandbox (no persistent Android SDK, no
network egress to the Android SDK distribution, Google's Maven repository,
the Gradle plugin portal, or the npm registry — confirmed below, not
assumed).

This report lists exactly what was run and its actual result, per
`AGENTS.md` §4 and §13 ("Never report a test as passing unless its command
completed successfully"). Nothing below is inferred from reading code —
each line is a command and what it actually returned.

## Environment probe

| Check | Command | Result |
| --- | --- | --- |
| Node.js | `node --version` | `v22.23.4` |
| npm | `npm --version` | `10.9.9` |
| Java | `java -version` | OpenJDK 21.0.12.1 (Temurin-compatible build) |
| Git | `git --version` | `2.43.0` |
| `ANDROID_HOME` / `ANDROID_SDK_ROOT` | `echo $ANDROID_HOME` | empty — not set |
| Android SDK present on disk | `find / -iname "*android-sdk*"` | no results |
| Gradle (standalone) | `which gradle` | `/opt/gradle/bin/gradle` (Gradle 8.14.3 preinstalled) |
| npm registry reachability | `curl https://registry.npmjs.org/express` | `Could not resolve host` / then `403` via explicit proxy routing — destination not allowed for this session |
| Google Maven reachability | `curl https://dl.google.com/...` | `403` via proxy — destination not allowed |
| Maven Central reachability | `curl https://repo.maven.apache.org/...` | `403` via proxy — destination not allowed |
| Gradle Plugin Portal reachability | `curl https://plugins.gradle.org/` | `403` via proxy — destination not allowed |

**Conclusion:** this sandbox session has no network path to any of the
package registries or SDK distribution hosts this project depends on to
install dependencies or build. This is a property of the current session
(reported by the agent proxy as a Free-plan restriction — "On the Free
plan, only package managers can be allowed" — and even the allow-listed
npm registry refused the connection with a policy-style 403 when routed
explicitly through the proxy). It is not a project defect, and it was the
same result on a prior inspection pass in this same conversation.

## Backend (`server/`)

| Step | Command | Result |
| --- | --- | --- |
| Install dependencies | `npm install` (from repo root, for the root/web package) | **Failed**: `ENOTFOUND registry.npmjs.org`, then (after forcing proxy routing) `403 Forbidden` on `registry.npmjs.org` |
| Install dependencies (server/) | not attempted independently | Same registry is unreachable; would fail identically |
| `npm run build` | not run | **Blocked** — no `node_modules`, `tsc`/`vite` not resolvable without install |
| `npm test` (`server/test/translate.test.ts`) | not run | **Blocked** — same reason |
| Local `/v1/translate` smoke request | not run | **Blocked** — server was never started |

**Status: untested.** The backend source was reviewed manually instead (see
`docs/KNOWN_ISSUES.md` items 4 and 6, and the "Already-good" section). No
defects were found in `app.ts`, `config.ts`, `routes/health.ts`,
`routes/translate.ts`, `middleware/validation.ts`,
`middleware/rate-limiter.ts`, `services/translation-provider.ts`,
`services/mock-provider.ts`, or `services/gemini-provider.ts` during that
read, but a manual read is not a substitute for `npm test` actually
passing, and is reported as such.

## Android (`android/`)

| Step | Command | Result |
| --- | --- | --- |
| `./gradlew assembleDebug` | not run | **Blocked** — no Android SDK present, and `dl.google.com` / Maven Central / Gradle Plugin Portal are all unreachable, so even if the Gradle wrapper's own distribution download succeeded, dependency resolution for AGP, Kotlin, Compose, ML Kit, and OkHttp would fail immediately |
| `./gradlew test` | not run | Same blocker |
| Gradle wrapper integrity | `unzip -l gradle-wrapper.jar`, `file gradle-wrapper.jar` | Valid zip archive, readable class entries — wrapper jar itself is not corrupted |
| `gradlew` / `gradlew.bat` present and executable | `ls -la android/gradlew*` | Present; `gradlew` has the executable bit set |

**Status: untested.** No Kotlin file — including the
`FloatingOverlayManager.kt` fix made in this pass — has been compiled or
run. Every Kotlin-level finding and fix in `docs/KNOWN_ISSUES.md` and
`CHANGELOG.md` is explicitly labeled as code-review-only for this reason.

## Static checks that *could* be run without network or an SDK

| Check | Method | Result |
| --- | --- | --- |
| Secret scan | `grep` for key/secret-shaped literals across `.ts`/`.kt`/`.json`/`.properties`/`.xml`/`.yml`, excluding the documented placeholders in `.env.example` | No matches — clean |
| Tracked `.env` files | `git ls-files | grep -E "\.env"` | Only `.env.example` (placeholders only) is tracked |
| `react-example` references | `grep -rn "react-example" .` | Only `package.json`'s `name` field (now renamed) — safe to rename |
| Markdown fence balance | counted `` ``` `` lines per file in `AGENTS.md`, `GEMINI.md`, `README.md`, `docs/*.md` | All files now have an even count (0 is also even/none-needed) — no dangling code fences remain |
| CI YAML fix: internal consistency | manual read of the rewritten `.github/workflows/build-apk.yml` | Logically consistent (SDK root resolved once and exported via `$GITHUB_ENV`, subsequent steps reuse it); **not validated against GitHub's actual YAML schema or a live Actions run** |

## What this means for the mission's acceptance criteria

Per `docs/ROADMAP.md` Phase 0, "Android project builds successfully" and
"Backend installs and builds successfully" are **not met** in this pass —
not because the code is known to be broken, but because neither could be
attempted in this environment. This is reported as a blocker, not papered
over.

**What would resolve this:** running the same repository (with this pass's
changes) in an environment with normal internet access — a local developer
machine, or an actual GitHub Actions run of the now-fixed
`build-apk.yml` — and recording the real `./gradlew assembleDebug` /
`npm test` output. Nothing further can be verified from inside this
sandbox.
