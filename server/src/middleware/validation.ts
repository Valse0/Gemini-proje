/**
 * Request Validation Middleware for Luma Translate API
 */

import { Request, Response, NextFunction } from 'express';
import { config } from '../config.js';

const VALID_LANG_REGEX = /^[a-z]{2,3}(-[A-Z]{2})?$/;

export function validateTranslateRequest(req: Request, res: Response, next: NextFunction): void {
  const { text, sourceLanguage, targetLanguage } = req.body || {};

  // Check text presence and type
  if (typeof text !== 'string' || text.trim().length === 0) {
    res.status(400).json({
      error: {
        code: 'INVALID_TEXT',
        message: 'Request body must include a non-empty "text" string.',
      },
    });
    return;
  }

  // Check text length
  if (text.length > config.maxTextLength) {
    res.status(400).json({
      error: {
        code: 'TEXT_TOO_LONG',
        message: `Text length (${text.length}) exceeds the maximum allowed limit of ${config.maxTextLength} characters.`,
      },
    });
    return;
  }

  // Check targetLanguage
  if (typeof targetLanguage !== 'string' || !VALID_LANG_REGEX.test(targetLanguage.trim())) {
    res.status(400).json({
      error: {
        code: 'INVALID_TARGET_LANGUAGE',
        message: 'A valid ISO target language code is required (e.g. "en", "tr", "es").',
      },
    });
    return;
  }

  // Check sourceLanguage
  if (
    typeof sourceLanguage !== 'string' ||
    (sourceLanguage !== 'auto' && !VALID_LANG_REGEX.test(sourceLanguage.trim()))
  ) {
    res.status(400).json({
      error: {
        code: 'INVALID_SOURCE_LANGUAGE',
        message: 'A valid ISO source language code or "auto" is required (e.g. "ja", "ko", "auto").',
      },
    });
    return;
  }

  next();
}
