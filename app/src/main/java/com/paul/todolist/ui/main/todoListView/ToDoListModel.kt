package com.paul.todolist.ui.main.todoListView

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


    fun getToDoList(listId: String): List<ToDoDataItem> {
        //TODO Replace hardcoded strings with strings
        runBlocking {
            var title = dataBaseProvider.getListTitle(listId)
            when(title) {
                "Finished" -> {
                    showAll = false
                    toDoItems = dataBaseProvider.getFinishedItems()
                }
                "All" -> {
                    toDoItems = dataBaseProvider.getAllItems()
                    showAll = true
                }
                else -> {
                    toDoItems = dataBaseProvider.getToDoItems(listId)
                    showAll = false
                }
            }
        }
        return toDoItems
    }

    fun getIsFinishedList() : Boolean {
        var finished = false
        getListId {
            runBlocking {
                finished = (dataBaseProvider.getListTitle(it).equals("Finished"))
            }
        }
        return finished
    }

    fun getListTitle() : String {
        var title = ""
        getListId(
            {
                runBlocking {
                    title = dataBaseProvider.getListTitle(it)
                }
            }
        )
        return title
    }

    fun setFinishedDate(itemId : String,  finishedDate : String) {
        runBlocking {
            dataBaseProvider.setFinishedDate(itemId,finishedDate )
        }
    }
}
