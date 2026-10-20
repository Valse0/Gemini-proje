# Luma Translate — Phase 3 (On-Device OCR & Bounding Box Recognition) Implementation Plan

Implementation plan for integrating high-precision on-device OCR using Google ML Kit and client-side recognition primitives, supporting Japanese, Korean, Chinese, and Latin scripts with localized bounding box overlay projections and custom image testing.

## User Review & Critical Decisions

> [!IMPORTANT]
> The following architectural decisions have been confirmed through user feedback and will govern Phase 3 implementation:

- **Confirmed Decision 1 (Bounding Box Visualization)**: Detected text regions and their corresponding translations will be highlighted directly over the original screen image using calibrated bounding boxes, allowing users to see translations in-place atop speech bubbles and dialogue windows.
- **Confirmed Decision 2 (Dynamic Recognizer Lifecycle)**: ML Kit recognizer instances will be dynamically instantiated and cached based on the active source language (`JapaneseTextRecognizerOptions`, `KoreanTextRecognizerOptions`, `ChineseTextRecognizerOptions`, and default Latin options), releasing memory when idle or when switching languages.
- **Confirmed Decision 3 (Corpus & Custom Upload Tester)**: The developer test console will feature both pre-loaded high-fidelity corpus samples (Manga speech bubbles, RPG dialogue boxes, game menus) and a custom drag-and-drop image uploader to test any screenshot directly with client-side OCR and backend translation.

---

## 1. Overview & Core Concept

- **What It Does**: Phase 3 brings localized text extraction to Luma Translate. The client processes screen imagery entirely on-device to detect text blocks, lines, and bounding coordinates without uploading raw pixels. Extracted text blocks are normalized, mapped to their screen coordinates, and dispatched to the Luma backend for translation.
- **Target Audience / Persona**: Gamers playing untranslated Japanese/Korean/Chinese games, manga readers reading raw scans, and developers verifying OCR bounding accuracy and translation round-trips.
- **Key Value**: 100% private on-device vision processing that protects user screen content while delivering localized in-place translations.

---

## 2. User Experience & Visual Design

### Key User Flows
1. **Corpus & Image Selection Flow**:
   - Developer/user chooses from curated test fixtures (Japanese RPG dialogue, Manga vertical speech bubble, Korean MMORPG quest prompt, Chinese settings menu) or uploads their own screenshot.
2. **On-Device OCR Processing Flow**:
   - User triggers **Run On-Device OCR**.
   - Client vision engine scans the image locally and identifies all discrete text blocks and their $(x, y, \text{width}, \text{height})$ bounding boxes.
   - Text blocks are listed with confidence scores and character counts.
3. **In-Place Overlay & Translation Flow**:
   - Detected text regions are highlighted with glowing subtle borders on the canvas.
   - User can click any individual text box or click **Translate All Detected Blocks**.
   - Bounding boxes smoothly transition from original text to translated text overlays, preserving the layout of the underlying screen.

### Visual Identity & Theme
- **Theme & Colors**:
  - Deep slate backdrop (`#0B0F19`) with high-contrast hairline borders (`#1F2937`)
  - Detected text bounding box: Subtle cobalt outline (`#3B82F6` with 15% opacity fill)
  - Active / selected bounding box: Bright electric blue (`#60A5FA` with 25% opacity fill)
  - Translated overlay card: Deep obsidian (`rgba(11, 15, 25, 0.92)`) with clean readable typography
- **Typography & Metrics**:
  - Monospace tabular numerals (`JetBrains Mono`, `tabular-nums`) for coordinates, character count, and OCR execution latency in ms.
  - Zero decorative pill boxes; clean inline metadata separated by typographic dots (`·`).

---

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: Dynamic ML Kit Model Instantiation vs Monolithic Loading**
  - *Chosen Approach*: Lazy-load specific language recognizers (`JapaneseTextRecognizerOptions` for Japanese, `KoreanTextRecognizerOptions` for Korean, etc.) only when selected as source language, and call `close()` when switching.
  - *Why*: Reduces Android memory footprint significantly, preventing background process kills during heavy game execution.
  - *Alternatives Considered*: Keeping all 4 language models active concurrently (rejected: excessive RAM usage on mobile devices).
- **Decision 2: Client-Side OCR in Web Simulator using Web OCR Engine**
  - *Chosen Approach*: Equip the web test console with a local browser-based optical character recognition canvas engine (Tesseract.js / Canvas vision engine) that mirrors the exact bounding box and text extraction contract of Android ML Kit, paired with the Android native Kotlin ML Kit implementation in `android/`.
  - *Why*: Enables immediate, interactive verification of the entire OCR-to-translation pipeline directly in the AI Studio web environment without requiring a physical Android phone connected via ADB.
  - *Alternatives Considered*: Mocking OCR outputs with hardcoded boxes (rejected: doesn't allow testing custom user-uploaded images).
- **Decision 3: Bounding Box Geometry Normalization**
  - *Chosen Approach*: Store coordinates as normalized ratios ($0.0$ to $1.0$) relative to image width and height.
  - *Why*: Ensures overlay boxes scale perfectly across different screen resolutions and display densities without coordinate drift.

---

## 4. Technical Architecture & Data Strategy

### System Architecture & OCR Data Flow

```text
┌─────────────────────────────────────────────────────────────────┐
│                    On-Device Vision Pipeline                    │
└─────────────────────────────────────────────────────────────────┘
                                │
        ┌───────────────────────┴───────────────────────┐
        ▼                                               ▼
┌──────────────────────────┐                ┌───────────────────────────┐
│    Android Kotlin Native │                │     Web Test Console      │
│     (com.luma.translate) │                │     (Developer Studio)    │
├──────────────────────────┤                ├───────────────────────────┤
│ • ML Kit TextRecognition │                │ • Client-Side Canvas OCR  │
│ • Japanese/Korean/Chinese│                │ • Custom Image Upload     │
│ • Normalized Rect Bounds │                │ • Bounding Box Projection │
│ • Zero Image Upload      │                │ • In-Place Overlay Canvas │
└─────────────┬────────────┘                └─────────────┬─────────────┘
              │                                           │
              │ Extracted text blocks + normalized rects  │
              └───────────────────┬───────────────────────┘
                                  ▼
                    ┌───────────────────────────┐
                    │  OcrResult Data Contract  │
                    ├───────────────────────────┤
                    │ • textBlocks: Block[]     │
                    │ • boundingBox: Rect       │
                    │ • confidence: number      │
                    │ • language: string        │
                    └─────────────┬─────────────┘
                                  ▼
                    ┌───────────────────────────┐
                    │     Luma Backend API      │
                    │   (POST /v1/translate)    │
                    │  Text-only payload        │
                    └───────────────────────────┘
```

### Data Contract & OCR Schema

- **OCR Text Block Structure**:
  - `id`: string
  - `text`: string
  - `normalizedRect`: `{ x: number, y: number, width: number, height: number }` (all $0.0$ to $1.0$)
  - `translatedText` (optional): string
  - `confidence`: number (0 to 1)
  - `language`: string ("ja", "ko", "zh", "en")

---

## Execution Sub-Tasks (Planned for Phase 3 Execution)

1. **Native Android OCR Architecture (`android/`)**:
   - Implement `OcrManager.kt` and `LanguageRecognizerFactory.kt` under `com.luma.translate.ocr`.
   - Implement bounding box coordinate normalization and safe lifecycle release.
   - Add unit tests verifying `OcrResult` contract and error containment.
2. **Interactive OCR & Bounding Box Visualizer (`src/components/OcrVisualizer.tsx`)**:
   - Build high-precision canvas overlay projector displaying interactive bounding boxes over sample and custom uploaded screenshots.
   - Support custom image drag-and-drop with client-side OCR extraction.
   - Connect OCR text blocks directly to `POST /v1/translate` for single-click block translation or batch translation.
3. **Console Integration**:
   - Add dedicated **On-Device OCR & Vision** tab in the developer console.
   - Verify performance metrics (OCR detection time, network latency, translation render time).
