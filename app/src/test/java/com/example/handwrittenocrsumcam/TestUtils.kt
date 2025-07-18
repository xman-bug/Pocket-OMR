package com.example.handwrittenocrsumcam

import org.junit.Assert.*
import java.util.regex.Pattern

/**
 * Utility class for testing text processing and number extraction functionality
 */
object TestUtils {

    /**
     * Extracts numbers from text using the same regex pattern as the main app
     */
    fun extractNumbersFromText(text: String): List<Int> {
        val regex = Regex("\\d+")
        return regex.findAll(text)
            .map { it.value.toInt() }
            .toList()
    }

    /**
     * Calculates the sum of numbers extracted from text
     */
    fun calculateSumFromText(text: String): Int {
        return extractNumbersFromText(text).sum()
    }

    /**
     * Validates that text contains expected numbers
     */
    fun validateNumberExtraction(text: String, expectedNumbers: List<Int>): Boolean {
        val extractedNumbers = extractNumbersFromText(text)
        return extractedNumbers == expectedNumbers
    }

    /**
     * Validates that text produces expected sum
     */
    fun validateSumCalculation(text: String, expectedSum: Int): Boolean {
        val calculatedSum = calculateSumFromText(text)
        return calculatedSum == expectedSum
    }

    /**
     * Generates test text with specified numbers
     */
    fun generateTestText(numbers: List<Int>, prefix: String = "Test numbers: "): String {
        return prefix + numbers.joinToString(" ")
    }

    /**
     * Tests regex pattern matching
     */
    fun testRegexPattern(text: String): Boolean {
        val regex = Regex("\\d+")
        return regex.containsMatchIn(text)
    }

    /**
     * Performance test for text processing
     */
    fun performanceTest(text: String, maxTimeMs: Long = 1000): Boolean {
        val startTime = System.currentTimeMillis()
        val result = extractNumbersFromText(text)
        val endTime = System.currentTimeMillis()
        
        return (endTime - startTime) < maxTimeMs
    }

    /**
     * Stress test with large amounts of text
     */
    fun stressTest(numberCount: Int): Boolean {
        val largeText = "This is a stress test with many numbers: " +
            (1..numberCount).joinToString(" ") { it.toString() }
        
        val startTime = System.currentTimeMillis()
        val result = extractNumbersFromText(largeText)
        val endTime = System.currentTimeMillis()
        
        return result.size == numberCount && (endTime - startTime) < 5000
    }

    /**
     * Edge case test data
     */
    object TestData {
        val emptyText = ""
        val noNumbersText = "Hello World"
        val singleNumberText = "123"
        val multipleNumbersText = "1 2 3 4 5"
        val largeNumbersText = "999999999 1000000000"
        val decimalNumbersText = "12.34 56.78"
        val specialCharactersText = "Test@#$%^&*()123!@#$%^&*()456"
        val whitespaceText = "  123   456   789  "
        val newlineText = "Line 1: 123\nLine 2: 456\nLine 3: 789"
        val zeroNumbersText = "0 000 00123"
    }

    /**
     * Expected results for test data
     */
    object ExpectedResults {
        val emptyTextNumbers = emptyList<Int>()
        val noNumbersResult = emptyList<Int>()
        val singleNumberResult = listOf(123)
        val multipleNumbersResult = listOf(1, 2, 3, 4, 5)
        val largeNumbersResult = listOf(999999999, 1000000000)
        val decimalNumbersResult = listOf(12, 34, 56, 78)
        val specialCharactersResult = listOf(123, 456)
        val whitespaceResult = listOf(123, 456, 789)
        val newlineResult = listOf(1, 123, 2, 456, 3, 789)
        val zeroNumbersResult = listOf(0, 0, 123)
    }

    /**
     * Runs a comprehensive test suite
     */
    fun runComprehensiveTestSuite(): TestResult {
        val results = mutableListOf<Boolean>()
        
        // Test basic functionality
        results.add(validateNumberExtraction(TestData.emptyText, ExpectedResults.emptyTextNumbers))
        results.add(validateNumberExtraction(TestData.noNumbersText, ExpectedResults.noNumbersResult))
        results.add(validateNumberExtraction(TestData.singleNumberText, ExpectedResults.singleNumberResult))
        results.add(validateNumberExtraction(TestData.multipleNumbersText, ExpectedResults.multipleNumbersResult))
        
        // Test edge cases
        results.add(validateNumberExtraction(TestData.largeNumbersText, ExpectedResults.largeNumbersResult))
        results.add(validateNumberExtraction(TestData.decimalNumbersText, ExpectedResults.decimalNumbersResult))
        results.add(validateNumberExtraction(TestData.specialCharactersText, ExpectedResults.specialCharactersResult))
        results.add(validateNumberExtraction(TestData.whitespaceText, ExpectedResults.whitespaceResult))
        results.add(validateNumberExtraction(TestData.newlineText, ExpectedResults.newlineResult))
        results.add(validateNumberExtraction(TestData.zeroNumbersText, ExpectedResults.zeroNumbersResult))
        
        // Test sum calculations
        results.add(validateSumCalculation(TestData.emptyText, 0))
        results.add(validateSumCalculation(TestData.noNumbersText, 0))
        results.add(validateSumCalculation(TestData.singleNumberText, 123))
        results.add(validateSumCalculation(TestData.multipleNumbersText, 15))
        
        // Test performance
        results.add(performanceTest(TestData.multipleNumbersText))
        results.add(stressTest(1000))
        
        val passedTests = results.count { it }
        val totalTests = results.size
        
        return TestResult(
            passedTests = passedTests,
            totalTests = totalTests,
            success = passedTests == totalTests
        )
    }

    /**
     * Test result data class
     */
    data class TestResult(
        val passedTests: Int,
        val totalTests: Int,
        val success: Boolean
    ) {
        val failureCount: Int get() = totalTests - passedTests
        val successRate: Double get() = passedTests.toDouble() / totalTests
    }
}