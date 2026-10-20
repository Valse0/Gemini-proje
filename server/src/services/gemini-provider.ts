/**
 * Gemini Translation Provider Implementation
 * Uses @google/genai SDK with gemini-3.8-flash
 */

import { GoogleGenAI } from '@google/genai';
import { TranslationProvider, TranslationRequest, TranslationResponse } from './translation-provider.js';

export class GeminiProvider implements TranslationProvider {
  public readonly name = 'gemini';
  private ai: GoogleGenAI | null = null;
  private readonly model = 'gemini-3.8-flash';

  constructor() {
    const apiKey = process.env.GEMINI_API_KEY;
    if (apiKey && apiKey !== 'MY_GEMINI_API_KEY') {
      this.ai = new GoogleGenAI({
        apiKey,
        httpOptions: {
          headers: {
            'User-Agent': 'aistudio-build',
          },
        },
      });
    }
  }

  public getSupportedLanguages(): string[] {
    return ['auto', 'ja', 'ko', 'zh', 'en', 'tr', 'es', 'de', 'fr', 'pt', 'it', 'ru', 'ar', 'id', 'vi', 'th'];
  }

  public async translate(request: TranslationRequest): Promise<TranslationResponse> {
    const startTime = Date.now();

    if (!this.ai) {
      // If key is not configured, give clear and safe diagnostic
      throw new Error('PROVIDER_UNAVAILABLE: Gemini API key is not configured in backend environment.');
    }

    const { text, sourceLanguage, targetLanguage, context = 'general' } = request;

    let contextDirective = '';
    if (context === 'gaming') {
      contextDirective = 'The text is from a video game UI or dialogue. Preserve game terminology, character names, equipment names, and concise phrasing.';
    } else if (context === 'manga') {
      contextDirective = 'The text is from a manga/comic speech bubble. Preserve dramatic tone, sound effects (onomatopoeia), and natural character speech.';
    }

    const systemInstruction = `You are the specialized translation engine for Luma Translate.
Your sole job is to translate text from ${sourceLanguage === 'auto' ? 'the detected source language' : sourceLanguage} to ${targetLanguage}.
${contextDirective}
Rules:
1. Return ONLY the direct translation.
2. Do not add explanations, conversational comments, translator notes, or quotes.
3. Preserve original layout, casing style, punctuation, and line breaks where appropriate.
4. If source text is already in the target language, return it accurately.`;

    try {
      const response = await this.ai.models.generateContent({
        model: this.model,
        contents: text,
        config: {
          systemInstruction,
          temperature: 0.3,
        },
      });

      const translatedText = (response.text || '').trim();
      const durationMs = Date.now() - startTime;

      return {
        translatedText,
        detectedSourceLanguage: sourceLanguage === 'auto' ? 'detected' : sourceLanguage,
        targetLanguage,
        provider: this.name,
        durationMs,
      };
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Unknown provider error';
      // Normalize error safely without leaking secrets
      throw new Error(`PROVIDER_ERROR: Failed to translate via Gemini provider: ${message}`);
    }
  }
}
