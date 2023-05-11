package com.paul.todolist.ui.main.todoListView

import android.util.Log
import com.paul.todoList.R
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.di.util.ResourcesProvider
import com.paul.todolist.listState_Finished
import com.paul.todolist.listState_Normal
import com.paul.todolist.listState_all_incomplete
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
    private val dataStoreProvider: DataStoreProvider,
    private val resourcesProvider: ResourcesProvider

) : StorageViewModel(dataStoreProvider) {

    private var TAG = this::class.simpleName

    private val _uiState = MutableStateFlow<List<ToDoDataItem>>(listOf())

    val uiState = _uiState.asStateFlow()

    fun swapSections(from: Int, to: Int) {
        val fromItem = _uiState.value[from]
        val toItem = _uiState.value[to]
        val newList = _uiState.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        _uiState.value = newList
    }

    fun getAllSortedASC(): List<ListDataItem> {
        var listDataItems = listOf<ListDataItem>()
        runBlocking {
            listDataItems = dataBaseProvider.getAllListsSorted()
        }
        return listDataItems
    }

    fun isListKnown(): Boolean {
        return MainView.listId.isBlank()
    }

    fun getToDoList(listId: String): List<ToDoDataItem> {
        var toDoDataItems = listOf<ToDoDataItem>()

        runBlocking {
            try {
                var todoListItem = dataBaseProvider.getListItem(listId)

                toDoDataItems = when (todoListItem.type) {
                    listState_all_incomplete ->
                        dataBaseProvider.getAllIncompleteItems()

                    listState_Finished ->
                        dataBaseProvider.getFinishedItems()

                    else ->
                        dataBaseProvider.getToDoItems(listId)
                }
                _uiState.value = toDoDataItems
            } catch (e: Exception) {
                Log.e(TAG, "getToDoList = exception thrown $e")
            }
        }
        return toDoDataItems
    }

    fun isFinishedList(): Boolean {
        return getItemType().equals(listState_Finished)
    }

    fun isNormalList(): Boolean {
        return getItemType().equals(listState_Normal)
    }

    fun isFullList(): Boolean {
        return getItemType().equals(listState_all_incomplete)
    }

    fun getItemType(): String {
        var type = "0"
        runBlocking {
            type = dataBaseProvider.getListItem(MainView.listId).type
        }
        return type
    }

    fun getListTitle(): String {
        var title = ""
        runBlocking {
            title = dataBaseProvider.getListTitle(MainView.listId)
        }
        return when (title) {
            null -> resourcesProvider.getString(R.string.please_select)
            "" -> resourcesProvider.getString(R.string.please_select)
            else -> title
        }
    }

    fun updateSequence() {
        var seq = 0
        _uiState.value.forEach {
            runBlocking {
                val todoitem = dataBaseProvider.getToDoItem(it.itemId)
                todoitem.display_sequence = seq
                seq++
                dataBaseProvider.updateToDo(todoitem)
            }
        }
    }

    fun getListTitleforId(listId: String): String {
        var title: String
        runBlocking {
            title = dataBaseProvider.getListTitle(listId)
        }

        when (title) {
            null -> return resourcesProvider.getString(R.string.please_select)
            "" -> return resourcesProvider.getString(R.string.please_select)
            else -> return title
        }
    }

    fun deleteItem(itemId: String) {
        runBlocking {
            try {
                dataBaseProvider.deleteAllToDoImages(itemId)
                dataBaseProvider.deleteItem(itemId)
            } catch (e: Exception) {
                Log.e(TAG, "getToDoImages - Failed $e")
            }
        }
    }

    fun setFinishedDate(itemId: String, finishedDate: String) {
        runBlocking {
            dataBaseProvider.setFinishedDate(itemId, finishedDate)
        }
    }
}

