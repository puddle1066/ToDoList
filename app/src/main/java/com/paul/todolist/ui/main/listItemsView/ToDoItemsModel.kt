package com.paul.todolist.ui.main.listItemsView

import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.menuOptionToDoList
import com.paul.todolist.ui.main.common.StorageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
open class ToDoItemsModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

) : StorageViewModel(dataStoreProvider) {

    val menuItems = listOf(menuOptionToDoList, menuOptionSettings)
    var deleteList = ArrayList<ListDataItem>()

    private var count = 0

    fun getListOfLists(): List<ListDataItem> {
        var lists = listOf<ListDataItem>()
        runBlocking {
            lists = dataBaseProvider.getListOfLists()
        }
        return lists
    }

    fun getListCount(listId: String): Int {
        runBlocking {
            count = dataBaseProvider.getLIstItemsCount(listId)
        }
        return count
    }

    fun deleteItem(itemId: String) {
        runBlocking {
            dataBaseProvider.deleteItem(itemId)
        }
    }

    fun insertListItem(title: String) {
        runBlocking {
            dataBaseProvider.insertList(
                ListDataItem(
                    UUID.randomUUID().toString(),
                    title.replaceFirstChar(Char::uppercase),
                    "N"
                )
            )
        }
    }

}
