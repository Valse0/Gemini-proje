/**
 * Server Configuration for Luma Translate
 */
import dotenv from 'dotenv';
dotenv.config();

export const config = {
  port: parseInt(process.env.PORT || '3000', 10),
  host: process.env.HOST || '0.0.0.0',
  environment: process.env.NODE_ENV || 'development',
  maxTextLength: parseInt(process.env.MAX_TEXT_LENGTH || '2000', 10),
  providerTimeoutMs: parseInt(process.env.PROVIDER_TIMEOUT_MS || '10000', 10),
  activeProvider: process.env.TRANSLATION_PROVIDER || 'gemini',
  geminiModel: 'gemini-3.8-flash',
  hasGeminiKey: Boolean(process.env.GEMINI_API_KEY && process.env.GEMINI_API_KEY !== 'MY_GEMINI_API_KEY'),
};
