package com.luma.translate

import com.luma.translate.ocr.NormalizedRect
import com.luma.translate.ocr.OcrResult
import com.luma.translate.ocr.OcrTextBlock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OcrContractTest {

    @Test
    fun normalizedRect_calculatesWidthAndHeightCorrectly() {
        val rect = NormalizedRect(
            left = 0.1f,
            top = 0.2f,
            right = 0.6f,
            bottom = 0.8f
        )
        assertEquals(0.5f, rect.width, 0.001f)
        assertEquals(0.6f, rect.height, 0.001f)
    }

    @Test
    fun ocrResult_aggregatesFullText() {
        val block1 = OcrTextBlock(
            id = "1",
            text = "冒険の準備はできましたか？",
            boundingBox = NormalizedRect(0.1f, 0.1f, 0.9f, 0.3f),
            language = "ja"
        )
        val block2 = OcrTextBlock(
            id = "2",
            text = "はい / いいえ",
            boundingBox = NormalizedRect(0.3f, 0.5f, 0.7f, 0.7f),
            language = "ja"
        )

        val result = OcrResult(
            blocks = listOf(block1, block2),
            imageWidth = 1080,
            imageHeight = 1920,
            processingTimeMs = 45L,
            detectedLanguage = "ja"
        )

        assertFalse(result.isEmpty)
        assertEquals(2, result.blocks.size)
        assertTrue(result.fullText.contains("冒険の準備はできましたか？"))
        assertTrue(result.fullText.contains("はい / いいえ"))
    }
}
