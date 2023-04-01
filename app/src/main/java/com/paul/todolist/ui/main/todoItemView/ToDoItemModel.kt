package com.paul.todolist.ui.main.todoItemView

import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.ui.main.common.StorageViewModel
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
open class ToDoItemModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

): StorageViewModel(dataStoreProvider) {
    var todoItem = ToDoDataItem(UUID.randomUUID().toString(),"","","0","0")

    var canDoSpeechToText : Boolean = true
    lateinit var voiceToText  : VoiceToTextParser

    fun loadData() {
        getItemId {
            runBlocking {
                if (it.length == 0) {
                    todoItem = ToDoDataItem(UUID.randomUUID().toString(),"","","0","0")
                } else {
                    todoItem = dataBaseProvider.getToDoItem(it)
                }
            }
        }
        getListId {todoItem.listID = it }
    }

    fun isNewItem() : Boolean {
        return todoItem.description.length == 0
    }

    fun getListTitle() : String {
        var title = ""
        getListId(
            {
                runBlocking {
                    title = dataBaseProvider.getListTitle(it)
                }
            }
        )
        return title
    }

    fun insert() {
        todoItem.description = todoItem.description.replaceFirstChar(Char::uppercase)
        runBlocking {
            dataBaseProvider.insertToDo(todoItem)
        }
    }

    fun update() {
        todoItem.description = todoItem.description.replaceFirstChar(Char::uppercase)
        runBlocking {
            dataBaseProvider.updateToDo(todoItem)
        }
    }
}
