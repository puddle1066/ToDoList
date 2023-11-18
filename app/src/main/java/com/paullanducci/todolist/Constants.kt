package com.paullanducci.todolist

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.datastore.preferences.core.stringPreferencesKey
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
}

val menuOptionSettings =
    DrawItem(Icons.Filled.Settings, R.string.settings, ToDoScreens.SettingsView.name)
val menuOptionToDoList =
    DrawItem(Icons.Filled.Home, R.string.ToDo_Lists, ToDoScreens.ToDoListView.name)

const val DATABASE_NAME = "tools-db"
const val DATABASE_NAME_SHM = "$DATABASE_NAME-shm"
const val DATABASE_NAME_WAL = "$DATABASE_NAME-wal"

const val PREFERENCES_NAME = "user_preferences"

val LIST_ID_KEY = stringPreferencesKey("selectedlistId")

val SPEECH_LANGUAGE = "en"

const val listState_Normal = "0"
const val listState_Finished = "1"
const val listState_all_incomplete = "2"

const val imageWidth = 480
const val imageHeight = 640

const val dateFormat = "dd/MMM/yyyy hh:MM a"
