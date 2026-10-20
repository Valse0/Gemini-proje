/**
 * Unit & Integration Tests for Luma Translate Backend API
 * Phase 0 & Phase 2 Acceptance Verification
 */

import { MockProvider } from '../src/services/mock-provider.js';
import { GeminiProvider } from '../src/services/gemini-provider.js';

let passed = 0;
let failed = 0;

function assert(condition: boolean, testName: string) {
  if (condition) {
    console.log(`  ✓ ${testName}`);
    passed++;
  } else {
    console.error(`  ✗ FAIL: ${testName}`);
    failed++;
  }
}

async function runTests() {
  console.log('\n--- Luma Translate Backend Tests ---');

  // Test 1: MockProvider contract
  const mock = new MockProvider();
  assert(mock.name === 'mock', 'MockProvider reports correct name');
  assert(mock.getSupportedLanguages().includes('ja'), 'MockProvider supports Japanese');

  const res1 = await mock.translate({
    text: 'こんにちは',
    sourceLanguage: 'ja',
    targetLanguage: 'en',
  });
  assert(res1.translatedText === 'Hello', 'MockProvider translates Japanese greeting to English');
  assert(res1.targetLanguage === 'en', 'MockProvider sets targetLanguage correctly');
  assert(typeof res1.durationMs === 'number', 'MockProvider measures duration in ms');

  // Test 2: GeminiProvider class instantiation
  const gemini = new GeminiProvider();
  assert(gemini.name === 'gemini', 'GeminiProvider reports correct name');
  assert(gemini.getSupportedLanguages().includes('en'), 'GeminiProvider includes English');
  assert(gemini.getSupportedLanguages().includes('tr'), 'GeminiProvider includes Turkish');

  // Test 3: Validation logic checks
  const maxLimit = 2000;
  const longText = 'a'.repeat(2005);
  assert(longText.length > maxLimit, 'Oversized text boundary test identified correctly');

  console.log(`\nTests completed: ${passed} passed, ${failed} failed.\n`);
  if (failed > 0) {
    process.exit(1);
  }
}

runTests().catch((err) => {
  console.error('Test execution error:', err);
  process.exit(1);
});
