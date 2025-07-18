package com.example.handwrittenocrsumcam

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestUtilsTest {

    @Before
    fun setUp() {
        // Setup if needed
    }

    @Test
    fun `test extractNumbersFromText basic functionality`() {
        val text = "Hello 123 World 456"
        val result = TestUtils.extractNumbersFromText(text)
        assertEquals(listOf(123, 456), result)
    }

    @Test
    fun `test extractNumbersFromText with empty string`() {
        val text = ""
        val result = TestUtils.extractNumbersFromText(text)
        assertEquals(emptyList<Int>(), result)
    }

    @Test
    fun `test extractNumbersFromText with no numbers`() {
        val text = "Hello World"
        val result = TestUtils.extractNumbersFromText(text)
        assertEquals(emptyList<Int>(), result)
    }

    @Test
    fun `test calculateSumFromText basic functionality`() {
        val text = "1 2 3 4 5"
        val result = TestUtils.calculateSumFromText(text)
        assertEquals(15, result)
    }

    @Test
    fun `test calculateSumFromText with empty string`() {
        val text = ""
        val result = TestUtils.calculateSumFromText(text)
        assertEquals(0, result)
    }

    @Test
    fun `test validateNumberExtraction with valid input`() {
        val text = "123 456"
        val expected = listOf(123, 456)
        val result = TestUtils.validateNumberExtraction(text, expected)
        assertTrue(result)
    }

    @Test
    fun `test validateNumberExtraction with invalid input`() {
        val text = "123 456"
        val expected = listOf(123, 789) // Wrong expected result
        val result = TestUtils.validateNumberExtraction(text, expected)
        assertFalse(result)
    }

    @Test
    fun `test validateSumCalculation with valid input`() {
        val text = "1 2 3 4 5"
        val expectedSum = 15
        val result = TestUtils.validateSumCalculation(text, expectedSum)
        assertTrue(result)
    }

    @Test
    fun `test validateSumCalculation with invalid input`() {
        val text = "1 2 3 4 5"
        val expectedSum = 20 // Wrong expected sum
        val result = TestUtils.validateSumCalculation(text, expectedSum)
        assertFalse(result)
    }

    @Test
    fun `test generateTestText`() {
        val numbers = listOf(1, 2, 3, 4, 5)
        val result = TestUtils.generateTestText(numbers)
        assertEquals("Test numbers: 1 2 3 4 5", result)
    }

    @Test
    fun `test generateTestText with custom prefix`() {
        val numbers = listOf(10, 20, 30)
        val result = TestUtils.generateTestText(numbers, "Custom: ")
        assertEquals("Custom: 10 20 30", result)
    }

    @Test
    fun `test testRegexPattern with numbers`() {
        val text = "Hello 123 World"
        val result = TestUtils.testRegexPattern(text)
        assertTrue(result)
    }

    @Test
    fun `test testRegexPattern without numbers`() {
        val text = "Hello World"
        val result = TestUtils.testRegexPattern(text)
        assertFalse(result)
    }

    @Test
    fun `test performanceTest with small text`() {
        val text = "1 2 3 4 5"
        val result = TestUtils.performanceTest(text, 1000)
        assertTrue("Performance test should complete within 1 second", result)
    }

    @Test
    fun `test stressTest with reasonable size`() {
        val result = TestUtils.stressTest(100)
        assertTrue("Stress test should handle 100 numbers", result)
    }

    @Test
    fun `test comprehensive test suite`() {
        val result = TestUtils.runComprehensiveTestSuite()
        
        assertNotNull("Test result should not be null", result)
        assertTrue("All tests should pass", result.success)
        assertTrue("Success rate should be 100%", result.successRate == 1.0)
        assertEquals("No tests should fail", 0, result.failureCount)
    }

    @Test
    fun `test all test data cases`() {
        // Test empty text
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.emptyText, 
            TestUtils.ExpectedResults.emptyTextNumbers
        ))

        // Test no numbers text
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.noNumbersText, 
            TestUtils.ExpectedResults.noNumbersResult
        ))

        // Test single number
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.singleNumberText, 
            TestUtils.ExpectedResults.singleNumberResult
        ))

        // Test multiple numbers
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.multipleNumbersText, 
            TestUtils.ExpectedResults.multipleNumbersResult
        ))

        // Test large numbers
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.largeNumbersText, 
            TestUtils.ExpectedResults.largeNumbersResult
        ))

        // Test decimal numbers
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.decimalNumbersText, 
            TestUtils.ExpectedResults.decimalNumbersResult
        ))

        // Test special characters
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.specialCharactersText, 
            TestUtils.ExpectedResults.specialCharactersResult
        ))

        // Test whitespace
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.whitespaceText, 
            TestUtils.ExpectedResults.whitespaceResult
        ))

        // Test newlines
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.newlineText, 
            TestUtils.ExpectedResults.newlineResult
        ))

        // Test zero numbers
        assertTrue(TestUtils.validateNumberExtraction(
            TestUtils.TestData.zeroNumbersText, 
            TestUtils.ExpectedResults.zeroNumbersResult
        ))
    }

    @Test
    fun `test edge cases for number extraction`() {
        // Test with very large numbers
        val largeText = "999999999 1000000000"
        val result = TestUtils.extractNumbersFromText(largeText)
        assertEquals(listOf(999999999, 1000000000), result)

        // Test with negative numbers (regex doesn't handle negatives)
        val negativeText = "-5 10 -3"
        val negativeResult = TestUtils.extractNumbersFromText(negativeText)
        assertEquals(listOf(5, 10, 3), negativeResult)

        // Test with mixed content
        val mixedText = "Price: $99.99, Quantity: 5, Total: 149.50"
        val mixedResult = TestUtils.extractNumbersFromText(mixedText)
        assertEquals(listOf(99, 99, 5, 149, 50), mixedResult)
    }

    @Test
    fun `test performance characteristics`() {
        // Test that performance is reasonable for typical use cases
        val typicalText = "This is a typical text with some numbers: 1 2 3 4 5 6 7 8 9 10"
        val startTime = System.currentTimeMillis()
        val result = TestUtils.extractNumbersFromText(typicalText)
        val endTime = System.currentTimeMillis()
        
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), result)
        assertTrue("Processing should be fast", (endTime - startTime) < 100)
    }

    @Test
    fun `test memory usage`() {
        // Test that memory usage is reasonable
        val largeText = "Large text with many numbers: " + (1..1000).joinToString(" ") { it.toString() }
        
        val beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        val result = TestUtils.extractNumbersFromText(largeText)
        val afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        
        assertEquals(1000, result.size)
        val memoryIncrease = afterMemory - beforeMemory
        assertTrue("Memory increase should be reasonable", memoryIncrease < 10 * 1024 * 1024) // Less than 10MB
    }
}