/**
 * Full-Stack Entry Point for AI Studio
 * Hosts the Express backend API (/v1/translate, /health)
 * and mounts the Vite dev server for the Developer Test Console
 */

import express from 'express';
import path from 'path';
import { fileURLToPath } from 'url';
import { createServer as createViteServer } from 'vite';
import { translateRouter } from './server/src/routes/translate.js';
import { healthRouter } from './server/src/routes/health.js';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

async function startServer() {
  const app = express();
  const PORT = parseInt(process.env.PORT || '3000', 10);
  const isProduction = process.env.NODE_ENV === 'production';

  // Request body parsing
  app.use(express.json({ limit: '500kb' }));

  // Mount backend API routes (as specified in docs/ARCHITECTURE.md and docs/ROADMAP.md)
  app.use('/', healthRouter);
  app.use('/v1', translateRouter);

  if (!isProduction) {
    // Mount Vite middleware in development
    const vite = await createViteServer({
      server: { middlewareMode: true },
      appType: 'spa',
    });
    app.use(vite.middlewares);
  } else {
    // Serve static build in production
    app.use(express.static(path.resolve(__dirname, 'dist')));
    app.get('*', (req, res) => {
      res.sendFile(path.resolve(__dirname, 'dist', 'index.html'));
    });
  }

  app.listen(PORT, '0.0.0.0', () => {
    console.log(`[Luma Fullstack] Running on http://0.0.0.0:${PORT}`);
    console.log(`[Luma API] Endpoints available: POST /v1/translate, GET /health, GET /v1/provider-info`);
  });
}

startServer().catch((err) => {
  console.error('[Luma Fullstack] Startup error:', err);
  process.exit(1);
});
