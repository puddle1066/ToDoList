package com.paul.todolist.ui.main.todoListView

import com.paul.todolist.*
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.main.common.StorageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
open class ToDoListModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

): StorageViewModel(dataStoreProvider) {

    private var lists = listOf<ListDataItem>()
    private var toDoItems = listOf<ToDoDataItem>()

    var showAll = false
    var showFinished = false

    val menuItems = listOf(menuOptionLists, menuOptionSettings)
    var deleteList = ArrayList<ToDoDataItem>()

    fun getAllSortedASC(): List<ListDataItem> {
        runBlocking {
            lists = dataBaseProvider.getAllSortedASC()
        }
        return lists
    }

    fun getToDoList(listId: String): List<ToDoDataItem> {
        runBlocking {
            val todoItem: ListDataItem = dataBaseProvider.getListItem(listId)
            showFinished = false
            if (todoItem.fixed.equals("Y")) {
                showAll = todoItem.showAll.equals("Y")
                if (todoItem.showAll.equals("Y")) {
                    toDoItems = dataBaseProvider.getAllItems()
                } else {
                    showFinished = true
                    toDoItems = dataBaseProvider.getFinishedItems()
                }
            } else {
                toDoItems = dataBaseProvider.getToDoItems(listId)
            }
        }
        return toDoItems
    }

    fun showListName() : Boolean {
        var showListName = false
        getListId {
            runBlocking {
                val todoItem: ListDataItem = dataBaseProvider.getListItem(it)
                showListName = todoItem.fixed.equals("Y")
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
        when (title) {
            null ->  return PLEASE_SELECT_STRING
            ""   -> return PLEASE_SELECT_STRING
            else ->  return title
        }
    }

    fun getListTitleforId(listId : String) : String {
        var title : String
        runBlocking {
            title = dataBaseProvider.getListTitle(listId)
        }

        when (title) {
            null ->  return PLEASE_SELECT_STRING
            ""   -> return PLEASE_SELECT_STRING
            else ->  return title
        }
    }

    fun deleteItem(itemId : String) {
        runBlocking {
            dataBaseProvider.deleteItem(itemId)
        }
    }

    fun setFinishedDate(itemId : String,  finishedDate : String) {
        runBlocking {
            dataBaseProvider.setFinishedDate(itemId,finishedDate )
        }
    }
}
