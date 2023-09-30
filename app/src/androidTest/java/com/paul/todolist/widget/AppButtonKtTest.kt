package com.paul.todolist.widget

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paul.todolist.ui.widgets.AppButton
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppButtonKtTest {

    @get: Rule
    val composeTestTextButton = createComposeRule()

    @Test
    fun previewAppButtonText() {
        composeTestTextButton.setContent {
            AppButton(
                onButtonPressed = {},
                textID = com.paul.todolist.R.string.Continue
            )
        }
        composeTestTextButton.onNodeWithText("Continue").assertIsDisplayed()
    }

}