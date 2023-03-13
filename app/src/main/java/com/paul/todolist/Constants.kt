package com.paul.todolist

import androidx.annotation.StringRes
import com.paul.todoList.R

/**
 * enum values that represent the screens in the app
 */
enum class ToDoScreens(@StringRes val title: Int) {
    MainView(title = R.string.MainView),
    SettingsView(title = R.string.SettingsView)
}

const val DATABASE_NAME = "tools-db"
const val PREFERENCES_NAME = "user_preferences"

val listOfStaffSizes =  listOf(Pair("50", "50 Cm"), Pair("100", "1 Meter"), Pair("150", "1.5 Meters"))
val distanceBetweenPoints =  listOf(Pair("1", "Variable"), Pair("2", "1 Meter"), Pair("3", "2 Meters"), Pair("4", "5 Meters"), Pair("5", "10 Meters"))

