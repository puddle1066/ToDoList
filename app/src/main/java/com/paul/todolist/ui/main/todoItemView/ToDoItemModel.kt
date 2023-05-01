package com.paul.todolist.ui.main.todoItemView

import android.graphics.Bitmap
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.main.common.StorageViewModel
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParser
import com.paul.todolist.util.encodeTobase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
open class ToDoItemModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider,
    private val dataStoreProvider: DataStoreProvider

) : StorageViewModel(dataStoreProvider) {

    var todoDataItem = ToDoDataItem(UUID.randomUUID().toString(), "", "", "0", "0", 0)

    var todoItemExists: Boolean = true
    var isSpeechToTextEnabled = false
    var isPhotoCaptureEnabled = true

    lateinit var voiceToText: VoiceToTextParser

    var addedBitmapList = ArrayList<Bitmap>()

    fun loadData() {
        runBlocking {
            if (MainView.itemID.isBlank()) {
                todoDataItem = ToDoDataItem(
                    UUID.randomUUID().toString(),
                    "",
                    "",
                    "0",
                    "0",
                    dataBaseProvider.getLastSequence() + 1
                )
                todoItemExists = false
            } else {
                todoItemExists = true
                todoDataItem = dataBaseProvider.getToDoItem(MainView.itemID)
            }
        }
        addedBitmapList.clear()
    }

    fun hasDataChanges(): Boolean {
        var hasChanges = true
        runBlocking {
            if (dataBaseProvider.getToDoItem(todoDataItem.itemId) == null) {
                hasChanges = true
            } else {
                hasChanges =
                    !dataBaseProvider.getToDoItem(todoDataItem.itemId)
                        .equals(todoDataItem.toString())
            }
        }
        return hasChanges
    }

    fun isNewItem(): Boolean {
        return todoDataItem.listID.isEmpty()
    }

    fun getListTitle(): String {
        var title = ""
        runBlocking {
            title = dataBaseProvider.getListTitle(MainView.listId)
        }
        return title
    }

    fun insert() {
        todoDataItem.description = todoDataItem.description.replaceFirstChar(Char::uppercase)
        todoDataItem.listID = MainView.listId
        runBlocking {
            todoDataItem.display_sequence = dataBaseProvider.getLastSequence() + 1
            dataBaseProvider.insertToDo(todoDataItem)
        }
    }

    fun update() {
        todoDataItem.description = todoDataItem.description.replaceFirstChar(Char::uppercase)
        runBlocking {
            dataBaseProvider.updateToDo(todoDataItem)
        }
    }

    fun addPhotos() {
        runBlocking {
            addedBitmapList.forEach { bitmap ->
                encodeTobase64(bitmap)?.let {
                    ToDoImageData(
                        UUID.randomUUID().toString(),
                        todoDataItem.itemId,
                        it
                    )
                }?.let { dataBaseProvider.insertToDoImage(it) }
            }
        }
    }

    fun getListOfLists(): HashMap<String, String> {
        val list: HashMap<String, String> = HashMap<String, String>()
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

    fun deleteImage(key: String) {
        runBlocking {
            dataBaseProvider.deleteToDoImage(key)
        }
    }
}
