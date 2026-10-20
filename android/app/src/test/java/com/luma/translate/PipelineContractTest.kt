package com.luma.translate

import com.luma.translate.ocr.NormalizedRect
import com.luma.translate.pipeline.PipelineMetrics
import com.luma.translate.pipeline.PipelineResult
import com.luma.translate.pipeline.TranslatedRegion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PipelineContractTest {

    @Test
    fun pipelineResult_successHoldsMetricsAndRegions() {
        val region = TranslatedRegion(
            id = "region-1",
            originalText = "冒険の準備はできましたか？",
            translatedText = "Are you ready for adventure?",
            boundingBox = NormalizedRect(0.1f, 0.2f, 0.9f, 0.4f),
            confidence = 0.96f
        )
        val metrics = PipelineMetrics(
            captureTimeMs = 12L,
            ocrTimeMs = 45L,
            networkTimeMs = 120L,
            totalTimeMs = 177L
        )
        val result = PipelineResult.Success(
            regions = listOf(region),
            sourceLanguage = "ja",
            targetLanguage = "en",
            metrics = metrics
        )

        assertEquals(1, result.regions.size)
        assertEquals("Are you ready for adventure?", result.regions[0].translatedText)
        assertEquals(177L, result.metrics.totalTimeMs)
        assertTrue(result.metrics.networkTimeMs > result.metrics.captureTimeMs)
    }

    @Test
    fun pipelineResult_emptyOcrIsRecognized() {
        val emptyResult: PipelineResult = PipelineResult.EmptyOcr
        assertTrue(emptyResult is PipelineResult.EmptyOcr)
    }
}
