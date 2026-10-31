package com.luma.translate

import com.luma.translate.translation.MlKitLanguages
import com.luma.translate.translation.SupportedLanguages
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** Every language Luma offers must have an on-device ML Kit translation code. */
class TranslationContractTest {

    @Test fun everyOfferedLanguage_hasAnMlKitCode() {
        (SupportedLanguages.SOURCE_LANGUAGES + SupportedLanguages.TARGET_LANGUAGES).forEach {
            assertNotNull("No ML Kit code for ${it.code}", MlKitLanguages.toMlKit(it.code))
        }
    }

    @Test fun mlKitCodes_roundTrip() {
        listOf("ja", "ko", "zh", "en", "es", "tr").forEach { code ->
            assertEquals(code, MlKitLanguages.fromMlKit(MlKitLanguages.toMlKit(code)!!))
        }
        assertNull(MlKitLanguages.toMlKit("xx"))
        assertNull(MlKitLanguages.fromMlKit("xx"))
    }

    @Test fun supportedPairs() {
        assertTrue(SupportedLanguages.isSupportedPair("ja", "tr"))
        assertTrue(SupportedLanguages.isSupportedPair("zh", "en"))
        assertFalse(SupportedLanguages.isSupportedPair("fr", "en"))
        assertFalse(SupportedLanguages.isSupportedPair("ja", "ko"))
    }
}
