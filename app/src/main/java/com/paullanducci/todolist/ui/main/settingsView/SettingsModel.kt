package com.paullanducci.todolist.ui.main.settingsView

import com.paullanducci.todolist.base.BaseViewModel
import com.paullanducci.todolist.di.database.RoomDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SettingsModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

) : BaseViewModel(dataBaseProvider) {

    fun closeDatabase() {
        dataBaseProvider.closeDatabase()
    }

    fun openDatabase() {
        dataBaseProvider.openDatabase()
    }
}
