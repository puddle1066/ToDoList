package com.paul.todolist.ui.main.todoListView

import com.paul.todolist.PLEASE_SELECT_STRING
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.menuOptionLists
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.main.common.StorageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class ToDoListModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

) : StorageViewModel(dataStoreProvider) {

    private var lists = listOf<ListDataItem>()
    private var toDoItems = listOf<ToDoDataItem>()

    var showAll = false
    var showFinished = false

    val menuItems = listOf(menuOptionLists, menuOptionSettings)
    var deleteList = ArrayList<ToDoDataItem>()

    lateinit var todoListItem: ListDataItem

    fun getAllSortedASC(): List<ListDataItem> {
        runBlocking {
            lists = dataBaseProvider.getAllSortedASC()
        }
        return lists
    }

    fun getToDoList(listId: String): List<ToDoDataItem> {
        runBlocking {
            todoListItem = dataBaseProvider.getListItem(listId)
            showFinished = false
            if (todoListItem.fixed.equals("Y")) {
                showAll = todoListItem.showAll.equals("Y")
                if (todoListItem.showAll.equals("Y")) {
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

    fun showListName(): Boolean {
        var showListName = false
            runBlocking {
                val todoItem: ListDataItem = dataBaseProvider.getListItem(MainView.listId)
                showListName = todoItem.fixed.equals("Y")
        }
        return showListName
    }

    fun getListTitle(): String {
        var title = ""
        runBlocking {
            title = dataBaseProvider.getListTitle(MainView.listId)
        }
        when (title) {
            null -> return PLEASE_SELECT_STRING
            "" -> return PLEASE_SELECT_STRING
            else -> return title
        }
    }

    fun getListTitleforId(listId: String): String {
        var title: String
        runBlocking {
            title = dataBaseProvider.getListTitle(listId)
        }

        when (title) {
            null -> return PLEASE_SELECT_STRING
            "" -> return PLEASE_SELECT_STRING
            else -> return title
        }
    }

    fun deleteItem(itemId: String) {
        runBlocking {
            dataBaseProvider.deleteAllToDoImages(itemId)
            dataBaseProvider.deleteItem(itemId)
        }
    }

    fun setFinishedDate(itemId: String, finishedDate: String) {
        runBlocking {
            dataBaseProvider.setFinishedDate(itemId, finishedDate)
        }
    }
}
