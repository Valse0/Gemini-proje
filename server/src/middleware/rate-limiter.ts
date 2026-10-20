/**
 * Lightweight in-memory rate limiter for backend API
 * Protects against excessive requests
 */

import { Request, Response, NextFunction } from 'express';

interface RateRecord {
  count: number;
  resetTime: number;
}

const windowMs = 60 * 1000; // 1 minute window
const maxRequestsPerWindow = 60; // 60 requests per minute
const ipRecords = new Map<string, RateRecord>();

// Cleanup stale records periodically
setInterval(() => {
  const now = Date.now();
  for (const [ip, record] of ipRecords.entries()) {
    if (now > record.resetTime) {
      ipRecords.delete(ip);
    }
  }
}, 30000);

export function rateLimiter(req: Request, res: Response, next: NextFunction): void {
  const clientIp = req.ip || req.socket.remoteAddress || 'unknown';
  const now = Date.now();

  let record = ipRecords.get(clientIp);
  if (!record || now > record.resetTime) {
    record = { count: 1, resetTime: now + windowMs };
    ipRecords.set(clientIp, record);
    return next();
  }

  record.count += 1;
  if (record.count > maxRequestsPerWindow) {
    res.status(429).json({
      error: {
        code: 'RATE_LIMIT_EXCEEDED',
        message: 'Too many translation requests. Please slow down.',
      },
    });
    return;
  }

  next();
}
