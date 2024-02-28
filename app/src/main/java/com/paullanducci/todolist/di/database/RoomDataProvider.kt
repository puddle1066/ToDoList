package com.paullanducci.todolist.di.database

import android.util.Log
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.di.database.data.ToDoDataItem
import com.paullanducci.todolist.di.database.data.ToDoImageData
import com.paullanducci.todolist.di.database.worker.DataBaseManager
import com.paullanducci.todolist.ui.main.MainActivity.Companion.dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RoomDataProvider @Inject constructor() {

    private var TAG = RoomDataProvider::class.simpleName

    fun closeDatabase() {
        if (DataBaseManager.getInstance().isOpen) {
            DataBaseManager.getInstance().openHelper.close()
        }
    }

    fun openDatabase() {
        if (!DataBaseManager.getInstance().isOpen) {
            DataBaseManager.getInstance().openHelper.writableDatabase
        }
    }

    @Singleton
    @Provides
    suspend fun getConfigValue(key: String): String {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getConfigDao().getConfigValue(key)
        }
    }

    @Singleton
    @Provides
    suspend fun setConfigValue(key: String, value: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getConfigDao().setConfigValue(key, value)
        }
    }

    @Singleton
    @Provides
    suspend fun getAllListsSorted(): List<ListDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getlistItemsDao().getAllSortedASC()
        }
    }


    @Singleton
    @Provides
    suspend fun getListOfLists(): List<ListDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getlistItemsDao().getUserDefinedLists()
        }
    }

    @Singleton
    @Provides
    suspend fun getLIstItemsCount(listId: String): Int {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().getListCount(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun insertList(list: ListDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getlistItemsDao().insert(list)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteList(listId: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getlistItemsDao().deleteList(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteItem(itemId: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().deleteItem(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun getListTitle(listId: String): String {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getlistItemsDao().getListTitle(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun getListItem(listId: String): ListDataItem {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getlistItemsDao().getListItem(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun isValidListItem(listId: String): Int {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getlistItemsDao().getListItemCount(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun getToDoItems(listId: String): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().getAllForListId(listId)
        }
    }

    @Singleton
    @Provides
    suspend fun getAllIncompleteItems(): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().getAll()
        }
    }

    @Singleton
    @Provides
    suspend fun getFinishedItems(): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().getAllFinished()
        }
    }

    @Singleton
    @Provides
    suspend fun insertToDo(toDoItem: ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().insert(toDoItem)
        }
    }

    @Singleton
    @Provides
    suspend fun updateToDo(toDoItem: ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().update(toDoItem)
        }
    }

    @Singleton
    @Provides
    suspend fun setFinishedDate(itemId: String, finishedDate: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().setFinishedDate(itemId, finishedDate)
        }
    }

    @Singleton
    @Provides
    suspend fun getLastSequence(): Int {
        return withContext(dispatcher) {
            try {
                DataBaseManager.getInstance().getToDoItemsDao().getLastSequence().sequence
            } catch (e: Exception) {
                Log.e(TAG, "getLastSequence - $e")
            }
        }
    }

    @Singleton
    @Provides
    suspend fun getFirstSequence(): Int {
        return withContext(dispatcher) {
            try {
                DataBaseManager.getInstance().getToDoItemsDao().getFirstSequence().sequence
            } catch (e: Exception) {
                Log.e(TAG, "getFirstSequence - $e")
            }
        }
    }

    @Singleton
    @Provides
    suspend fun getToDoItem(itemId: String): ToDoDataItem {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getToDoItemsDao().getToDoItem(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun insertToDoImage(toDoImage: ToDoImageData) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getImageDataDao().insert(toDoImage)
        }
    }

    @Singleton
    @Provides
    suspend fun getToDoImages(itemId: String): List<ToDoImageData> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getImageDataDao().getItemImages(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteAllToDoImages(itemId: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getImageDataDao().deleteAllImagesForItem(itemId)
        }
    }

    @Singleton
    @Provides
    suspend fun deleteToDoImage(key: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().getImageDataDao().deleteImage(key)
        }
    }
}