package com.paullanducci.todolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createComposeRule
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.todoListView.ToDoListModel
import com.paullanducci.todolist.ui.main.todoListView.ToDoListView
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.util.ResourcesProvider
import org.junit.Rule
import org.junit.Test

class TodoListViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Composable
    @Test
    fun toDoListView() {
        MainActivity.context = LocalContext.current

        composeTestRule.setContent {
            ToDoListTheme {
                ToDoListView(
                    ToDoListModel(
                        RoomDataProvider(),
                        ResourcesProvider(LocalContext.current)
                    )
                )
            }
        }

        val mySwitch = SemanticsMatcher.expectValue(
            SemanticsProperties.Role, Role.Button
        )

        //Test Menu Option
//        composeTestRule.onNode(mySwitch).performClick()
//
//        composeTestRule.onNodeWithText("Lists Maintenance").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
    }
}