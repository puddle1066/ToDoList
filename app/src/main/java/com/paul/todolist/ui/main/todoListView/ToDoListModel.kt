package com.paul.todolist.ui.main.todoListView

import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.Lists
import com.paul.todolist.di.database.data.ToDoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class ToDoListModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

): BaseViewModel() {

    private var lists =  listOf<Lists>()
    private var toDoItem  = listOf<ToDoItem>()

    fun getList() : List<Lists> {
        runBlocking {
            lists = dataBaseProvider.getList()
        }
        return lists
    }

    fun deleteItem(seletcedItem: ToDoItem) {
    }

    fun getBatchList() : List<ToDoItem> {
        runBlocking {
            toDoItem = dataBaseProvider.getToDoItems()
        }
        return toDoItem
    }

}
