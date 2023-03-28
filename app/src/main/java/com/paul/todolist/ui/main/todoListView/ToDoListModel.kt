package com.paul.todolist.ui.main.todoListView

import android.util.Log
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.menuOptionLists
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.ui.main.common.StorageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class ToDoListModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

): StorageViewModel(dataStoreProvider) {

    private var lists = listOf<ListDataItem>()
    private var toDoItems = listOf<ToDoDataItem>()
    private var showAll = false

    val menuItems = listOf(menuOptionLists, menuOptionSettings)


    fun getAllSortedASC(): List<ListDataItem> {
        runBlocking {
            lists = dataBaseProvider.getAllSortedASC()
        }
        return lists
    }

    fun deleteItem(seletcedItem: ToDoDataItem) {
    }

    fun getToDoList(listId: String): List<ToDoDataItem> {
        runBlocking {
            if (dataBaseProvider.getShowAllItems(listId) == "Y") {
                toDoItems = dataBaseProvider.getAllItems()
                showAll = true
            } else {
                toDoItems = dataBaseProvider.getToDoItems(listId)
            }
        }
        return toDoItems
    }

    fun getListTitle() : String {
        var title = ""
        getListId(
            {
                runBlocking {
                    title = dataBaseProvider.getListTitle(it)
                    Log.e("StorageViewModel","getting list Id $it $title")
                }
            }
        )
        return title
    }
}
