package com.paullanducci.todolist.base

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import com.paullanducci.todolist.di.database.RoomDataProvider
import kotlinx.coroutines.runBlocking

open class BaseViewModel(private val dataBaseProvider: RoomDataProvider) : ViewModel() {

    fun getOption(key: String): Boolean {
        var showInstructions: String
        runBlocking {
            showInstructions = dataBaseProvider.getConfigValue(key)
        }
        return showInstructions.toBoolean()
    }

    fun getOptionString(key: String): String {
        var optionString: String
        runBlocking {
            optionString = dataBaseProvider.getConfigValue(key)
        }
        return optionString
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

    fun setOption(key: String, value: String) {
        runBlocking {
            dataBaseProvider.setConfigValue(key, value)
        }
    }

    fun setOption(key: String, flag: Boolean) {
        runBlocking {
            dataBaseProvider.setConfigValue(key, flag.toString())
        }
    }

}