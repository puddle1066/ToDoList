package com.paul.todolist.ui.main.listItemsView

import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.database.RoomDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class ListItemsModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

): BaseViewModel() {

}
