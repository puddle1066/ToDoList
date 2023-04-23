package com.paul.todolist.di.database

import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.di.database.worker.DataBaseManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RoomDataProvider @Inject constructor() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    suspend fun getAllSortedASC(): List<ListDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getAllSortedASC()
        }
    }

    @Singleton
    @Provides
    suspend fun getListOfLists(): List<ListDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getUser()
        }
    }

    @Singleton
    @Provides
    suspend fun getLIstItemsCount(listId: String): Int {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getListCount(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun insertList(list: ListDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().insert(list)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteItem(itemId: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().deleteItem(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun getListTitle(listId: String): String {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getListTitle(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun getListItem(listId: String): ListDataItem {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getListItem(listId)
        }
    }


    @Singleton
    @Provides
    suspend fun getToDoItems(listId: String): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().getAllForListId(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun getAllItems(): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().getAll()
        }
    }

    @Singleton
    @Provides
    suspend fun getFinishedItems(): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().getAllFinished()
        }
    }

    @Singleton
    @Provides
    suspend fun insertToDo(toDoItem: ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().insert(toDoItem)
        }
    }

    @Singleton
    @Provides
    suspend fun updateToDo(toDoItem: ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().update(toDoItem)
        }
    }

    @Singleton
    @Provides
    suspend fun setFinishedDate(itemId: String, finishedDate: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().setFinishedDate(itemId, finishedDate)
        }
    }

    @Singleton
    @Provides
    suspend fun getLastSequence(): Int {
        return withContext(dispatcher) {
            if (DataBaseManager.getInstance().ToDoDao().getLastSequence() != null) {
                DataBaseManager.getInstance().ToDoDao().getLastSequence().display_sequence
            } else {
                0
            }
        }
    }

    @Singleton
    @Provides
    suspend fun getToDoItem(itemId: String): ToDoDataItem {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().getToDoItem(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun insertToDoImage(toDoImage: ToDoImageData) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ImageDataDao().insert(toDoImage)
        }
    }

    @Singleton
    @Provides
    suspend fun getToDoImages(itemId: String): List<ToDoImageData> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ImageDataDao().getItemImages(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteAllToDoImages(itemId: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ImageDataDao().deleteAllImagesForItem(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteToDoImage(key: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ImageDataDao().deleteImage(key)
        }
    }
}