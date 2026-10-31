package com.luma.translate

import com.luma.translate.pipeline.FailureKind
import com.luma.translate.pipeline.ModelMissingException
import com.luma.translate.pipeline.TranslationFailureClassifier
import com.luma.translate.pipeline.UnsupportedLanguagePairException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class TranslationFailureTest {

    private fun kind(t: Throwable) = TranslationFailureClassifier.classify(t)

    @Test fun missingModel_pointsUserToSettings() {
        assertEquals(FailureKind.MODEL_MISSING, kind(ModelMissingException()))
        assertTrue(FailureKind.MODEL_MISSING.userMessage.contains("Settings"))
    }

    @Test fun unsupportedPair_classified() {
        assertEquals(FailureKind.UNSUPPORTED_PAIR, kind(UnsupportedLanguagePairException()))
    }

    @Test fun cancellation_classified() {
        assertEquals(FailureKind.CANCELLED, kind(kotlinx.coroutines.CancellationException("x")))
    }

    @Test fun localFailures_classified() {
        assertEquals(FailureKind.TRANSLATION_FAILED, kind(IOException("x")))
        assertEquals(FailureKind.TRANSLATION_FAILED, kind(IllegalStateException("x")))
        assertEquals(FailureKind.UNKNOWN, kind(Error("x")))
    }

    @Test fun userMessagesNeverContainExceptionText() {
        // Exception messages could contain recognized screen text; they must never reach the UI.
        val secret = "secret screen text 192.168.1.3"
        listOf(IOException(secret), IllegalStateException(secret), ModelMissingException()).forEach {
            assertFalse(kind(it).userMessage.contains("secret"))
            assertFalse(kind(it).userMessage.contains("192.168"))
        }
    }
}
