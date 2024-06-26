package com.paullanducci.todolist.ui.main.todoListView

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.paullanducci.todolist.R
import com.paullanducci.todolist.base.BaseViewModel
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.di.database.data.ToDoDataItem
import com.paullanducci.todolist.listState_Finished
import com.paullanducci.todolist.listState_Normal
import com.paullanducci.todolist.listState_all_incomplete
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.todoItemView.ListItemAlertsData
import com.paullanducci.todolist.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class ToDoListModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val resourcesProvider: ResourcesProvider

) : BaseViewModel(dataBaseProvider) {

    private var TAG = this::class.simpleName

    private val _uiState = MutableStateFlow<List<ToDoDataItem>>(listOf())
    val uiState = _uiState.asStateFlow()

    fun swapSections(from: Int, to: Int) {
        val newList = _uiState.value.toMutableList()
        newList[from] = _uiState.value[to]
        newList[to] = _uiState.value[from]

        _uiState.value = newList
    }

    fun getAllSortedASC(): List<ListDataItem> {
        var listDataItems: List<ListDataItem>
        runBlocking {
            listDataItems = dataBaseProvider.getAllListsSorted()
        }
        return listDataItems
    }

    fun isListNotKnown(): Boolean {
        var notFound: Boolean
        runBlocking {
            notFound = dataBaseProvider.isValidListItem(MainActivity.listId) == 0
        }
        return notFound
    }

    fun getToDoList(listId: String) {
        var toDoDataItems: List<ToDoDataItem>

        runBlocking {
            try {
                val todoListItem = dataBaseProvider.getListItem(listId)

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
    }

    fun searchToDoList(listId: String, searchText: String) {
        var toDoDataItems: List<ToDoDataItem>

        runBlocking {
            try {
                val todoListItem = dataBaseProvider.getListItem(listId)

                toDoDataItems = when (todoListItem.type) {
                    listState_Finished ->
                        dataBaseProvider.searchFinishedItems(searchText)

                    else ->
                        dataBaseProvider.searchAllToDoItems(searchText)
                }
                _uiState.value = toDoDataItems
            } catch (e: Exception) {
                Log.e(TAG, "getToDoList = exception thrown $e")
            }
        }
    }

    fun isFinishedList(): Boolean {
        return getCurrentItemType() == listState_Finished
    }

    fun isNormalList(): Boolean {
        return getCurrentItemType() == listState_Normal
    }

    fun isFullList(): Boolean {
        return getCurrentItemType() == listState_all_incomplete
    }

    fun isFinished(finishedDate: String): Boolean {
        return finishedDate == "0"
    }

    fun getCurrentItemType(): String {
        var type = listState_Normal
        runBlocking {
            val item = dataBaseProvider.getListItem(MainActivity.listId)
            type = item.type // Else type has been removed
        }
        return type
    }

    fun getListTitle(): String {
        var title: String
        runBlocking {
            title = dataBaseProvider.getListTitle(MainActivity.listId)
        }
        return when (title) {
            null -> resourcesProvider.getString(R.string.please_select)
            "" -> resourcesProvider.getString(R.string.please_select)
            else -> title
        }
    }

    fun getListType(): String {
        var type: String
        runBlocking {
            type = dataBaseProvider.getListType(MainActivity.listId)
        }
        return when (type) {
            "" -> listState_Normal
            else -> type
        }
    }

    fun removeAllFinished() {
        runBlocking {
            dataBaseProvider.removeAllFinished()
        }
    }

    fun updateSequence() {
        var seq = 0
        _uiState.value.forEach {
            runBlocking {
                val todoitem = dataBaseProvider.getToDoItem(it.itemId)
                todoitem.sequence = seq
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
            "" -> {
                return resourcesProvider.getString(R.string.please_select)
            }
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

    fun getAlertValues(): ListItemAlertsData {
        return ListItemAlertsData(
            getOverdueDaysThreshold(),
            getOverdueColor(),
            getLateDaysTHreshold(),
            getLateColor()
        )
    }


    private fun getOverdueDaysThreshold(): Int {
        var days: Int
        runBlocking {
            days = try {
                dataBaseProvider.getConfigValue("OverdueDays").toInt()
            } catch (e: Exception) {
                0
            }
        }
        return days
    }

    private fun getOverdueColor(): Color {
        var color: Color
        runBlocking {
            try {
                color = Color(dataBaseProvider.getConfigValue("OverdueColor").toColorInt())
            } catch (e: Exception) {
                color = Color.White
                Log.e("color", "getOverdueColor - Exception  = ${e.message}")
            }
        }
        return color
    }

    private fun getLateDaysTHreshold(): Int {
        var days: Int
        runBlocking {
            days = try {
                dataBaseProvider.getConfigValue("LateDays").toInt()
            } catch (e: Exception) {
                0
            }
        }
        return days
    }

    private fun getLateColor(): Color {
        var lateColor: Color
        runBlocking {
            try {
                lateColor = Color(dataBaseProvider.getConfigValue("LateColor").toColorInt())
            } catch (e: Exception) {
                lateColor = Color.White
                Log.e("color", "getLateColor - Exception  = ${e.message}")
            }
        }
        return lateColor
    }
}

