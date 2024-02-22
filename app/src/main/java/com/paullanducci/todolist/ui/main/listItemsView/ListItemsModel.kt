package com.paullanducci.todolist.ui.main.listItemsView

import com.paullanducci.todolist.base.BaseViewModel
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.menuOptionSettings
import com.paullanducci.todolist.menuOptionToDoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
open class ListItemsModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
) : BaseViewModel(dataBaseProvider) {

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
