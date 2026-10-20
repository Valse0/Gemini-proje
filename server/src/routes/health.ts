/**
 * Health and Diagnostics Route Handler
 */

import { Router, Request, Response } from 'express';
import { config } from '../config.js';

export const healthRouter = Router();

healthRouter.get('/health', (req: Request, res: Response) => {
  res.json({
    status: 'ok',
    service: 'luma-translate-backend',
    version: '0.1.0',
    uptimeSeconds: Math.floor(process.uptime()),
    timestamp: new Date().toISOString(),
    environment: config.environment,
    provider: {
      type: config.hasGeminiKey ? 'gemini' : 'mock-fallback',
      model: config.geminiModel,
    },
  });
});
