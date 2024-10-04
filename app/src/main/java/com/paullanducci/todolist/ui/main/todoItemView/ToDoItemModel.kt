package com.paullanducci.todolist.ui.main.todoItemView

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.paullanducci.speech.input.InputDevice.InputDeviceListener
import com.paullanducci.speech.input.SpeechInputDevice
import com.paullanducci.speech.output.speech.SpeechOutputDevice
import com.paullanducci.todolist.ADD_TO_TOP
import com.paullanducci.todolist.base.BaseViewModel
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.di.database.data.ToDoDataItem
import com.paullanducci.todolist.di.database.data.ToDoImageData
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.util.encodeTobase64
import com.paullanducci.todolist.util.getCurrentDateAsString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
open class ToDoItemModel @Inject constructor(
    private val dataBaseProvider: RoomDataProvider

) : BaseViewModel(dataBaseProvider) {

    private var TAG = this::class.simpleName

    var todoDataItem = ToDoDataItem(
        UUID.randomUUID().toString(),
        MainActivity.listId,
        "",
        "0",
        getCurrentDateAsString(),
        "0",
        0
    )

    var todoItemIsNew: Boolean = false
    var isSpeechToTextEnabled = false
    var isPhotoCaptureEnabled = false

    lateinit var speechInputDevice: SpeechInputDevice
    lateinit var speechOutputDevice: SpeechOutputDevice

    fun loadData() {
        runBlocking {
            if (MainActivity.itemId.isBlank()) {
                var seq = dataBaseProvider.getLastSequence()
                todoDataItem = ToDoDataItem(
                    UUID.randomUUID().toString(),
                    MainActivity.listId,
                    "",
                    "0",
                    "0",
                    getCurrentDateAsString(),
                    seq + 1
                )
                todoItemIsNew = true
            } else {
                todoItemIsNew = false
                todoDataItem = dataBaseProvider.getToDoItem(MainActivity.itemId)
            }
        }
    }

    fun isNewItem(): Boolean {
        return todoItemIsNew
    }

    fun hasDescription(): Boolean {
        return todoDataItem.description.isNotEmpty()
    }

    fun getListTitle(listId: String): String {
        var title: String
        runBlocking {
            title = dataBaseProvider.getListTitle(listId)
        }
        return title
    }

    fun insert() {
        setDescription(todoDataItem.description)
        todoDataItem.itemId = UUID.randomUUID().toString()
        todoDataItem.lastupdated = getCurrentDateAsString()
        runBlocking {
            //Add new items to top or bottom of the list
            if (getOption(ADD_TO_TOP)) {
                todoDataItem.sequence = dataBaseProvider.getLastSequence() + 1
            } else {
                todoDataItem.sequence = dataBaseProvider.getFirstSequence() - 1
            }


            dataBaseProvider.insertToDo(todoDataItem)
        }
    }

    fun update() {

        todoDataItem.lastupdated = getCurrentDateAsString()
        setDescription(todoDataItem.description)
        runBlocking {
            dataBaseProvider.updateToDo(todoDataItem)
        }
    }

    fun getDescription(): String {
        return todoDataItem.description
    }

    fun setDescription(desc: String) {
        runBlocking {
            todoDataItem.description = desc.trim()
            todoDataItem.description = todoDataItem.description.replaceFirstChar(Char::uppercase)
        }
    }

    fun addPhotos(toDoImages: SnapshotStateList<Bitmap>) {
        runBlocking {
            toDoImages.forEach { bitmap ->
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
        val list: HashMap<String, String> = HashMap()
        runBlocking {
            val toDoListList = dataBaseProvider.getListOfLists()
            toDoListList.forEach {
                list[it.listId] = it.title
            }
        }
        return list
    }

    fun getToDoImages(itemId: String): List<ToDoImageData> {
        var list = listOf<ToDoImageData>()
        runBlocking {
            try {
                list = dataBaseProvider.getToDoImages(itemId)
            } catch (e: Exception) {
                Log.e(TAG, "getToDoImages - Failed $e")
            }
        }
        return list
    }


    fun deleteImage(key: String) {
        runBlocking {
            dataBaseProvider.deleteToDoImage(key)
        }
    }

    fun setupInputDeviceListeners(listener: InputDeviceListener?) {
        speechInputDevice.setInputDeviceListener(listener)
    }
}
