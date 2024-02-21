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


    fun getOption(key: String): Boolean {
        var showInstructions: String
        runBlocking {
            showInstructions = dataBaseProvider.getConfigValue(key)
        }
        return showInstructions.toBoolean()
    }

    fun getOptionInt(key: String): Int {
        var value: String
        runBlocking {
            value = dataBaseProvider.getConfigValue(key)
        }
        return value.toInt()
    }

    fun getOptionColor(key: String): Color {
        var lateColor: Color
        runBlocking {
            lateColor = Color(dataBaseProvider.getConfigValue("LateColor").toColorInt())
        }
        return lateColor
    }

    fun setOption(key: String, value: Color) {
        runBlocking {
            dataBaseProvider.setConfigValue(
                key,
                "#" + Integer.toHexString(value.toArgb()).uppercase()
            )
        }
    }

    fun setOption(key: String, value: Int) {
        runBlocking {
            dataBaseProvider.setConfigValue(key, value.toString())
        }
    }

    fun setOption(key: String, flag: Boolean) {
        runBlocking {
            dataBaseProvider.setConfigValue(key, flag.toString())
        }
    }


    fun closeDatabase() {
        dataBaseProvider.closeDatabase()
    }

    fun openDatabase() {
        dataBaseProvider.openDatabase()
    }
}
