# APK Update Conflict Resolution Plan

The "App not installed" error during updates usually occurs because:
1. **Signature Mismatch**: The new APK is signed with a different key than the installed version.
2. **Version Code Conflict**: The `versionCode` in the new APK is not higher than the installed version.

## Plan

1.  **Version Increment**: Implement an automatic increment mechanism for `versionCode` and `versionName` in `build.gradle.kts`.
2.  **Signing Configuration**: Ensure `debug` builds are consistently signed with the standard Android debug keystore and `release` builds (if generated) are handled by the GitHub Actions pipeline using a consistent strategy.
3.  **Clean Build Protocol**: Ensure the CI/CD pipeline clears previous artifacts to prevent cached signatures.
