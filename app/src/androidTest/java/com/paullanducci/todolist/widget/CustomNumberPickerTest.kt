package com.paullanducci.todolist.widget

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.paullanducci.todolist.ui.widgets.CustomNumberPicker
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomNumberPickerTest {
    private var context: Context = getInstrumentation().targetContext.applicationContext

    @get: Rule
    val composeTestNumberPicker = createComposeRule()

    @Test
    fun numberPickerISOne() {
        composeTestNumberPicker.setContent {
            CustomNumberPicker(context).apply {
                setOnValueChangedListener { _, _, days ->
                    assert(days == 1)
                }
                minValue = 0
                maxValue = 24
                value = 1
            }
        }
    }


}