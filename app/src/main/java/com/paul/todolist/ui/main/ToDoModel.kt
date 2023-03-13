package com.paul.todolist.ui.main

import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class ToDoModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

): BaseViewModel() {

    private var todoItemList =  listOf<ToDoItem>()

    fun getBatchList() : List<ToDoItem> {
        runBlocking {
            todoItemList = dataBaseProvider.getToDoItems()
        }
        return todoItemList
    }

    fun deleteItem(seletcedItem: ToDoItem) {
    }
}
