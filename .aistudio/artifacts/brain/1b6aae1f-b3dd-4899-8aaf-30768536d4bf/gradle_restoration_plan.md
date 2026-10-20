# Gradle Wrapper Restoration Plan

The `android/` directory is missing critical Gradle wrapper files (`gradlew`, `gradle/wrapper/gradle-wrapper.jar`, `gradle/wrapper/gradle-wrapper.properties`). This prevents the CI/CD pipeline from building the APK.

## Plan

1.  **Generate Fresh Wrapper**: Create a temporary Android project structure in the workspace to generate standard, production-ready Gradle wrapper files.
2.  **Restore Files**: Copy the missing files from the temporary project to the existing `android/` directory.
3.  **Update CI/CD**: Verify `build-apk.yml` correctly targets these restored files.
4.  **Cleanup**: Delete the temporary project.
