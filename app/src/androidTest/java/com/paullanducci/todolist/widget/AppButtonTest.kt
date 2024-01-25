package com.paullanducci.todolist.widget

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paullanducci.todolist.ui.widgets.AppButton
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppButtonTest {

    @get: Rule
    val composeTestTextButton = createComposeRule()

    @Test
    fun previewAppButtonText() {
        composeTestTextButton.setContent {
            AppButton(
                onButtonPressed = {},
                textID = com.paullanducci.todolist.R.string.Continue
            )
        }
        composeTestTextButton.onNodeWithText("Continue").assertIsDisplayed()
    }

    @Test
    fun previewAppButtonFreeFormatText() {
        var buttonText = "Free Format Text"
        composeTestTextButton.setContent {
            AppButton(
                onButtonPressed = {},
                textString = buttonText
            )
        }
        composeTestTextButton.onNodeWithText(buttonText).assertIsDisplayed()
    }

}