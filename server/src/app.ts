/**
 * Express Application Configuration
 */

import express from 'express';
import { translateRouter } from './routes/translate.js';
import { healthRouter } from './routes/health.js';

export const app = express();

// Body parser with 500kb limit (reject oversized requests)
app.use(express.json({ limit: '500kb' }));

// Mount routes
app.use('/', healthRouter);
app.use('/v1', translateRouter);

// Fallback 404 handler
app.use((req, res) => {
  res.status(404).json({
    error: {
      code: 'NOT_FOUND',
      message: `Cannot ${req.method} ${req.path}`,
    },
  });
});
