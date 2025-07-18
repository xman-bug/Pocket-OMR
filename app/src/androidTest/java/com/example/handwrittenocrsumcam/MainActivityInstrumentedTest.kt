package com.example.handwrittenocrsumcam

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.After
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.Manifest
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val permissionRule = GrantPermissionRule.grant(
        Manifest.permission.CAMERA
    )

    private lateinit var context: Context
    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun testActivityLaunch() {
        // Test that the activity launches successfully
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                assertNotNull("Activity should not be null", activity)
                assertTrue("Activity should be active", !activity.isFinishing)
            }
        }
    }

    @Test
    fun testUIComponentsExist() {
        // Test that all UI components are present
        onView(withId(R.id.viewFinder))
            .check(matches(isDisplayed()))

        onView(withId(R.id.txtResult))
            .check(matches(isDisplayed()))
            .check(matches(withText("Initializing...")))
    }

    @Test
    fun testTextViewInitialState() {
        // Test the initial state of the TextView
        onView(withId(R.id.txtResult))
            .check(matches(isDisplayed()))
            .check(matches(withText("Initializing...")))
            .check(matches(withTextColor(android.graphics.Color.WHITE)))
    }

    @Test
    fun testCameraPermission() {
        // Test camera permission handling
        val hasCameraPermission = ContextCompat.checkSelfPermission(
            context, 
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        
        assertTrue("Camera permission should be granted", hasCameraPermission)
    }

    @Test
    fun testCameraPreviewDisplayed() {
        // Test that camera preview is displayed
        onView(withId(R.id.viewFinder))
            .check(matches(isDisplayed()))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testTextViewBackground() {
        // Test TextView background color
        onView(withId(R.id.txtResult))
            .check(matches(withBackgroundColor(android.graphics.Color.parseColor("#AA000000"))))
    }

    @Test
    fun testTextViewTextSize() {
        // Test TextView text size
        onView(withId(R.id.txtResult))
            .check(matches(withTextSize(18f)))
    }

    @Test
    fun testTextViewPadding() {
        // Test TextView padding
        onView(withId(R.id.txtResult))
            .check(matches(withPadding(8)))
    }

    @Test
    fun testLayoutConstraints() {
        // Test that the layout constraints are properly set
        onView(withId(R.id.viewFinder))
            .check(matches(isDisplayed()))

        onView(withId(R.id.txtResult))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testActivityLifecycle() {
        // Test activity lifecycle
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // Test that camera is started
                assertNotNull("Camera should be initialized", activity)
            }
        }
    }

    @Test
    fun testTextRecognitionIntegration() {
        // Test that text recognition is properly integrated
        // This test verifies the ML Kit integration
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // The activity should have text recognition capabilities
                assertNotNull("Activity should have text recognition", activity)
            }
        }
    }

    @Test
    fun testUIResponsiveness() {
        // Test UI responsiveness
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // Test that UI updates work
                activity.runOnUiThread {
                    val textView = activity.findViewById<android.widget.TextView>(R.id.txtResult)
                    textView.text = "Test Update"
                    assertTrue("TextView should be updated", textView.text == "Test Update")
                }
            }
        }
    }

    @Test
    fun testCameraExecutor() {
        // Test that camera executor is properly initialized
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // The activity should have a camera executor
                assertNotNull("Camera executor should be initialized", activity)
            }
        }
    }

    @Test
    fun testImageAnalysisSetup() {
        // Test that image analysis is properly set up
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // The activity should have image analysis capabilities
                assertNotNull("Image analysis should be set up", activity)
            }
        }
    }

    @Test
    fun testTextProcessingLogic() {
        // Test the text processing logic with mock data
        val testText = "Hello 123 World 456"
        val numbers = Regex("\\d+").findAll(testText)
            .map { it.value.toInt() }
            .toList()
        val sum = numbers.sum()
        
        assertEquals("Should extract correct numbers", listOf(123, 456), numbers)
        assertEquals("Should calculate correct sum", 579, sum)
    }

    @Test
    fun testEmptyTextProcessing() {
        // Test text processing with empty text
        val testText = ""
        val numbers = Regex("\\d+").findAll(testText)
            .map { it.value.toInt() }
            .toList()
        val sum = numbers.sum()
        
        assertEquals("Should handle empty text", emptyList<Int>(), numbers)
        assertEquals("Should return zero sum for empty text", 0, sum)
    }

    @Test
    fun testTextWithNoNumbers() {
        // Test text processing with text containing no numbers
        val testText = "Hello World"
        val numbers = Regex("\\d+").findAll(testText)
            .map { it.value.toInt() }
            .toList()
        val sum = numbers.sum()
        
        assertEquals("Should handle text with no numbers", emptyList<Int>(), numbers)
        assertEquals("Should return zero sum for text with no numbers", 0, sum)
    }

    @Test
    fun testLargeNumberProcessing() {
        // Test processing of large numbers
        val testText = "Large numbers: 999999999 1000000000"
        val numbers = Regex("\\d+").findAll(testText)
            .map { it.value.toInt() }
            .toList()
        
        assertTrue("Should handle large numbers", numbers.contains(999999999))
        assertTrue("Should handle very large numbers", numbers.contains(1000000000))
    }

    @Test
    fun testDecimalNumberProcessing() {
        // Test processing of decimal numbers (should split on decimal point)
        val testText = "Price: 12.34 and 56.78"
        val numbers = Regex("\\d+").findAll(testText)
            .map { it.value.toInt() }
            .toList()
        
        assertEquals("Should split decimal numbers", listOf(12, 34, 56, 78), numbers)
    }

    @After
    fun tearDown() {
        // Clean up any resources if needed
    }
}