package com.paul.todolist.di.database

import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
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

    suspend fun insertList(list: ListDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().insert(list)
        }
    }

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

    suspend fun getFinishedItems(): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().getAllFinished()
        }
    }

    suspend fun insertToDo(toDoItem: ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().insert(toDoItem)
        }
    }

    suspend fun updateToDo(toDoItem: ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().insert(toDoItem)
        }
    }

    suspend fun setFinishedDate(itemId: String, finishedDate: String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().setFinishedDate(itemId, finishedDate)
        }
    }

    suspend fun getToDoItem(itemId: String): ToDoDataItem {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().getToDoItem(itemId)
        }
    }

}