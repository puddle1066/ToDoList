package com.paul.todolist

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.paul.todoList.R
import com.paul.todolist.ui.main.draw.DrawItem

/**
 * enum values that represent the screens in the app
 */
enum class ToDoScreens(@StringRes val title: Int) {
    MainView(title = R.string.MainView),
    SettingsView(title = R.string.SettingsView)
}
val menuOptionSettings = DrawItem(Icons.Filled.Settings, R.string.settings, ToDoScreens.SettingsView.name)
val menuOptionMain = DrawItem(Icons.Filled.Home, R.string.settings, ToDoScreens.MainView.name)

const val DATABASE_NAME = "tools-db"
const val PREFERENCES_NAME = "user_preferences"

