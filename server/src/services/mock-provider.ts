/**
 * Mock Translation Provider for Local Testing and Offline Validation
 */

import { TranslationProvider, TranslationRequest, TranslationResponse } from './translation-provider.js';

export class MockProvider implements TranslationProvider {
  public readonly name = 'mock';

  public getSupportedLanguages(): string[] {
    return ['auto', 'ja', 'ko', 'zh', 'en', 'tr', 'es', 'de', 'fr'];
  }

  public async translate(request: TranslationRequest): Promise<TranslationResponse> {
    const startTime = Date.now();
    const { text, sourceLanguage, targetLanguage } = request;

    // Small simulated network jitter
    await new Promise((resolve) => setTimeout(resolve, 25));

    // Basic mock dictionary for testing
    let translatedText = `[${targetLanguage.toUpperCase()}] ${text}`;
    if (text === 'こんにちは' && targetLanguage === 'en') {
      translatedText = 'Hello';
    } else if (text === 'こんにちは' && targetLanguage === 'tr') {
      translatedText = 'Merhaba';
    } else if (text === '冒険の準備はできましたか？' && targetLanguage === 'en') {
      translatedText = 'Are you ready for adventure?';
    } else if (text === 'Game Over' && targetLanguage === 'tr') {
      translatedText = 'Oyun Bitti';
    }

    return {
      translatedText,
      detectedSourceLanguage: sourceLanguage === 'auto' ? 'ja' : sourceLanguage,
      targetLanguage,
      provider: this.name,
      durationMs: Date.now() - startTime,
    };
  }
}
