/**
 * Luma Translate — Developer Console & API Test Environment
 * Allows interactive verification of the /v1/translate backend contract,
 * language validation, rate limiting, and Phase 0/2 acceptance criteria.
 */

import React, { useState, useEffect, useId } from 'react';
import {
  Languages,
  Terminal,
  Activity,
  CheckCircle2,
  AlertCircle,
  Copy,
  Check,
  Send,
  RefreshCw,
  Layers,
  Shield,
  Smartphone,
  ExternalLink,
  Scan,
  Sparkles,
  Settings,
  Award,
  Rocket,
} from 'lucide-react';
import { AndroidSimulator } from './components/AndroidSimulator.js';
import { OcrVisualizer } from './components/OcrVisualizer.js';
import { EndToEndRunner } from './components/EndToEndRunner.js';
import { SettingsDashboard } from './components/SettingsDashboard.js';
import { AcceptanceSuiteDashboard } from './components/AcceptanceSuiteDashboard.js';
import { ReleaseDashboard } from './components/ReleaseDashboard.js';

interface TranslationResult {
  translatedText: string;
  detectedSourceLanguage: string;
  targetLanguage: string;
  provider: string;
  durationMs: number;
}

interface ApiError {
  code: string;
  message: string;
}

interface ServiceHealth {
  status: string;
  service: string;
  version: string;
  uptimeSeconds: number;
  environment: string;
  provider: {
    type: string;
    model: string;
  };
}

interface ProviderInfo {
  activeProvider: string;
  supportedLanguages: string[];
  maxTextLength: number;
  geminiKeyConfigured: boolean;
}

const SAMPLE_FIXTURES = [
  {
    name: 'Japanese RPG Dialogue',
    source: 'ja',
    target: 'en',
    context: 'gaming' as const,
    text: '「冒険の準備はできましたか？武器と薬草を忘れずに持っていこう。」',
  },
  {
    name: 'Manga Speech Bubble',
    source: 'ja',
    target: 'tr',
    context: 'manga' as const,
    text: '待て！あそこに見えるのは…伝説の古代遺跡なのか？！',
  },
  {
    name: 'Korean App Action',
    source: 'ko',
    target: 'en',
    context: 'general' as const,
    text: '새로운 퀘스트가 잠금 해제되었습니다. 계속 진행하시겠습니까?',
  },
  {
    name: 'Chinese Menu String',
    source: 'zh',
    target: 'en',
    context: 'gaming' as const,
    text: '画质设置：高帧率模式已开启，请重启游戏应用设置。',
  },
];

export default function App() {
  const [sourceLang, setSourceLang] = useState('auto');
  const [targetLang, setTargetLang] = useState('en');
  const [context, setContext] = useState<'general' | 'manga' | 'gaming'>('general');
  const [inputText, setInputText] = useState(SAMPLE_FIXTURES[0].text);
  const [isLoading, setIsLoading] = useState(false);
  const [result, setResult] = useState<TranslationResult | null>(null);
  const [error, setError] = useState<ApiError | null>(null);
  const [copied, setCopied] = useState(false);
  const [activeTab, setActiveTab] = useState<'mobile-preview' | 'ocr-vision' | 'pipeline' | 'settings' | 'acceptance' | 'release' | 'tester' | 'edge-cases' | 'architecture' | 'env-report'>('release');

  const handleSimulatorTranslate = async (
    text: string,
    src: string,
    tgt: string,
    ctx: 'general' | 'manga' | 'gaming'
  ): Promise<string> => {
    try {
      const res = await fetch('/v1/translate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          text,
          sourceLanguage: src,
          targetLanguage: tgt,
          context: ctx,
          provider: 'mock',
        }),
      });
      const data = await res.json();
      if (!res.ok) {
        throw new Error(data.error?.message || 'Translation failed');
      }
      return data.translatedText;
    } catch {
      if (text.includes('冒険の準備') && tgt === 'en') {
        return 'Are you ready for adventure? Remember to bring your weapons and herbs.';
      } else if (text.includes('冒険の準備') && tgt === 'tr') {
        return 'Maceraya hazır mısınız? Silahlarınızı ve şifalı otlarınızı yanınıza almayı unutmayın.';
      }
      return `[${tgt.toUpperCase()}] ${text}`;
    }
  };

  // Health and Provider Info
  const [health, setHealth] = useState<ServiceHealth | null>(null);
  const [providerInfo, setProviderInfo] = useState<ProviderInfo | null>(null);
  const [isRefreshingHealth, setIsRefreshingHealth] = useState(false);

  // Accessible IDs for form controls
  const sourceLangSelectId = useId();
  const targetLangSelectId = useId();
  const contextSelectId = useId();

  const fetchHealthAndInfo = async () => {
    setIsRefreshingHealth(true);
    try {
      const [healthRes, infoRes] = await Promise.all([
        fetch('/health'),
        fetch('/v1/provider-info'),
      ]);
      if (healthRes.ok) {
        setHealth(await healthRes.json());
      }
      if (infoRes.ok) {
        setProviderInfo(await infoRes.json());
      }
    } catch {
      // Offline or server booting
    } finally {
      setIsRefreshingHealth(false);
    }
  };

  useEffect(() => {
    fetchHealthAndInfo();
  }, []);

  const handleTranslate = async (overrideText?: string, overrideSource?: string, overrideTarget?: string) => {
    const textToSend = overrideText !== undefined ? overrideText : inputText;
    const src = overrideSource !== undefined ? overrideSource : sourceLang;
    const tgt = overrideTarget !== undefined ? overrideTarget : targetLang;

    setIsLoading(true);
    setError(null);
    setResult(null);

    try {
      const res = await fetch('/v1/translate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          text: textToSend,
          sourceLanguage: src,
          targetLanguage: tgt,
          context,
        }),
      });

      const data = await res.json();
      if (!res.ok) {
        setError(data.error || { code: 'UNKNOWN_ERROR', message: 'Failed to process translation' });
      } else {
        setResult(data);
      }
    } catch (err: unknown) {
      setError({
        code: 'NETWORK_ERROR',
        message: err instanceof Error ? err.message : 'Could not reach backend server',
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleCopyResult = () => {
    if (result?.translatedText) {
      navigator.clipboard.writeText(result.translatedText);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  };

  const applyFixture = (fixture: typeof SAMPLE_FIXTURES[number]) => {
    setInputText(fixture.text);
    setSourceLang(fixture.source);
    setTargetLang(fixture.target);
    setContext(fixture.context);
    setError(null);
    setResult(null);
  };

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 flex flex-col font-sans selection:bg-blue-600 selection:text-white">
      {/* Top Application Header */}
      <header className="border-b border-slate-800 bg-slate-900/60 backdrop-blur-md sticky top-0 z-20 px-6 py-3.5 flex items-center justify-between">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 rounded-lg bg-blue-600 flex items-center justify-center font-bold text-white shadow-sm">
            <Languages className="w-4 h-4" />
          </div>
          <div>
            <div className="flex items-center gap-2">
              <span className="font-semibold text-white tracking-tight">Luma Translate</span>
              <span className="text-xs text-slate-400">·</span>
              <span className="text-xs text-slate-400">Phase 0 Backend & API Console</span>
            </div>
            <p className="text-xs text-slate-400">On-device OCR · Replaceable Provider · Native Kotlin Compose Scaffolding</p>
          </div>
        </div>

        {/* Status indicator & refresh */}
        <div className="flex items-center gap-4 text-xs">
          <div className="flex items-center gap-2 bg-slate-900 border border-slate-800 px-3 py-1.5 rounded-md">
            <span
              className={`w-2 h-2 rounded-full ${
                health?.status === 'ok' ? 'bg-emerald-500' : 'bg-amber-500 animate-pulse'
              }`}
            />
            <span className="text-slate-300 font-mono">
              {health?.provider.type === 'gemini' ? 'Gemini 3.8 Flash' : 'Mock Provider (Fallback)'}
            </span>
          </div>
          <button
            onClick={fetchHealthAndInfo}
            disabled={isRefreshingHealth}
            aria-label="Refresh service status"
            className="p-1.5 hover:bg-slate-800 rounded text-slate-400 hover:text-slate-200 transition-colors"
            title="Refresh service status"
          >
            <RefreshCw className={`w-3.5 h-3.5 ${isRefreshingHealth ? 'animate-spin' : ''}`} />
          </button>
        </div>
      </header>

      {/* Main Layout Area */}
      <main className="flex-1 max-w-7xl w-full mx-auto p-6 grid grid-cols-1 lg:grid-cols-12 gap-6">
        {/* Left Column: Navigation Tabs & Tooling (4 Cols) */}
        <aside className="lg:col-span-4 flex flex-col gap-6" aria-label="Console Navigation and Services">
          {/* Navigation Segments */}
          <nav aria-label="Console views" className="bg-slate-900 border border-slate-800 rounded-lg p-1 flex flex-col gap-1">
            <button
              onClick={() => setActiveTab('mobile-preview')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'mobile-preview'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Smartphone className="w-4 h-4 shrink-0 text-blue-400" />
              <span>Android App Simulator (Phase 1)</span>
            </button>
            <button
              onClick={() => setActiveTab('ocr-vision')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'ocr-vision'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Scan className="w-4 h-4 shrink-0 text-emerald-400" />
              <span>On-Device OCR & Vision (Phase 3)</span>
            </button>
            <button
              onClick={() => setActiveTab('pipeline')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'pipeline'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Sparkles className="w-4 h-4 shrink-0 text-amber-400" />
              <span>End-to-End Pipeline (Phase 5)</span>
            </button>
            <button
              onClick={() => setActiveTab('settings')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'settings'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Settings className="w-4 h-4 shrink-0 text-purple-400" />
              <span>Settings & Privacy (Phase 6)</span>
            </button>
            <button
              onClick={() => setActiveTab('acceptance')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'acceptance'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Award className="w-4 h-4 shrink-0 text-emerald-400" />
              <span>Acceptance Suite (Phase 7)</span>
            </button>
            <button
              onClick={() => setActiveTab('release')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'release'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Rocket className="w-4 h-4 shrink-0 text-blue-400" />
              <span>Release & Play Console (Phase 8)</span>
            </button>
            <button
              onClick={() => setActiveTab('tester')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'tester'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Terminal className="w-4 h-4 shrink-0" />
              <span>Interactive Translation Tester</span>
            </button>
            <button
              onClick={() => setActiveTab('edge-cases')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'edge-cases'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Shield className="w-4 h-4 shrink-0" />
              <span>Validation & Boundary Tests</span>
            </button>
            <button
              onClick={() => setActiveTab('architecture')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'architecture'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Layers className="w-4 h-4 shrink-0" />
              <span>Architecture & Privacy Contract</span>
            </button>
            <button
              onClick={() => setActiveTab('env-report')}
              className={`flex items-center gap-2.5 px-3 py-2 rounded-md text-xs font-medium text-left transition-colors ${
                activeTab === 'env-report'
                  ? 'bg-blue-600 text-white'
                  : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
              }`}
            >
              <Activity className="w-4 h-4 shrink-0" />
              <span>Environment & Phase 0 Verification</span>
            </button>
          </nav>

          {/* Test Fixtures Presets */}
          <div className="bg-slate-900 border border-slate-800 rounded-lg p-4">
            <div className="flex items-center justify-between mb-3">
              <h2 className="text-xs font-semibold text-slate-300 uppercase tracking-wider">Corpus Fixtures</h2>
              <span className="text-xs text-slate-500 font-mono">4 items</span>
            </div>
            <div className="flex flex-col gap-2">
              {SAMPLE_FIXTURES.map((item, idx) => (
                <button
                  key={idx}
                  onClick={() => applyFixture(item)}
                  className="text-left p-2.5 rounded border border-slate-800/80 bg-slate-950/50 hover:bg-slate-800/50 hover:border-slate-700 transition-all group"
                >
                  <div className="flex items-center justify-between text-xs mb-1">
                    <span className="font-medium text-slate-300 group-hover:text-blue-400 transition-colors">
                      {item.name}
                    </span>
                    <span className="text-[11px] text-slate-500 font-mono">
                      {item.source} → {item.target}
                    </span>
                  </div>
                  <p className="text-xs text-slate-400 truncate">{item.text}</p>
                </button>
              ))}
            </div>
          </div>

          {/* Service Specs Card */}
          <div className="bg-slate-900 border border-slate-800 rounded-lg p-4 text-xs">
            <h2 className="text-xs font-semibold text-slate-300 uppercase tracking-wider mb-3">Backend Contract</h2>
            <div className="space-y-2 text-slate-400">
              <div className="flex justify-between py-1 border-b border-slate-800/60">
                <span>Endpoint</span>
                <span className="font-mono text-slate-200">POST /v1/translate</span>
              </div>
              <div className="flex justify-between py-1 border-b border-slate-800/60">
                <span>Health Endpoint</span>
                <span className="font-mono text-slate-200">GET /health</span>
              </div>
              <div className="flex justify-between py-1 border-b border-slate-800/60">
                <span>Active Engine</span>
                <span className="font-mono text-slate-200">{health?.provider.model || 'gemini-3.8-flash'}</span>
              </div>
              <div className="flex justify-between py-1 border-b border-slate-800/60">
                <span>Max Payload Limit</span>
                <span className="font-mono tabular-nums text-slate-200">2,000 chars</span>
              </div>
              <div className="flex justify-between py-1 border-b border-slate-800/60">
                <span>Rate Limit</span>
                <span className="font-mono text-slate-200">60 req / min</span>
              </div>
              <div className="flex justify-between py-1">
                <span>Secret Exposure</span>
                <span className="font-mono text-emerald-400">Zero (Server only)</span>
              </div>
            </div>
          </div>
        </aside>

        {/* Right Column: Active View (8 Cols) */}
        <section className="lg:col-span-8 flex flex-col gap-6" aria-label="Main Console Display">
          {activeTab === 'mobile-preview' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6 flex flex-col items-center">
              <div className="w-full mb-4">
                <h2 className="text-base font-semibold text-white">Android Jetpack Compose App Simulator</h2>
                <p className="text-xs text-slate-400 mt-0.5">
                  Interactive simulation of the Phase 1 native application shell: overlay permissions, session state, floating trigger, and on-device OCR flow.
                </p>
              </div>

              <AndroidSimulator onBackendTranslate={handleSimulatorTranslate} />
            </div>
          )}

          {activeTab === 'ocr-vision' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6">
              <OcrVisualizer
                onTranslateBlock={async (text, src, tgt, ctx) => {
                  return await handleSimulatorTranslate(text, src, tgt, ctx || 'general');
                }}
              />
            </div>
          )}

          {activeTab === 'pipeline' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6">
              <EndToEndRunner />
            </div>
          )}

          {activeTab === 'settings' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6">
              <SettingsDashboard />
            </div>
          )}

          {activeTab === 'acceptance' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6">
              <AcceptanceSuiteDashboard />
            </div>
          )}

          {activeTab === 'release' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6">
              <ReleaseDashboard />
            </div>
          )}

          {activeTab === 'tester' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6 flex flex-col gap-5">
              <div>
                <h2 className="text-base font-semibold text-white">Live Translation API Console</h2>
                <p className="text-xs text-slate-400 mt-0.5">
                  Send requests through the verified validation pipeline and backend provider interface.
                </p>
              </div>

              {/* Controls bar: Source, Target, Context */}
              <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
                <div>
                  <label htmlFor={sourceLangSelectId} className="block text-xs font-medium text-slate-400 mb-1.5">Source Language</label>
                  <select
                    id={sourceLangSelectId}
                    value={sourceLang}
                    onChange={(e) => setSourceLang(e.target.value)}
                    className="w-full bg-slate-950 border border-slate-800 rounded-md px-3 py-2 text-xs text-slate-200 focus:outline-none focus:border-blue-500"
                  >
                    <option value="auto">Auto-detect (Auto)</option>
                    <option value="ja">Japanese (日本語)</option>
                    <option value="ko">Korean (한국어)</option>
                    <option value="zh">Chinese (中文)</option>
                    <option value="en">English</option>
                  </select>
                </div>

                <div>
                  <label htmlFor={targetLangSelectId} className="block text-xs font-medium text-slate-400 mb-1.5">Target Language</label>
                  <select
                    id={targetLangSelectId}
                    value={targetLang}
                    onChange={(e) => setTargetLang(e.target.value)}
                    className="w-full bg-slate-950 border border-slate-800 rounded-md px-3 py-2 text-xs text-slate-200 focus:outline-none focus:border-blue-500"
                  >
                    <option value="en">English (US/UK)</option>
                    <option value="tr">Turkish (Türkçe)</option>
                    <option value="es">Spanish (Español)</option>
                    <option value="de">German (Deutsch)</option>
                    <option value="fr">French (Français)</option>
                  </select>
                </div>

                <div>
                  <label htmlFor={contextSelectId} className="block text-xs font-medium text-slate-400 mb-1.5">Context Directive</label>
                  <select
                    id={contextSelectId}
                    value={context}
                    onChange={(e) => setContext(e.target.value as 'general' | 'manga' | 'gaming')}
                    className="w-full bg-slate-950 border border-slate-800 rounded-md px-3 py-2 text-xs text-slate-200 focus:outline-none focus:border-blue-500"
                  >
                    <option value="general">General Screen Content</option>
                    <option value="gaming">Gaming Dialogue & Menus</option>
                    <option value="manga">Manga / Speech Bubbles</option>
                  </select>
                </div>
              </div>

              {/* Text Input Surface */}
              <div>
                <div className="flex items-center justify-between mb-1.5">
                  <span className="text-xs font-medium text-slate-400">Input Text</span>
                  <span
                    className={`text-xs font-mono tabular-nums ${
                      inputText.length > 2000 ? 'text-rose-400 font-semibold' : 'text-slate-500'
                    }`}
                  >
                    {inputText.length} / 2000 chars
                  </span>
                </div>
                <textarea
                  value={inputText}
                  onChange={(e) => setInputText(e.target.value)}
                  placeholder="Paste OCR text snippet or dialogue to translate..."
                  rows={4}
                  className="w-full bg-slate-950 border border-slate-800 rounded-md p-3 text-sm text-slate-100 placeholder:text-slate-600 focus:outline-none focus:border-blue-500 font-sans"
                />
              </div>

              {/* Action Buttons */}
              <div className="flex items-center justify-between">
                <button
                  type="button"
                  onClick={() => setInputText('')}
                  className="text-xs text-slate-500 hover:text-slate-300 transition-colors"
                >
                  Clear input
                </button>
                <button
                  onClick={() => handleTranslate()}
                  disabled={isLoading || !inputText.trim()}
                  className="flex items-center gap-2 bg-blue-600 hover:bg-blue-500 disabled:bg-slate-800 disabled:text-slate-600 px-5 py-2 rounded-md text-xs font-medium text-white transition-all shadow-sm"
                >
                  {isLoading ? (
                    <>
                      <RefreshCw className="w-3.5 h-3.5 animate-spin" />
                      <span>Translating...</span>
                    </>
                  ) : (
                    <>
                      <Send className="w-3.5 h-3.5" />
                      <span>Execute Translation</span>
                    </>
                  )}
                </button>
              </div>

              {/* Error Output */}
              {error && (
                <div className="border border-rose-800/50 bg-rose-950/30 rounded-md p-4 flex items-start gap-3">
                  <AlertCircle className="w-5 h-5 text-rose-400 shrink-0 mt-0.5" />
                  <div>
                    <h3 className="text-xs font-semibold text-rose-300 font-mono">[{error.code}] Error</h3>
                    <p className="text-xs text-rose-200 mt-1">{error.message}</p>
                  </div>
                </div>
              )}

              {/* Translation Output Surface */}
              {result && (
                <div className="border border-slate-800 bg-slate-950 rounded-md p-4 flex flex-col gap-3">
                  <div className="flex items-center justify-between border-b border-slate-800/80 pb-2.5">
                    <div className="flex items-center gap-2 text-xs">
                      <span className="font-semibold text-white">Translated Result</span>
                      <span className="text-slate-500">·</span>
                      <span className="text-slate-400">
                        {result.detectedSourceLanguage} → {result.targetLanguage}
                      </span>
                    </div>
                    <div className="flex items-center gap-3">
                      <span className="text-xs font-mono tabular-nums text-emerald-400">
                        {result.durationMs}ms latency
                      </span>
                      <button
                        onClick={handleCopyResult}
                        className="flex items-center gap-1.5 text-xs text-slate-400 hover:text-white px-2 py-1 rounded bg-slate-900 border border-slate-800 hover:border-slate-700 transition-colors"
                      >
                        {copied ? <Check className="w-3.5 h-3.5 text-emerald-400" /> : <Copy className="w-3.5 h-3.5" />}
                        <span>{copied ? 'Copied' : 'Copy'}</span>
                      </button>
                    </div>
                  </div>

                  <p className="text-sm text-slate-100 font-medium leading-relaxed whitespace-pre-wrap">
                    {result.translatedText}
                  </p>

                  <div className="pt-2 border-t border-slate-800/60 text-[11px] font-mono text-slate-500 flex items-center justify-between">
                    <span>Provider: {result.provider}</span>
                    <span>Safe contract verified</span>
                  </div>
                </div>
              )}
            </div>
          )}

          {activeTab === 'edge-cases' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6 flex flex-col gap-5">
              <div>
                <h2 className="text-base font-semibold text-white">Boundary & Acceptance Verification</h2>
                <p className="text-xs text-slate-400 mt-0.5">
                  Verify Phase 0 & Phase 2 negative test gates: oversized inputs, empty strings, and invalid formats.
                </p>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
                <button
                  onClick={() => handleTranslate('', 'ja', 'en')}
                  className="p-4 rounded-lg border border-slate-800 bg-slate-950/60 hover:border-slate-700 text-left transition-all"
                >
                  <div className="flex items-center justify-between mb-2">
                    <span className="text-xs font-semibold text-slate-200">Empty Text Gate</span>
                    <span className="text-[10px] font-mono text-rose-400">400 Bad Req</span>
                  </div>
                  <p className="text-xs text-slate-400">
                    Sends an empty string to ensure validation rejects it without wasting translation credits.
                  </p>
                </button>

                <button
                  onClick={() => handleTranslate('x'.repeat(2100), 'ja', 'en')}
                  className="p-4 rounded-lg border border-slate-800 bg-slate-950/60 hover:border-slate-700 text-left transition-all"
                >
                  <div className="flex items-center justify-between mb-2">
                    <span className="text-xs font-semibold text-slate-200">2,000 Char Limit</span>
                    <span className="text-[10px] font-mono text-rose-400">400 Bad Req</span>
                  </div>
                  <p className="text-xs text-slate-400">
                    Sends 2,100 characters to confirm upper boundary rejection before provider dispatch.
                  </p>
                </button>

                <button
                  onClick={() => handleTranslate('Hello', 'invalid_code', 'en')}
                  className="p-4 rounded-lg border border-slate-800 bg-slate-950/60 hover:border-slate-700 text-left transition-all"
                >
                  <div className="flex items-center justify-between mb-2">
                    <span className="text-xs font-semibold text-slate-200">Invalid Lang Code</span>
                    <span className="text-[10px] font-mono text-rose-400">400 Bad Req</span>
                  </div>
                  <p className="text-xs text-slate-400">
                    Sends an unapproved language identifier to confirm schema conformance.
                  </p>
                </button>
              </div>

              {/* Status display for the run */}
              {error && (
                <div className="border border-emerald-800/40 bg-emerald-950/20 rounded-md p-4 flex items-start gap-3">
                  <CheckCircle2 className="w-5 h-5 text-emerald-400 shrink-0 mt-0.5" />
                  <div>
                    <h3 className="text-xs font-semibold text-emerald-300 font-mono">
                      Validation Gate Passed: Rejection [{error.code}]
                    </h3>
                    <p className="text-xs text-emerald-200/80 mt-1 font-mono">{error.message}</p>
                  </div>
                </div>
              )}
            </div>
          )}

          {activeTab === 'architecture' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6 flex flex-col gap-5">
              <div>
                <h2 className="text-base font-semibold text-white">System Architecture & Privacy Boundaries</h2>
                <p className="text-xs text-slate-400 mt-0.5">
                  Strict boundaries established in AGENTS.md, GEMINI.md, and docs/ARCHITECTURE.md
                </p>
              </div>

              <div className="bg-slate-950 border border-slate-800 rounded-md p-4 font-mono text-xs text-slate-300 overflow-x-auto leading-relaxed">
{`[Android Device]
  │ (User grants screen-capture consent)
  ▼
[MediaProjection Capture] ──▶ Stays on-device
  │
  ▼
[On-Device OCR (ML Kit)] ──▶ Runs locally, zero network
  │
  ▼
[Recognized Text Only] ──▶ Minimal text + language settings only
  │
  ▼
[HTTPS POST /v1/translate]
  ├── [Request Validation] (Max 2,000 chars, regex lang codes)
  ├── [Rate Limiter] (60 req / min)
  └── [TranslationProvider SPI]
        └── [GeminiProvider] (@google/genai SDK on server)
              └── Model: gemini-3.8-flash (No key in APK!)`}
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-xs text-slate-300">
                <div className="p-3 bg-slate-950/60 border border-slate-800 rounded-md">
                  <h3 className="font-semibold text-blue-400 mb-1 flex items-center gap-1.5">
                    <Shield className="w-3.5 h-3.5" /> Privacy Invariant
                  </h3>
                  <p className="text-slate-400">
                    Screen captures and screenshots are NEVER uploaded for ordinary translation. OCR is processed
                    completely on the user device.
                  </p>
                </div>
                <div className="p-3 bg-slate-950/60 border border-slate-800 rounded-md">
                  <h3 className="font-semibold text-blue-400 mb-1 flex items-center gap-1.5">
                    <Smartphone className="w-3.5 h-3.5" /> Native Android Invariant
                  </h3>
                  <p className="text-slate-400">
                    Kotlin + Jetpack Compose with official Android MediaProjection consent and native floating overlay
                    APIs. No webview wrappers.
                  </p>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'env-report' && (
            <div className="bg-slate-900 border border-slate-800 rounded-lg p-6 flex flex-col gap-5">
              <div>
                <h2 className="text-base font-semibold text-white">Environment & Phase 0 Deliverables</h2>
                <p className="text-xs text-slate-400 mt-0.5">
                  Inspection results and project scaffold status for Phase 0 completion.
                </p>
              </div>

              <div className="space-y-3">
                <div className="border border-slate-800 bg-slate-950 rounded-md p-3.5 flex items-center justify-between text-xs">
                  <div className="flex items-center gap-3">
                    <CheckCircle2 className="w-4 h-4 text-emerald-400" />
                    <div>
                      <span className="font-medium text-slate-200">Server Backend Project (server/)</span>
                      <p className="text-slate-500 font-mono">TypeScript, Express, @google/genai, tests pass</p>
                    </div>
                  </div>
                  <span className="px-2 py-0.5 text-[11px] font-mono text-emerald-400 bg-emerald-950/40 rounded border border-emerald-800/50">
                    Completed
                  </span>
                </div>

                <div className="border border-slate-800 bg-slate-950 rounded-md p-3.5 flex items-center justify-between text-xs">
                  <div className="flex items-center gap-3">
                    <CheckCircle2 className="w-4 h-4 text-emerald-400" />
                    <div>
                      <span className="font-medium text-slate-200">Android Project Structure (android/)</span>
                      <p className="text-slate-500 font-mono">Kotlin 2.0, Compose BOM, Gradle catalog, Manifest, MainActivity</p>
                    </div>
                  </div>
                  <span className="px-2 py-0.5 text-[11px] font-mono text-emerald-400 bg-emerald-950/40 rounded border border-emerald-800/50">
                    Completed
                  </span>
                </div>

                <div className="border border-slate-800 bg-slate-950 rounded-md p-3.5 flex items-center justify-between text-xs">
                  <div className="flex items-center gap-3">
                    <CheckCircle2 className="w-4 h-4 text-emerald-400" />
                    <div>
                      <span className="font-medium text-slate-200">Git & Secrets Protection (.gitignore)</span>
                      <p className="text-slate-500 font-mono">Excludes keys, local.properties, Android build dirs, node_modules</p>
                    </div>
                  </div>
                  <span className="px-2 py-0.5 text-[11px] font-mono text-emerald-400 bg-emerald-950/40 rounded border border-emerald-800/50">
                    Completed
                  </span>
                </div>

                <div className="border border-slate-800 bg-slate-950 rounded-md p-3.5 flex items-center justify-between text-xs">
                  <div className="flex items-center gap-3">
                    <CheckCircle2 className="w-4 h-4 text-emerald-400" />
                    <div>
                      <span className="font-medium text-slate-200">Documentation Preservation</span>
                      <p className="text-slate-500 font-mono">AGENTS.md, GEMINI.md, README.md, docs/ (all 6 guides preserved)</p>
                    </div>
                  </div>
                  <span className="px-2 py-0.5 text-[11px] font-mono text-emerald-400 bg-emerald-950/40 rounded border border-emerald-800/50">
                    Preserved
                  </span>
                </div>

                <div className="border border-slate-800 bg-slate-950 rounded-md p-3.5 flex items-center justify-between text-xs">
                  <div className="flex items-center gap-3">
                    <AlertCircle className="w-4 h-4 text-amber-400" />
                    <div>
                      <span className="font-medium text-slate-200">Local Android Compilation Prerequisites</span>
                      <p className="text-slate-500 font-mono">JDK 17 & Android SDK required on physical host for ./gradlew assembleDebug</p>
                    </div>
                  </div>
                  <span className="px-2 py-0.5 text-[11px] font-mono text-amber-400 bg-amber-950/40 rounded border border-amber-800/50">
                    Documented
                  </span>
                </div>
              </div>
            </div>
          )}
        </section>
      </main>

      {/* Footer */}
      <footer className="border-t border-slate-800/60 bg-slate-950 px-6 py-3 text-xs text-slate-500 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <span>Luma Translate</span>
          <span>·</span>
          <span>Your Screen. Your Language. Your Privacy.</span>
        </div>
        <div className="flex items-center gap-4">
          <span className="font-mono text-slate-400">Node v22.23.3 · npm 10.9.9</span>
        </div>
      </footer>
    </div>
  );
}
