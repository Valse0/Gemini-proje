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
    const provider = getActiveProvider();

    // Call provider with bounded execution
    const result = await provider.translate({
      text,
      sourceLanguage,
      targetLanguage,
      context,
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
  });
});
