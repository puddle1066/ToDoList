package com.paullanducci.todolist.ui.main.settingsView

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import com.paullanducci.todolist.base.BaseViewModel
import com.paullanducci.todolist.di.database.RoomDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class SettingsModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

) : BaseViewModel() {

    fun closeDatabase() {
        dataBaseProvider.closeDatabase()
    }

    fun openDatabase() {
        dataBaseProvider.openDatabase()
    }

    fun getOverdueDays(): Int {
        var days: Int
        runBlocking {
            val daysAsString = dataBaseProvider.getConfigValue("OverdueDays")
            days = daysAsString.toInt()
        }
        return days
    }

    fun setOverdueDays(days: Int) {
        runBlocking {
            dataBaseProvider.setConfigValue("OverdueDays", days.toString())
        }
    }

    fun getOverdueColor(): Color {
        var color: Color
        runBlocking {
            color = Color(dataBaseProvider.getConfigValue("OverdueColor").toColorInt())
        }
        return color
    }

    fun setOverdueColor(color: Color) {
        runBlocking {
            dataBaseProvider.setConfigValue(
                "OverdueColor",
                "#" + Integer.toHexString(color.toArgb()).uppercase()
            )
        }

    }

    fun getLateDays(): Int {
        var days: Int
        runBlocking {
            days = dataBaseProvider.getConfigValue("LateDays").toInt()
        }
        return days
    }

    fun setLateDays(days: Int) {
        runBlocking {
            dataBaseProvider.setConfigValue("LateDays", days.toString())
        }
    }

    fun getLateColor(): Color {
        var lateColor: Color
        runBlocking {
            lateColor = Color(dataBaseProvider.getConfigValue("LateColor").toColorInt())
        }
        return lateColor
    }

    fun setLateColor(color: Color) {
        runBlocking {
            dataBaseProvider.setConfigValue(
                "LateColor",
                "#" + Integer.toHexString(color.toArgb()).uppercase()
            )
        }
    }
}
