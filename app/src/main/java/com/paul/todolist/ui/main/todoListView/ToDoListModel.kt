package com.paul.todolist.ui.main.todoListView

import com.paul.todolist.PLEASE_SELECT_STRING
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.listState_Finished
import com.paul.todolist.listState_Normal
import com.paul.todolist.listState_all_incomplete
import com.paul.todolist.menuOptionLists
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.main.common.StorageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class ToDoListModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

) : StorageViewModel(dataStoreProvider) {


    private var lists = listOf<ListDataItem>()

    var toDoItems = listOf<ToDoDataItem>()

    val _uiState = MutableStateFlow<List<ToDoDataItem>>(listOf())
    val uiState = _uiState.asStateFlow()

    fun swapSections(from: Int, to: Int) {
        val fromItem = _uiState.value[from]
        val toItem = _uiState.value[to]
        val newList = _uiState.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        _uiState.value = newList
    }

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

            when (todoListItem.type) {
                listState_all_incomplete ->
                    toDoItems = dataBaseProvider.getAllIncompleteItems()

                listState_Finished ->
                    toDoItems = dataBaseProvider.getFinishedItems()

                else ->
                    toDoItems = dataBaseProvider.getToDoItems(listId)
            }
        }
        _uiState.value = toDoItems
        return toDoItems
    }

    fun isFinishedList(): Boolean {
        return todoListItem.type.equals(listState_Finished)
    }

    fun isNormalList(): Boolean {
        return todoListItem.type.equals(listState_Normal)
    }

    fun isFullList(): Boolean {
        return todoListItem.type.equals(listState_all_incomplete)
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
