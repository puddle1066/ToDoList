package com.paul.todolist.ui.main.todoListView

import com.paul.todolist.*
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
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
        runBlocking {
            when(dataBaseProvider.getListTitle(listId)) {
                FINISHED_LIST -> {
                    showAll = false
                    toDoItems = dataBaseProvider.getFinishedItems()
                }
                LIST_OF_ALL -> {
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

    fun showListName() : Boolean {
        var showListName = false
        getListId {
            runBlocking {
                when(dataBaseProvider.getListTitle(it)) {
                    FINISHED_LIST  -> showListName = true
                    LIST_OF_ALL -> showListName = true
                }
            }
        }
        return showListName
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

        if (title == null || title.isEmpty() ) { title = PLEASE_SELECT_STRING}
        return title
    }

    fun getListTitleforId(listId : String) : String {
        var title = ""
        runBlocking {
            title = dataBaseProvider.getListTitle(listId)
        }
        return title
    }

    fun setFinishedDate(itemId : String,  finishedDate : String) {
        runBlocking {
            dataBaseProvider.setFinishedDate(itemId,finishedDate )
        }
    }
}
