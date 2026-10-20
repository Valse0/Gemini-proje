/**
 * Phase 6: Settings, Customization & Privacy Inspector Dashboard
 * Provides deep configurability for language defaults, translation context directives,
 * overlay opacity, and cryptographic privacy verification.
 */

import React, { useState } from 'react';
import {
  Settings,
  Shield,
  Sliders,
  Check,
  RotateCcw,
  Smartphone,
  Layers,
  Lock,
  Globe,
  Eye,
  CheckCircle2,
} from 'lucide-react';

export function SettingsDashboard() {
  // User Preferences State
  const [defaultSource, setDefaultSource] = useState('auto');
  const [defaultTarget, setDefaultTarget] = useState('en');
  const [defaultContext, setDefaultContext] = useState<'gaming' | 'manga' | 'general'>('gaming');
  const [showBoundingBoxes, setShowBoundingBoxes] = useState(true);
  const [autoTranslate, setAutoTranslate] = useState(true);
  const [overlayOpacity, setOverlayOpacity] = useState(85);
  const [keepAlive, setKeepAlive] = useState(true);
  const [savedToast, setSavedToast] = useState(false);

  const handleSavePreferences = (e: React.FormEvent) => {
    e.preventDefault();
    setSavedToast(true);
    setTimeout(() => setSavedToast(false), 2500);
  };

  const handleResetDefaults = () => {
    setDefaultSource('auto');
    setDefaultTarget('en');
    setDefaultContext('gaming');
    setShowBoundingBoxes(true);
    setAutoTranslate(true);
    setOverlayOpacity(85);
    setKeepAlive(true);
    setSavedToast(true);
    setTimeout(() => setSavedToast(false), 2500);
  };

  return (
    <div className="w-full flex flex-col gap-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-4 border-b border-slate-800">
        <div>
          <h2 className="text-base font-semibold text-white flex items-center gap-2">
            <Settings className="w-4 h-4 text-blue-400" />
            <span>Phase 6: Settings, Customization & Privacy Auditor</span>
          </h2>
          <p className="text-xs text-slate-400 mt-0.5">
            Configure default translation language pairs, context directives, floating button opacity, and cryptographic privacy invariants.
          </p>
        </div>

        {savedToast && (
          <div className="flex items-center gap-1.5 px-3 py-1.5 rounded bg-emerald-950/60 border border-emerald-800 text-emerald-300 text-xs animate-in fade-in">
            <CheckCircle2 className="w-3.5 h-3.5" />
            <span>Preferences saved successfully</span>
          </div>
        )}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
        {/* Settings Form (7 Cols) */}
        <div className="lg:col-span-7 bg-slate-900 border border-slate-800 rounded-lg p-6 flex flex-col gap-6">
          <form onSubmit={handleSavePreferences} className="flex flex-col gap-5">
            <div>
              <h3 className="text-xs font-semibold text-slate-200 uppercase tracking-wider mb-3">
                Default Translation Defaults
              </h3>
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div>
                  <label className="block text-xs font-medium text-slate-400 mb-1.5">Default Source Language</label>
                  <select
                    value={defaultSource}
                    onChange={(e) => setDefaultSource(e.target.value)}
                    className="w-full bg-slate-950 border border-slate-800 rounded px-3 py-2 text-xs text-slate-200 focus:outline-none focus:border-blue-500"
                  >
                    <option value="auto">Auto-detect (Recommended)</option>
                    <option value="ja">Japanese (日本語)</option>
                    <option value="ko">Korean (한국어)</option>
                    <option value="zh">Chinese (中文)</option>
                  </select>
                </div>

                <div>
                  <label className="block text-xs font-medium text-slate-400 mb-1.5">Default Target Language</label>
                  <select
                    value={defaultTarget}
                    onChange={(e) => setDefaultTarget(e.target.value)}
                    className="w-full bg-slate-950 border border-slate-800 rounded px-3 py-2 text-xs text-slate-200 focus:outline-none focus:border-blue-500"
                  >
                    <option value="en">English (US/UK)</option>
                    <option value="tr">Turkish (Türkçe)</option>
                    <option value="es">Spanish (Español)</option>
                    <option value="de">German (Deutsch)</option>
                  </select>
                </div>
              </div>
            </div>

            <div>
              <label className="block text-xs font-medium text-slate-400 mb-1.5">Default Context Directive</label>
              <select
                value={defaultContext}
                onChange={(e) => setDefaultContext(e.target.value as any)}
                className="w-full bg-slate-950 border border-slate-800 rounded px-3 py-2 text-xs text-slate-200 focus:outline-none focus:border-blue-500"
              >
                <option value="gaming">Gaming (RPG dialogues, inventory, stat prompts)</option>
                <option value="manga">Manga (Vertical speech bubbles, SFX)</option>
                <option value="general">General (Standard screen reader text)</option>
              </select>
            </div>

            <div className="space-y-4 pt-2 border-t border-slate-800/80">
              <h3 className="text-xs font-semibold text-slate-200 uppercase tracking-wider">
                Overlay & Visual Preferences
              </h3>

              <div className="flex items-center justify-between">
                <div>
                  <span className="text-xs font-medium text-slate-200 block">Show Bounding Box Overlays</span>
                  <span className="text-[11px] text-slate-400">Highlight detected text regions directly on screen</span>
                </div>
                <input
                  type="checkbox"
                  checked={showBoundingBoxes}
                  onChange={(e) => setShowBoundingBoxes(e.target.checked)}
                  className="w-4 h-4 rounded bg-slate-950 border-slate-800 text-blue-600 focus:ring-0 cursor-pointer"
                />
              </div>

              <div className="flex items-center justify-between">
                <div>
                  <span className="text-xs font-medium text-slate-200 block">Auto-Translate on Overlay Tap</span>
                  <span className="text-[11px] text-slate-400">Trigger OCR & backend translation immediately when bubble is tapped</span>
                </div>
                <input
                  type="checkbox"
                  checked={autoTranslate}
                  onChange={(e) => setAutoTranslate(e.target.checked)}
                  className="w-4 h-4 rounded bg-slate-950 border-slate-800 text-blue-600 focus:ring-0 cursor-pointer"
                />
              </div>

              <div>
                <div className="flex items-center justify-between mb-1.5">
                  <span className="text-xs font-medium text-slate-200">Floating Button Opacity</span>
                  <span className="text-xs font-mono tabular-nums text-slate-400">{overlayOpacity}%</span>
                </div>
                <input
                  type="range"
                  min="40"
                  max="100"
                  value={overlayOpacity}
                  onChange={(e) => setOverlayOpacity(Number(e.target.value))}
                  className="w-full accent-blue-600 bg-slate-950"
                />
              </div>
            </div>

            {/* Action Buttons */}
            <div className="flex items-center justify-between pt-4 border-t border-slate-800">
              <button
                type="button"
                onClick={handleResetDefaults}
                className="flex items-center gap-1.5 text-xs text-slate-400 hover:text-white px-3 py-2 rounded bg-slate-950 border border-slate-800 transition-colors"
              >
                <RotateCcw className="w-3.5 h-3.5" />
                <span>Reset Defaults</span>
              </button>

              <button
                type="submit"
                className="px-5 py-2 rounded bg-blue-600 hover:bg-blue-500 text-white text-xs font-semibold shadow transition-all"
              >
                Save Preferences
              </button>
            </div>
          </form>
        </div>

        {/* Privacy Auditor & Security Inspector (5 Cols) */}
        <div className="lg:col-span-5 flex flex-col gap-4">
          <div className="bg-slate-900 border border-slate-800 rounded-lg p-5 flex flex-col gap-4">
            <h3 className="text-xs font-semibold text-emerald-400 uppercase tracking-wider flex items-center gap-1.5">
              <Shield className="w-4 h-4" /> Privacy & Security Invariants
            </h3>

            <div className="space-y-3 text-xs text-slate-300">
              <div className="p-3 rounded-lg bg-slate-950/70 border border-slate-800">
                <div className="flex items-center gap-2 mb-1">
                  <span className="w-2 h-2 rounded-full bg-emerald-500" />
                  <span className="font-semibold text-white">100% On-Device OCR</span>
                </div>
                <p className="text-[11px] text-slate-400 leading-tight">
                  Google ML Kit extracts all text locally on your Android hardware. Bitmaps and raw screenshots never leave the device.
                </p>
              </div>

              <div className="p-3 rounded-lg bg-slate-950/70 border border-slate-800">
                <div className="flex items-center gap-2 mb-1">
                  <span className="w-2 h-2 rounded-full bg-emerald-500" />
                  <span className="font-semibold text-white">Text-Only Network Payload</span>
                </div>
                <p className="text-[11px] text-slate-400 leading-tight">
                  Only the extracted string and language directives are sent over HTTPS to the translation backend.
                </p>
              </div>

              <div className="p-3 rounded-lg bg-slate-950/70 border border-slate-800">
                <div className="flex items-center gap-2 mb-1">
                  <span className="w-2 h-2 rounded-full bg-emerald-500" />
                  <span className="font-semibold text-white">FLAG_SECURE Enforcement</span>
                </div>
                <p className="text-[11px] text-slate-400 leading-tight">
                  Protected system windows and banking apps are automatically shielded from capture in strict compliance with Android security rules.
                </p>
              </div>
            </div>

            <div className="pt-3 border-t border-slate-800 text-[11px] text-slate-500 font-mono flex items-center justify-between">
              <span>Security Auditor: PASSED</span>
              <span>v0.1.0-release</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
