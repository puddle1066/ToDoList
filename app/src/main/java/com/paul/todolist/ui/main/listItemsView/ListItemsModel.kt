package com.paul.todolist.ui.main.listItemsView

import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.menuOptionToDoList
import com.paul.todolist.ui.main.common.StorageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
open class ListItemsModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

) : StorageViewModel(dataStoreProvider) {

    val menuItems = listOf(menuOptionToDoList, menuOptionSettings)
    var deleteList = ArrayList<ListDataItem>()

    private var count = 0

    fun getListOfLists(): List<ListDataItem> {
        var lists: List<ListDataItem>
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

    fun deleteListId(listId: String) {
        runBlocking {
            dataBaseProvider.deleteList(listId)
        }
    }

    fun insertListItem(title: String) {
        runBlocking {
            dataBaseProvider.insertList(
                ListDataItem(
                    UUID.randomUUID().toString(),
                    title.replaceFirstChar(Char::uppercase),
                    "0"
                )
            )
        }
    }

}
