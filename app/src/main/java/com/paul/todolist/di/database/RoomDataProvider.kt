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

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    @Singleton
    @Provides
    suspend fun getAllListsSorted(): List<ListDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listItemsDao().getAllSortedASC()
        }
    }

    @Singleton
    @Provides
    suspend fun getListOfLists(): List<ListDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listItemsDao().getUserDefinedLists()
        }
    }

    @Singleton
    @Provides
    suspend fun getLIstItemsCount(listId: String): Int {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listItemsDao().getListCount(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun insertList(list: ListDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listItemsDao().insert(list)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteList(listId: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listItemsDao().deleteList(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteItem(itemId: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemsDao().deleteItem(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun getListTitle(listId: String): String {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listItemsDao().getListTitle(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun getListItem(listId: String): ListDataItem {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listItemsDao().getListItem(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun getToDoItems(listId: String): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemsDao().getAllForListId(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun getAllIncompleteItems(): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemsDao().getAll()
        }
    }

    @Singleton
    @Provides
    suspend fun getFinishedItems(): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemsDao().getAllFinished()
        }
    }

    @Singleton
    @Provides
    suspend fun insertToDo(toDoItem: ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemsDao().insert(toDoItem)
        }
    }

    @Singleton
    @Provides
    suspend fun updateToDo(toDoItem: ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemsDao().update(toDoItem)
        }
    }

    @Singleton
    @Provides
    suspend fun setFinishedDate(itemId: String, finishedDate: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemsDao().setFinishedDate(itemId, finishedDate)
        }
    }

    @Singleton
    @Provides
    suspend fun getLastSequence(): Int {
        return withContext(dispatcher) {
            //Need to check for empty database
            if (DataBaseManager.getInstance().ToDoItemsDao().getLastSequence() != null) {
                DataBaseManager.getInstance().ToDoItemsDao().getLastSequence().display_sequence
            } else {
                0
            }
        }
    }

    @Singleton
    @Provides
    suspend fun getToDoItem(itemId: String): ToDoDataItem {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemsDao().getToDoItem(itemId)
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