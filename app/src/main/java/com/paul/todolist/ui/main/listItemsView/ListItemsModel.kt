package com.paul.todolist.ui.main.listItemsView

import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.menuOptionSettings
import com.paul.todolist.menuOptionToDoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
open class ListItemsModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

): BaseViewModel() {

    val menuItems = listOf(menuOptionToDoList, menuOptionSettings)
    var deleteList = ArrayList<ListDataItem>()

    private var lists =  listOf<ListDataItem>()

    fun getUserList() : List<ListDataItem> {
        runBlocking {
            lists = dataBaseProvider.getListUserRows()
        }
        return lists
    }

    fun deleteItem(listId : String) {
        runBlocking {
            dataBaseProvider.deleteItem(listId)
        }
    }

     fun insertList(title : String) {
        runBlocking {
            dataBaseProvider.insertList(ListDataItem( UUID.randomUUID().toString(),title.replaceFirstChar(Char::uppercase),0))
        }
    }

}
