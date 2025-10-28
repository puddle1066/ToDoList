package com.paullanducci.todolist.ui.main.listItemsView

import com.paullanducci.todolist.base.BaseViewModel
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.di.database.data.ListDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
open class ListItemsModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
) : BaseViewModel(dataBaseProvider) {

    fun getUserDefinedLists(): List<ListDataItem> {
        var lists: List<ListDataItem>
        runBlocking {
            lists = dataBaseProvider.getUserDefinedLists()
        }
        return lists
    }

    fun getListCount(listId: String): Int {
        var count = 0
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

    fun updateListItem(listItem: ListDataItem) {
        runBlocking {
            dataBaseProvider.updateList(listItem)
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
