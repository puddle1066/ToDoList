package com.paul.todolist.ui.main.todoListView

import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class ToDoListModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

): BaseViewModel() {

    private var lists =  listOf<ListDataItem>()
    private var toDoItem  = listOf<ToDoDataItem>()

    fun getList() : List<ListDataItem> {
        runBlocking {
            lists = dataBaseProvider.getList()
        }
        return lists
    }

    fun deleteItem(seletcedItem: ToDoDataItem) {
    }

    fun getBatchList() : List<ToDoDataItem> {
        runBlocking {
            toDoItem = dataBaseProvider.getToDoItems()
        }
        return toDoItem
    }

}
