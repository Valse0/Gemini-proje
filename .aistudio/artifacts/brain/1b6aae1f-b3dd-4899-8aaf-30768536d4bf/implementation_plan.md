# Luma Translate — Phase 8 Implementation Plan: GitHub Actions Workflow Fix

Implementation plan for correcting the GitHub Actions workflow by removing the deprecated `setup-android` action and relying on standard Gradle and Ubuntu runner environment for APK and AAB compilation.

## User Review & Critical Decisions

> [!IMPORTANT]
> The following decision has been confirmed through user feedback:
> - **Action Selection**: Use standard Gradle build action without explicit `setup-android` action to avoid `sdkmanager` deprecation errors (`Warning: Failed to find package 'tools'`).

---

## 1. Overview & Core Concept

- **What It Does**: Updates `.github/workflows/build-apk.yml` to remove the faulty `android-actions/setup-android` step and cleanly compile the Android project using standard Gradle wrapper (`./gradlew assembleDebug` and `./gradlew bundleRelease`).
- **Target Audience / Persona**: Developer and CI/CD automation pipeline.
- **Key Value**: Clean, reliable GitHub Actions builds without SDK manager package failures.

---

## 2. Technical Architecture & Workflow Steps

```text
┌─────────────────────────────────────────────────────────────┐
│                 GitHub Actions Runner (Ubuntu)              │
└──────────────────────────────┬──────────────────────────────┘
                               │
       ┌───────────────────────┴───────────────────────┐
       ▼                                               ▼
┌───────────────────────────┐           ┌───────────────────────────┐
│     Checkout & Setup      │           │     Standard Gradle       │
├───────────────────────────┤           ├───────────────────────────┤
│ • actions/checkout@v4     │           │ • actions/setup-java@v4   │
│ • Node.js 20              │           │ • ./gradlew assembleDebug │
└───────────────────────────┘           └─────────────┬─────────────┘
                                                      │
                                                      ▼
                                        ┌───────────────────────────┤
                                        │    Upload Artifacts       │
                                        ├───────────────────────────┤
                                        │ • Debug APK               │
                                        │ • Release AAB             │
                                        └───────────────────────────┘
```

---

## Execution Steps (Implemented in this turn)

1. Update `.github/workflows/build-apk.yml` to remove `android-actions/setup-android`.
2. Test compilation flow and verify repository state.
