/**
 * Replaceable Translation Provider Interface
 * As specified in docs/ARCHITECTURE.md and AGENTS.md
 */

export interface TranslationRequest {
  text: string;
  sourceLanguage: string; // ISO-639-1 e.g. "ja", "ko", "zh", or "auto"
  targetLanguage: string; // ISO-639-1 e.g. "en", "tr", "es", "de", "fr"
  context?: 'general' | 'manga' | 'gaming';
}

export interface TranslationResponse {
  translatedText: string;
  detectedSourceLanguage: string;
  targetLanguage: string;
  provider: string;
  durationMs: number;
}

export interface TranslationProvider {
  readonly name: string;
  translate(request: TranslationRequest): Promise<TranslationResponse>;
  getSupportedLanguages(): string[];
}
