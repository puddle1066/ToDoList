package com.paul.todolist.ui.main.todoItemView

import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
open class ToDoItemModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

): BaseViewModel() {

    fun insertToDO(listId : String,  title : String) {
        runBlocking {
            dataBaseProvider.insertToDo(ToDoDataItem(UUID.randomUUID().toString(),listId,title.replaceFirstChar(Char::uppercase),"",""))
        }
    }
}
