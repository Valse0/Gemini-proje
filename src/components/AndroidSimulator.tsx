/**
 * Android Device Simulator for Luma Translate Phase 1 - Phase 4
 * Replicates the Jetpack Compose HomeScreen, MediaProjection official consent flow,
 * Android 14+ Foreground Service with Stop control, and FLAG_SECURE protection.
 */

import React, { useState } from 'react';
import {
  Shield,
  Layers,
  ArrowRightLeft,
  X,
  Copy,
  Check,
  Sparkles,
  Wifi,
  Battery,
  AlertTriangle,
  Cast,
  Lock,
  Sun,
  Moon,
  StopCircle,
} from 'lucide-react';

interface AndroidSimulatorProps {
  onBackendTranslate: (text: string, src: string, tgt: string, context: 'general' | 'manga' | 'gaming') => Promise<string>;
}

export function AndroidSimulator({ onBackendTranslate }: AndroidSimulatorProps) {
  // Mobile App State
  const [isDarkTheme, setIsDarkTheme] = useState(true);
  const [sessionActive, setSessionActive] = useState(false);
  const [hasOverlayPermission, setHasOverlayPermission] = useState(false);
  const [hasMediaProjectionConsent, setHasMediaProjectionConsent] = useState(false);
  const [showOverlayDialog, setShowOverlayDialog] = useState(false);
  const [showMediaProjectionDialog, setShowMediaProjectionDialog] = useState(false);
  const [showPrivacySheet, setShowPrivacySheet] = useState(false);
  const [isSecureSurfaceActive, setIsSecureSurfaceActive] = useState(false);
  const [securityBlockAlert, setSecurityBlockAlert] = useState<string | null>(null);

  const [sourceLang, setSourceLang] = useState('ja');
  const [targetLang, setTargetLang] = useState('en');
  const [context, setContext] = useState<'general' | 'manga' | 'gaming'>('gaming');

  // Floating Overlay & Translation State
  const [isTranslating, setIsTranslating] = useState(false);
  const [translatedResult, setTranslatedResult] = useState<string | null>(null);
  const [copied, setCopied] = useState(false);

  // Simulated screen content
  const sampleScene = {
    title: 'Fantasy Quest: Brave Chronicle',
    originalText: '「冒険の準備はできましたか？武器と薬草を忘れずに持っていこう。」',
    character: 'Guild Master Leo',
  };

  // Phase 4: Handle session start with official consent steps
  const handleStartSession = () => {
    if (!hasOverlayPermission) {
      setShowOverlayDialog(true);
    } else if (!hasMediaProjectionConsent) {
      setShowMediaProjectionDialog(true);
    } else {
      setSessionActive(!sessionActive);
      setTranslatedResult(null);
      setSecurityBlockAlert(null);
    }
  };

  const handleGrantOverlayPermission = () => {
    setHasOverlayPermission(true);
    setShowOverlayDialog(false);
    // Proceed to MediaProjection consent
    setShowMediaProjectionDialog(true);
  };

  const handleGrantMediaProjection = () => {
    setHasMediaProjectionConsent(true);
    setShowMediaProjectionDialog(false);
    setSessionActive(true);
  };

  const handleStopCapture = () => {
    setSessionActive(false);
    setTranslatedResult(null);
    setSecurityBlockAlert(null);
  };

  const handleTriggerOverlayTranslation = async () => {
    setSecurityBlockAlert(null);

    // Phase 4 Invariant: Respect FLAG_SECURE and DRM protection
    if (isSecureSurfaceActive) {
      setSecurityBlockAlert('Protected screen content detected (FLAG_SECURE). Screen capture is strictly blocked in compliance with Android security policies.');
      return;
    }

    setIsTranslating(true);
    try {
      const translated = await onBackendTranslate(sampleScene.originalText, sourceLang, targetLang, context);
      setTranslatedResult(translated);
    } catch {
      setTranslatedResult('Translation failed or backend unreachable');
    } finally {
      setIsTranslating(false);
    }
  };

  const handleCopy = () => {
    if (translatedResult) {
      navigator.clipboard.writeText(translatedResult);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center p-2 sm:p-6 w-full">
      {/* Device Toolbar & Phase 4 Simulation Controls */}
      <div className="mb-4 flex items-center justify-between w-full max-w-[380px] px-2 text-xs text-slate-400">
        <div className="flex items-center gap-2">
          <span className="font-medium text-slate-200">Pixel 8 (Android 14)</span>
          <span>·</span>
          <span>MediaProjection</span>
        </div>
        <div className="flex items-center gap-2">
          <button
            onClick={() => setIsSecureSurfaceActive(!isSecureSurfaceActive)}
            className={`flex items-center gap-1 px-2 py-1 rounded border text-[11px] transition-colors ${
              isSecureSurfaceActive
                ? 'bg-amber-950/60 border-amber-600 text-amber-300'
                : 'bg-slate-900 border-slate-800 text-slate-400 hover:text-white'
            }`}
            title="Toggle FLAG_SECURE test condition"
          >
            <Lock className="w-3 h-3" />
            <span>{isSecureSurfaceActive ? 'FLAG_SECURE: ON' : 'FLAG_SECURE: OFF'}</span>
          </button>

          <button
            onClick={() => setIsDarkTheme(!isDarkTheme)}
            className="p-1.5 rounded bg-slate-900 border border-slate-800 hover:text-white transition-colors"
            title="Toggle Light/Dark Theme"
          >
            {isDarkTheme ? <Sun className="w-3.5 h-3.5 text-amber-400" /> : <Moon className="w-3.5 h-3.5 text-blue-400" />}
          </button>
        </div>
      </div>

      {/* Realistic Mobile Device Frame */}
      <div
        className={`relative w-[360px] h-[730px] rounded-[44px] p-3 shadow-2xl border-4 transition-colors duration-300 ${
          isDarkTheme ? 'bg-slate-900 border-slate-700' : 'bg-slate-200 border-slate-300'
        }`}
      >
        {/* Device Outer Rim */}
        <div
          className={`relative w-full h-full rounded-[34px] overflow-hidden flex flex-col transition-colors duration-300 ${
            isDarkTheme ? 'bg-slate-950 text-slate-100' : 'bg-slate-50 text-slate-900'
          }`}
        >
          {/* Top Status Bar & Notch */}
          <div className="h-9 px-6 flex items-center justify-between text-[11px] font-mono tracking-tight shrink-0 select-none z-30">
            <span>12:00</span>
            {/* Camera cutout */}
            <div className="w-4 h-4 rounded-full bg-black border border-slate-800" />
            <div className="flex items-center gap-1.5">
              <Wifi className="w-3.5 h-3.5" />
              <Battery className="w-3.5 h-3.5" />
            </div>
          </div>

          {/* Android 14+ Foreground Service Active Notification Banner */}
          {sessionActive && (
            <div className="bg-slate-900/95 border-b border-slate-800 px-3.5 py-2 flex items-center justify-between text-xs z-30 animate-in fade-in duration-200">
              <div className="flex items-center gap-2">
                <span className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
                <div>
                  <span className="font-semibold text-white block text-[11px]">CaptureService Running</span>
                  <span className="text-[10px] text-slate-400">MediaProjection ready</span>
                </div>
              </div>
              <button
                onClick={handleStopCapture}
                className="flex items-center gap-1 px-2 py-1 rounded bg-rose-950/80 hover:bg-rose-900 border border-rose-800 text-rose-200 text-[10px] font-medium transition-colors"
                title="Stop Capture Service"
              >
                <StopCircle className="w-3 h-3 text-rose-400" />
                <span>Stop</span>
              </button>
            </div>
          )}

          {/* Screen Content: App View or Active Background Screen */}
          <div className="flex-1 flex flex-col p-4 overflow-y-auto relative">
            {/* App Header */}
            <div className="flex items-center justify-between mb-4">
              <div>
                <h1 className="text-xl font-bold tracking-tight text-blue-500">Luma Translate</h1>
                <p className="text-[11px] text-slate-400">Your Screen · Your Privacy</p>
              </div>
              <button
                onClick={() => setShowPrivacySheet(true)}
                className={`p-2 rounded-full border transition-colors ${
                  isDarkTheme ? 'border-slate-800 bg-slate-900 text-slate-300' : 'border-slate-200 bg-white text-slate-700'
                }`}
                title="Privacy & Security Architecture"
              >
                <Shield className="w-4 h-4 text-emerald-400" />
              </button>
            </div>

            {/* Session Status Card */}
            <div
              className={`p-3.5 rounded-xl border mb-3 transition-colors ${
                sessionActive
                  ? isDarkTheme
                    ? 'border-emerald-800/60 bg-emerald-950/20'
                    : 'border-emerald-300 bg-emerald-50'
                  : isDarkTheme
                  ? 'border-slate-800 bg-slate-900/50'
                  : 'border-slate-200 bg-white'
              }`}
            >
              <div className="flex items-center gap-2.5">
                <span
                  className={`w-2.5 h-2.5 rounded-full ${
                    sessionActive ? 'bg-emerald-500 animate-pulse' : 'bg-slate-500'
                  }`}
                />
                <div>
                  <h3 className="text-xs font-semibold">
                    {sessionActive ? 'Foreground Capture Active' : 'Ready to Translate'}
                  </h3>
                  <p className="text-[10px] text-slate-400">
                    {sessionActive ? 'Tap floating bubble on any screen' : 'Start session to request screen capture'}
                  </p>
                </div>
              </div>
            </div>

            {/* Language Selection Card */}
            <div
              className={`p-3 rounded-xl border mb-3 ${
                isDarkTheme ? 'border-slate-800 bg-slate-900/60' : 'border-slate-200 bg-white shadow-sm'
              }`}
            >
              <span className="text-[10px] font-medium text-slate-400 uppercase tracking-wider block mb-1.5">
                Language Direction
              </span>
              <div className="flex items-center justify-between gap-2">
                <div className="flex-1">
                  <select
                    value={sourceLang}
                    onChange={(e) => setSourceLang(e.target.value)}
                    className={`w-full text-xs font-medium px-2 py-1.5 rounded border focus:outline-none ${
                      isDarkTheme ? 'bg-slate-950 border-slate-800 text-slate-200' : 'bg-slate-100 border-slate-200 text-slate-800'
                    }`}
                  >
                    <option value="auto">Auto-detect</option>
                    <option value="ja">Japanese</option>
                    <option value="ko">Korean</option>
                    <option value="zh">Chinese</option>
                  </select>
                </div>
                <button
                  type="button"
                  onClick={() => {
                    const temp = sourceLang === 'auto' ? 'ja' : sourceLang;
                    setSourceLang(targetLang);
                    setTargetLang(temp);
                  }}
                  className="p-1 rounded text-slate-400 hover:text-blue-400"
                >
                  <ArrowRightLeft className="w-3.5 h-3.5" />
                </button>
                <div className="flex-1">
                  <select
                    value={targetLang}
                    onChange={(e) => setTargetLang(e.target.value)}
                    className={`w-full text-xs font-medium px-2 py-1.5 rounded border focus:outline-none ${
                      isDarkTheme ? 'bg-slate-950 border-slate-800 text-slate-200' : 'bg-slate-100 border-slate-200 text-slate-800'
                    }`}
                  >
                    <option value="en">English</option>
                    <option value="tr">Turkish</option>
                    <option value="es">Spanish</option>
                  </select>
                </div>
              </div>
            </div>

            {/* Target Application View */}
            <div
              className={`flex-1 p-3.5 rounded-xl border relative flex flex-col justify-between overflow-hidden ${
                isSecureSurfaceActive
                  ? 'border-amber-700/60 bg-amber-950/20'
                  : isDarkTheme
                  ? 'border-slate-800 bg-slate-900/30'
                  : 'border-slate-200 bg-slate-100'
              }`}
            >
              <div className="flex items-center justify-between text-[10px] text-slate-400 border-b border-slate-800/60 pb-1.5">
                <span className="font-semibold text-slate-300">{sampleScene.title}</span>
                {isSecureSurfaceActive && (
                  <span className="flex items-center gap-1 font-mono text-[9px] text-amber-400">
                    <Lock className="w-2.5 h-2.5" /> FLAG_SECURE
                  </span>
                )}
              </div>

              {/* Game dialogue box */}
              <div className="my-auto p-3 rounded-lg bg-black/70 border border-slate-700 backdrop-blur-sm">
                <span className="text-[10px] font-bold text-amber-400 block mb-1">
                  {sampleScene.character}
                </span>
                <p className="text-xs text-slate-100 font-sans leading-relaxed">
                  {sampleScene.originalText}
                </p>
              </div>

              {/* Floating Luma Overlay Bubble */}
              {sessionActive && (
                <div
                  style={{ top: '80px', right: '14px' }}
                  className="absolute z-20 flex flex-col items-end"
                >
                  <button
                    onClick={handleTriggerOverlayTranslation}
                    disabled={isTranslating}
                    className="w-12 h-12 rounded-full bg-blue-600 hover:bg-blue-500 shadow-xl border-2 border-white flex items-center justify-center text-white transition-all transform hover:scale-105 active:scale-95 group"
                    title="Tap to Translate Visible Screen"
                  >
                    {isTranslating ? (
                      <Sparkles className="w-5 h-5 animate-spin" />
                    ) : (
                      <span className="font-bold text-sm tracking-tighter">LUMA</span>
                    )}
                  </button>
                  <span className="text-[9px] font-medium bg-black/80 text-white px-1.5 py-0.5 rounded shadow mt-1">
                    Tap to translate
                  </span>
                </div>
              )}

              {/* Security Block Alert (FLAG_SECURE handling) */}
              {securityBlockAlert && (
                <div className="absolute inset-x-2 bottom-2 p-3 rounded-lg bg-amber-950/95 border border-amber-600 shadow-2xl backdrop-blur-md z-30 animate-in fade-in">
                  <div className="flex items-start gap-2">
                    <AlertTriangle className="w-4 h-4 text-amber-400 shrink-0 mt-0.5" />
                    <div>
                      <span className="font-bold text-xs text-amber-300 block">Capture Blocked</span>
                      <p className="text-[11px] text-amber-200 mt-0.5 leading-tight">{securityBlockAlert}</p>
                    </div>
                  </div>
                  <button
                    onClick={() => setSecurityBlockAlert(null)}
                    className="w-full mt-2 py-1 rounded bg-amber-900/60 hover:bg-amber-900 text-amber-200 text-[10px] font-semibold"
                  >
                    Dismiss
                  </button>
                </div>
              )}

              {/* Translation Overlay Result Card on Screen */}
              {translatedResult && (
                <div className="absolute inset-x-2 bottom-2 p-3 rounded-lg bg-slate-950/95 border border-blue-500/60 shadow-2xl backdrop-blur-md z-30 animate-in fade-in slide-in-from-bottom-2">
                  <div className="flex items-center justify-between border-b border-slate-800 pb-1 mb-1.5 text-[10px]">
                    <span className="font-semibold text-blue-400 flex items-center gap-1">
                      <Sparkles className="w-3 h-3" /> Luma Translation
                    </span>
                    <div className="flex items-center gap-1.5">
                      <button
                        onClick={handleCopy}
                        className="text-slate-400 hover:text-white p-1"
                        title="Copy translation"
                      >
                        {copied ? <Check className="w-3 h-3 text-emerald-400" /> : <Copy className="w-3 h-3" />}
                      </button>
                      <button
                        onClick={() => setTranslatedResult(null)}
                        className="text-slate-400 hover:text-white p-1"
                        title="Dismiss"
                      >
                        <X className="w-3 h-3" />
                      </button>
                    </div>
                  </div>
                  <p className="text-xs text-white leading-relaxed font-medium">
                    {translatedResult}
                  </p>
                </div>
              )}
            </div>

            {/* Bottom Primary Action Button */}
            <div className="mt-3">
              <button
                onClick={handleStartSession}
                className={`w-full py-2.5 rounded-xl font-semibold text-xs transition-all shadow-md ${
                  sessionActive
                    ? isDarkTheme
                      ? 'bg-slate-800 hover:bg-slate-700 text-slate-200'
                      : 'bg-slate-300 hover:bg-slate-400 text-slate-800'
                    : 'bg-blue-600 hover:bg-blue-500 text-white'
                }`}
              >
                {sessionActive ? 'Stop Capture Session' : 'Start Capture Session'}
              </button>
            </div>
          </div>

          {/* Android Navigation Bar */}
          <div className="h-6 flex items-center justify-center shrink-0">
            <div className="w-28 h-1 rounded-full bg-slate-600/60" />
          </div>

          {/* Step 1: Overlay Permission Dialog */}
          {showOverlayDialog && (
            <div className="absolute inset-0 bg-black/70 backdrop-blur-sm z-40 flex items-center justify-center p-4">
              <div
                className={`w-full rounded-2xl p-5 border shadow-2xl ${
                  isDarkTheme ? 'bg-slate-900 border-slate-700 text-slate-100' : 'bg-white border-slate-200 text-slate-900'
                }`}
              >
                <div className="w-10 h-10 rounded-full bg-blue-600/20 text-blue-500 flex items-center justify-center mb-3">
                  <Layers className="w-5 h-5" />
                </div>
                <h3 className="text-sm font-bold mb-1">Step 1: Overlay Permission</h3>
                <p className="text-[11px] text-slate-400 mb-4 leading-relaxed">
                  Luma Translate needs permission to draw the floating translation trigger button over other apps.
                </p>
                <div className="flex gap-2">
                  <button
                    onClick={() => setShowOverlayDialog(false)}
                    className="flex-1 py-2 text-xs rounded-lg font-medium text-slate-400 hover:text-white"
                  >
                    Cancel
                  </button>
                  <button
                    onClick={handleGrantOverlayPermission}
                    className="flex-1 py-2 text-xs rounded-lg font-medium bg-blue-600 text-white hover:bg-blue-500"
                  >
                    Allow Overlay
                  </button>
                </div>
              </div>
            </div>
          )}

          {/* Step 2: Official Android MediaProjection Consent Dialog (Phase 4) */}
          {showMediaProjectionDialog && (
            <div className="absolute inset-0 bg-black/80 backdrop-blur-sm z-50 flex items-center justify-center p-4">
              <div
                className={`w-full rounded-2xl p-5 border shadow-2xl ${
                  isDarkTheme ? 'bg-slate-900 border-slate-700 text-slate-100' : 'bg-white border-slate-200 text-slate-900'
                }`}
              >
                <div className="w-10 h-10 rounded-full bg-emerald-600/20 text-emerald-400 flex items-center justify-center mb-3">
                  <Cast className="w-5 h-5" />
                </div>
                <h3 className="text-sm font-bold mb-2">Start recording or casting with Luma Translate?</h3>
                <p className="text-[11px] text-slate-400 mb-4 leading-relaxed">
                  Luma Translate will have access to all of the information that is visible on your screen or played from
                  your device while recording or casting. This includes information such as passwords, payment details,
                  photos, messages, and audio.
                </p>
                <div className="flex gap-2">
                  <button
                    onClick={() => setShowMediaProjectionDialog(false)}
                    className="flex-1 py-2 text-xs rounded-lg font-medium text-slate-400 hover:text-white"
                  >
                    Cancel
                  </button>
                  <button
                    onClick={handleGrantMediaProjection}
                    className="flex-1 py-2 text-xs rounded-lg font-medium bg-emerald-600 text-white hover:bg-emerald-500"
                  >
                    Start Now
                  </button>
                </div>
              </div>
            </div>
          )}

          {/* Privacy Architecture Modal Sheet */}
          {showPrivacySheet && (
            <div className="absolute inset-0 bg-black/75 backdrop-blur-sm z-40 flex flex-col justify-end">
              <div
                className={`p-5 rounded-t-3xl border-t ${
                  isDarkTheme ? 'bg-slate-900 border-slate-700 text-slate-100' : 'bg-white border-slate-200 text-slate-900'
                }`}
              >
                <div className="flex items-center justify-between mb-3">
                  <h3 className="text-sm font-bold flex items-center gap-1.5 text-emerald-400">
                    <Shield className="w-4 h-4" /> Privacy & Capture Rules
                  </h3>
                  <button
                    onClick={() => setShowPrivacySheet(false)}
                    className="p-1 rounded text-slate-400 hover:text-white"
                  >
                    <X className="w-4 h-4" />
                  </button>
                </div>

                <div className="space-y-2 text-[11px] text-slate-300">
                  <div className="p-2 rounded bg-slate-950/50 border border-slate-800">
                    <span className="font-semibold text-blue-400 block mb-0.5">Local Frame Buffer Only</span>
                    <span>Frames captured via MediaProjection are processed directly into on-device ML Kit OCR and recycled. No video or screen frames are stored or streamed.</span>
                  </div>
                  <div className="p-2 rounded bg-slate-950/50 border border-slate-800">
                    <span className="font-semibold text-blue-400 block mb-0.5">FLAG_SECURE Compliance</span>
                    <span>Protected windows (banking apps, DRM video) are never recorded or bypassed.</span>
                  </div>
                </div>

                <button
                  onClick={() => setShowPrivacySheet(false)}
                  className="w-full mt-4 py-2 rounded-xl text-xs font-semibold bg-emerald-600 text-white hover:bg-emerald-500"
                >
                  Understood & Close
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
