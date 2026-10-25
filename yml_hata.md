# Build Error Report (historical) - Wed Oct 21 19:10:36 UTC 2026
GitHub Actions Run: #21

> **Note (2026-10-24):** This file was being overwritten automatically by the
> "Update yml_hata.md on failure" step in `.github/workflows/build-apk.yml`.
> That step had a hardcoded fallback for failures that happened before the
> Gradle build ran, so this exact message would have been re-committed for
> *any* pre-Gradle failure, not necessarily today's real cause. The workflow
> has been changed to upload the build log as a workflow artifact instead of
> committing back to the repository, and to use the Android SDK already
> preinstalled on the GitHub-hosted runner instead of `android-actions/setup-android@v3`,
> which is where this specific failure occurred. See `docs/RELEASE.md` and
> the verification report in this delivery for details. This file is kept
> for history and is no longer updated automatically.

## Setup / SDK Error Details
```
Error: The process '/usr/local/lib/android/sdk/cmdline-tools/16.0/bin/sdkmanager' failed with exit code 1
    at ExecState._setResult (/home/runner/work/_actions/android-actions/setup-android/v3/dist/index.js:1823:25)
    at ExecState.CheckComplete (/home/runner/work/_actions/android-actions/setup-android/v3/dist/index.js:1806:18)
    at ChildProcess.<anonymous> (/home/runner/work/_actions/android-actions/setup-android/v3/dist/index.js:1700:27)
    at ChildProcess.emit (node:events:514:28)
    at maybeClose (node:internal/child_process:1141:16)
    at ChildProcess._handle.onexit (node:internal/child_process:306:5)

Node.js v24.21.0
```
