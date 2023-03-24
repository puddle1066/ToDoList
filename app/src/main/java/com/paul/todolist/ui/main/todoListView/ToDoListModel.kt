package com.paul.todolist.ui.main.todoListView

import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.menuOptionLists
import com.paul.todolist.menuOptionSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class ToDoListModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

): BaseViewModel() {

    private var lists =  listOf<ListDataItem>()
    private var toDoItem  = listOf<ToDoDataItem>()
    val menuItems  = listOf(menuOptionLists, menuOptionSettings)

    fun getAllSortedASC() : List<ListDataItem> {
        runBlocking {
            lists = dataBaseProvider.getAllSortedASC()
        }
        return lists
    }

    fun deleteItem(seletcedItem: ToDoDataItem) {
    }

    fun getToDoList(listId : String) : List<ToDoDataItem> {
        runBlocking {
            toDoItem = dataBaseProvider.getToDoItems(listId)
        }
        return toDoItem
    }

}
