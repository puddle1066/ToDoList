package com.paullanducci.todolist

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.paullanducci.todolist.ui.main.common.drawMenu.DrawItem

/**
 * enum values that represent the screens in the app
 */
enum class ToDoScreens(@StringRes val title: Int) {
    ToDoListView(title = R.string.ToDo_Lists),
    ToDoItemView(R.string.ToDo_item),
    listsView(R.string.lists),
    SettingsView(title = R.string.settings),
    ImageItemView(title = R.string.image_view),
    TutorialCarousel(title = R.string.tutoral_carousel),
    Clear_Finished_List(title = -1)
}

val menuOptionSettings =
    DrawItem(Icons.Filled.Settings, R.string.settings, ToDoScreens.SettingsView.name)
val menuOptionToDoList =
    DrawItem(Icons.Filled.Home, R.string.ToDo_Lists, ToDoScreens.ToDoListView.name)

const val DATABASE_NAME = "tools-db"

const val listState_Normal = "0"
const val listState_Finished = "1"
const val listState_all_incomplete = "2"

const val imageWidth = 480
const val imageHeight = 640

const val dateFormat = "dd/MMM/yyyy hh:MM a"

//Key values for settings fields in Database
const val SHOW_INSTRUCTIONS = "showInstructions"
const val ADD_TO_TOP = "addToTop"
const val OVERDUE_DAYS = "OverdueDays"
const val LATE_DAYS = "LateDays"
const val OVERDUE_COLOR = "OverdueColor"
const val LATE_COLOR = "LateColor"
const val LAST_LIST_ID = "lastlistId"
const val SPOKEN_LANGUAGE_OUTPUT = "enableSpokenLanguage"








