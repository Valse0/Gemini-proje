/**
 * Translation Route Handler (POST /v1/translate)
 */

import { Router, Request, Response } from 'express';
import { validateTranslateRequest } from '../middleware/validation.js';
import { rateLimiter } from '../middleware/rate-limiter.js';
import { GeminiProvider } from '../services/gemini-provider.js';
import { MockProvider } from '../services/mock-provider.js';
import { TranslationProvider } from '../services/translation-provider.js';
import { config } from '../config.js';

export const translateRouter = Router();

// Instantiate provider based on environment/config
const geminiProvider = new GeminiProvider();
const mockProvider = new MockProvider();

// High-performance in-memory translation cache for repeated game/manga dialogues
interface CacheEntry {
  translatedText: string;
  detectedSourceLanguage: string;
  durationMs: number;
  timestamp: number;
}
const translationCache = new Map<string, CacheEntry>();
const MAX_CACHE_SIZE = 500;

function getCacheKey(text: string, src: string, tgt: string, context?: string): string {
  return `${src}:${tgt}:${context || 'general'}:${text.trim()}`;
}

function getActiveProvider(): TranslationProvider {
  // If provider forced or no Gemini key, use mock in dev / testing
  if (config.activeProvider === 'mock' || !config.hasGeminiKey) {
    return mockProvider;
  }
  return geminiProvider;
}

translateRouter.post('/translate', rateLimiter, validateTranslateRequest, async (req: Request, res: Response) => {
  try {
    const { text, sourceLanguage, targetLanguage, context } = req.body;
    const cacheKey = getCacheKey(text, sourceLanguage, targetLanguage, context);

    // Check cache first for lightning-fast zero-network-overhead response on repeated lines
    const cached = translationCache.get(cacheKey);
    if (cached) {
      res.json({
        translatedText: cached.translatedText,
        detectedSourceLanguage: cached.detectedSourceLanguage,
        targetLanguage,
        provider: `${getActiveProvider().name}-cached`,
        durationMs: 1, // Ultra-fast cache hit
      });
      return;
    }

    const provider = getActiveProvider();

    // Call provider with bounded execution
    const result = await provider.translate({
      text,
      sourceLanguage,
      targetLanguage,
      context,
    });

    // Store in cache (evict oldest if cache exceeds max size)
    if (translationCache.size >= MAX_CACHE_SIZE) {
      const firstKey = translationCache.keys().next().value;
      if (firstKey) {
        translationCache.delete(firstKey);
      }
    }
    translationCache.set(cacheKey, {
      translatedText: result.translatedText,
      detectedSourceLanguage: result.detectedSourceLanguage,
      durationMs: result.durationMs,
      timestamp: Date.now(),
    });

    res.json(result);
  } catch (error: unknown) {
    const errMessage = error instanceof Error ? error.message : 'Translation failed';
    res.status(502).json({
      error: {
        code: 'TRANSLATION_FAILED',
        message: errMessage,
      },
    });
  }
});

// Endpoint to list supported languages and active provider info (non-sensitive)
translateRouter.get('/provider-info', (req: Request, res: Response) => {
  const provider = getActiveProvider();
  res.json({
    activeProvider: provider.name,
    supportedLanguages: provider.getSupportedLanguages(),
    maxTextLength: config.maxTextLength,
    geminiKeyConfigured: config.hasGeminiKey,
    cacheSize: translationCache.size,
  });
});
