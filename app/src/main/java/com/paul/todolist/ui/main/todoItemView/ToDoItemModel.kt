package com.paul.todolist.ui.main.todoItemView

import android.graphics.Bitmap
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.ui.main.common.StorageViewModel
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParser
import com.paul.todolist.util.encodeTobase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject


@HiltViewModel
open class ToDoItemModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

) : StorageViewModel(dataStoreProvider) {

    var todoItem = ToDoDataItem(UUID.randomUUID().toString(), "", "", "0", "0")

    var todoItemExists: Boolean = true
    var isSpeechToTextEnabled = false
    var isPhotoCaptureEnabled = true

    lateinit var voiceToText: VoiceToTextParser

    var addedBitmapList = ArrayList<Bitmap>()

    fun loadData() {
        getItemId {
            runBlocking {
                if (it.isEmpty()) {
                    todoItem = ToDoDataItem(UUID.randomUUID().toString(), "", "", "0", "0")
                    todoItemExists = false
                } else {
                    todoItemExists = true
                    todoItem = dataBaseProvider.getToDoItem(it)
                }
            }
        }
        getListId { todoItem.listID = it }
    }

    fun hasDataChanges(): Boolean {
        var hasChanges = true
        runBlocking {
            val dbData = dataBaseProvider.getToDoItem(todoItem.itemId)
            if (dbData == null) {
                hasChanges = true
            } else {
                hasChanges = !dbData.equals(todoItem.toString())
            }
        }
        return hasChanges
    }

    fun isNewItem(): Boolean {
        return todoItem.description.isEmpty()
    }

    fun getListTitle(): String {
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

    fun addPhotos() {
        runBlocking {
            addedBitmapList.forEach { bitmap ->
                encodeTobase64(bitmap)?.let {
                    ToDoImageData(
                        UUID.randomUUID().toString(),
                        todoItem.itemId,
                        it
                    )
                }?.let { dataBaseProvider.insertToDoImage(it) }
            }
        }
    }

    fun getListOfLists(): HashMap<String, String> {
        var list: HashMap<String, String> = HashMap<String, String>()
        runBlocking {
            var toDoListList = dataBaseProvider.getListOfLists()
            toDoListList.forEach {
                list.put(it.listId, it.title)
            }
        }
        return list
    }

    fun getToDoImages(itemId: String): List<ToDoImageData> {
        var list = listOf<ToDoImageData>()
        runBlocking {
            list = dataBaseProvider.getToDoImages(itemId)
            if (list.isEmpty()) {
                list = listOf<ToDoImageData>()
            }
        }
        return list
    }

}
