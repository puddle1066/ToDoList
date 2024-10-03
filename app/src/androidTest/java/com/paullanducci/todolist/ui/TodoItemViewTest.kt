package com.paullanducci.todolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemView
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import org.junit.Rule
import org.junit.Test

class TodoItemViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Composable
    @Test
    fun toDoItemView() {
        MainActivity.context = LocalContext.current

        composeTestRule.setContent {
            ToDoListTheme {
                ToDoItemView(
                    ToDoItemModel(
                        RoomDataProvider()
                    )
                )
            }
        }

        //   composeTestRule.onNodeWithText("Continue").performClick()

        //    composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
}