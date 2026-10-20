# Gradle Wrapper Repair Plan

The existing `gradle-wrapper.jar` is corrupted, causing build failures in the GitHub Actions pipeline.

## Plan

1.  **Delete Corrupted Wrapper Files**: Clean up the corrupted `android/gradle/wrapper/` directory.
2.  **Force Re-download**: Update the GitHub Actions workflow to run the `gradle wrapper` command *before* executing the build commands. This forces the generation of a fresh, valid `gradle-wrapper.jar` and `gradle-wrapper.properties` based on the environment's current Gradle version.
3.  **Verification**: Update the workflow to ensure `chmod +x gradlew` is executed.
4.  **Test**: Verify pipeline execution.
