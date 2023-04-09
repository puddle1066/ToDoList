package com.paul.todolist.ui.main.todoItemView

import com.paul.todoList.R
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
    var todoItemExists : Boolean = true
    lateinit var voiceToText  : VoiceToTextParser

    fun loadData() {
        getItemId {
            runBlocking {
                if (it.isEmpty()) {
                    todoItem = ToDoDataItem(UUID.randomUUID().toString(),"","","0","0")
                    todoItemExists = false
                } else {
                    todoItemExists = true
                    todoItem = dataBaseProvider.getToDoItem(it)
                }
            }
        }
        getListId {todoItem.listID = it }
    }

    fun hasDataChanges() : Boolean {
        var hasChanges = true
        runBlocking {
            var dbData = dataBaseProvider.getToDoItem(todoItem.itemId)
            if (dbData == null) {
                hasChanges = true
            } else {
                if (dbData.equals(todoItem.toString())
                ) {
                    hasChanges = false
                }
            }
        }
        return hasChanges
    }

    fun getButtonText() : Int {
        if (todoItemExists) {
            return R.string.update_todo
        } else {
            return R.string.add_todo

        }
    }

    fun isNewItem() : Boolean {
        return todoItem.description.isEmpty()
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


    fun getListOfLists() : HashMap<String , String> {
        var list: HashMap<String , String> = HashMap<String, String>()
        runBlocking {
        var toDoListList  = dataBaseProvider.getListOfLists()
            toDoListList.forEach {
                list.put(it.listId ,it.title)
            }
        }
        return list
    }
}
