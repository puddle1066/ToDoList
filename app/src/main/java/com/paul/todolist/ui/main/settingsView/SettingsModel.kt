package com.paul.todolist.ui.main.settingsView

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.database.RoomDataProvider
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
            days = try {
                dataBaseProvider.getConfigValue("OverdueDays").toInt()
            } catch (e: Exception) {
                0
            }
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
            color = try {
                Color(dataBaseProvider.getConfigValue("OverdueColor").toColorInt())
            } catch (e: Exception) {
                Color.Green
            }
        }
        return color
    }

    fun setOverdueColor(color: Color) {
        runBlocking {
            dataBaseProvider.setConfigValue("OverdueColor", color.toString())
        }
    }

    fun getLateDays(): Int {
        var days: Int
        runBlocking {
            days = try {
                dataBaseProvider.getConfigValue("LateDays").toInt()
            } catch (e: Exception) {
                0
            }
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
            lateColor = try {
                Color(dataBaseProvider.getConfigValue("LateColor").toColorInt())
            } catch (e: Exception) {
                Color.Green
            }
        }
        return lateColor
    }

    fun setLateColor(color: Color) {
        runBlocking {
            dataBaseProvider.setConfigValue("LateColor", color.toString())
        }
    }
}
