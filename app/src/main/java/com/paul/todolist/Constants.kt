package com.paul.todolist

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paul.todoList.R
import com.paul.todolist.ui.main.common.drawMenu.DrawItem

/**
 * enum values that represent the screens in the app
 */
enum class ToDoScreens(@StringRes val title: Int) {
    ToDoListView(title = R.string.ToDo_Lists),
    ToDoItemView(R.string.ToDo_item),
    listsView(R.string.lists),
    SettingsView(title = R.string.settings),
    ImageItemView(title = R.string.image_view),
}

val menuOptionSettings =
    DrawItem(Icons.Filled.Settings, R.string.settings, ToDoScreens.SettingsView.name)
val menuOptionLists = DrawItem(Icons.Filled.Menu, R.string.lists, ToDoScreens.listsView.name)
val menuOptionToDoList =
    DrawItem(Icons.Filled.Home, R.string.ToDo_Lists, ToDoScreens.ToDoListView.name)

const val DATABASE_NAME = "tools-db"
const val PREFERENCES_NAME = "user_preferences"

val LIST_ID_KEY = stringPreferencesKey("selectedlistId")

val SPEECH_LANGUAGE = "en"

const val listState_Normal = "0"
const val listState_Finished = "1"
const val listState_all_incomplete = "2"
