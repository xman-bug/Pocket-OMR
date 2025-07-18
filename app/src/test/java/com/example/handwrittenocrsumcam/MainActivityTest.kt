package com.example.handwrittenocrsumcam

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.*
import android.content.Context
import android.widget.TextView
import androidx.camera.view.PreviewView
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    @Mock
    private lateinit var mockContext: Context
    
    @Mock
    private lateinit var mockTextView: TextView
    
    @Mock
    private lateinit var mockPreviewView: PreviewView
    
    @Mock
    private lateinit var mockTextRecognizer: TextRecognizer
    
    @Mock
    private lateinit var mockInputImage: InputImage
    
    @Mock
    private lateinit var mockTask: Task<Text>
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test number extraction from text`() {
        // Test data with various number formats
        val testCases = mapOf(
            "Hello 123 World 456" to listOf(123, 456),
            "Sum: 1 + 2 + 3 = 6" to listOf(1, 2, 3, 6),
            "Price: $99.99 and $149.50" to listOf(99, 99, 149, 50),
            "No numbers here" to emptyList<Int>(),
            "123456789" to listOf(123456789),
            "Mixed 42 text 7 with 13 numbers" to listOf(42, 7, 13)
        )

        testCases.forEach { (input, expected) ->
            val result = extractNumbersFromText(input)
            assertEquals("Failed for input: '$input'", expected, result)
        }
    }

    @Test
    fun `test sum calculation`() {
        val numbers = listOf(1, 2, 3, 4, 5)
        val expectedSum = 15
        val actualSum = numbers.sum()
        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun `test empty number list sum`() {
        val numbers = emptyList<Int>()
        val expectedSum = 0
        val actualSum = numbers.sum()
        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun `test large number handling`() {
        val largeNumbers = listOf(999999, 1000000, 2147483647)
        val expectedSum = 999999 + 1000000 + 2147483647L
        val actualSum = largeNumbers.sum().toLong()
        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun `test negative number handling`() {
        val numbers = listOf(-5, 10, -3, 8)
        val expectedSum = -5 + 10 + -3 + 8
        val actualSum = numbers.sum()
        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun `test text with decimal numbers`() {
        // The current regex only matches integers, so decimals should be split
        val text = "Price: 12.34 and 56.78"
        val result = extractNumbersFromText(text)
        assertEquals(listOf(12, 34, 56, 78), result)
    }

    @Test
    fun `test text with special characters`() {
        val text = "Test@#$%^&*()123!@#$%^&*()456"
        val result = extractNumbersFromText(text)
        assertEquals(listOf(123, 456), result)
    }

    @Test
    fun `test text with multiple spaces`() {
        val text = "  123   456   789  "
        val result = extractNumbersFromText(text)
        assertEquals(listOf(123, 456, 789), result)
    }

    @Test
    fun `test text with newlines`() {
        val text = "Line 1: 123\nLine 2: 456\nLine 3: 789"
        val result = extractNumbersFromText(text)
        assertEquals(listOf(1, 123, 2, 456, 3, 789), result)
    }

    // Helper function to test the number extraction logic
    private fun extractNumbersFromText(text: String): List<Int> {
        val regex = Regex("\\d+")
        return regex.findAll(text)
            .map { it.value.toInt() }
            .toList()
    }

    @Test
    fun `test regex pattern matches correctly`() {
        val regex = Regex("\\d+")
        
        // Test various number patterns
        assertTrue(regex.containsMatchIn("123"))
        assertTrue(regex.containsMatchIn("abc123def"))
        assertTrue(regex.containsMatchIn("123.456"))
        assertFalse(regex.containsMatchIn("abc"))
        assertFalse(regex.containsMatchIn(""))
        assertFalse(regex.containsMatchIn(" "))
    }

    @Test
    fun `test number parsing edge cases`() {
        val testCases = mapOf(
            "0" to listOf(0),
            "000" to listOf(0),
            "00123" to listOf(123),
            "123000" to listOf(123000),
            "999999999" to listOf(999999999)
        )

        testCases.forEach { (input, expected) ->
            val result = extractNumbersFromText(input)
            assertEquals("Failed for input: '$input'", expected, result)
        }
    }

    @Test
    fun `test performance with large text`() {
        val largeText = "This is a very long text with many numbers: " +
            (1..1000).joinToString(" ") { it.toString() }
        
        val startTime = System.currentTimeMillis()
        val result = extractNumbersFromText(largeText)
        val endTime = System.currentTimeMillis()
        
        assertEquals(1000, result.size)
        assertTrue("Performance test took too long: ${endTime - startTime}ms", 
                  endTime - startTime < 1000) // Should complete within 1 second
    }
}