package com.paul.todolist.ui.main.todoItemView

import android.util.Log
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.main.common.StorageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
open class ToDoItemModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

): StorageViewModel(dataStoreProvider) {
    var selectedlistId = ""

    fun getListTitle() : String {
        var title = ""
        getListId(
            {
                selectedlistId = it
                runBlocking {
                    title = dataBaseProvider.getListTitle(it)
                    Log.e("StorageViewModel","getting list Id $it $title")
                }
            }
        )
        return title
    }

    fun insertToDO(listId : String,  title : String) {
        runBlocking {
            dataBaseProvider.insertToDo(ToDoDataItem(UUID.randomUUID().toString(),listId,title.replaceFirstChar(Char::uppercase),"",""))
        }
    }

}
