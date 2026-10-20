/**
 * Server Entry Point
 */

import { app } from './app.js';
import { config } from './config.js';

app.listen(config.port, config.host, () => {
  console.log(`[Luma Backend] Server listening at http://${config.host}:${config.port}`);
  console.log(`[Luma Backend] Active provider: ${config.activeProvider} (Gemini key: ${config.hasGeminiKey ? 'configured' : 'fallback-mock'})`);
});
