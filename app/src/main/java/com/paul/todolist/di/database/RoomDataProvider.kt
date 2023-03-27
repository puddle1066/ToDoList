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
class RoomDataProvider  @Inject constructor() {

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
    suspend fun getListUserRows(): List<ListDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getUser()
        }
    }

    suspend fun insertList(list : ListDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().insert(list)
        }
    }

    suspend fun deleteItem(listId : String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().deleteListItem(listId)
        }
    }


    @Singleton
    @Provides
    suspend fun getListTitle(listId : String): String {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getListTitle(listId)
        }
    }


    @Singleton
    @Provides
    suspend fun getToDoItems(listId : String): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().getAllForListId(listId)
        }
    }

    suspend fun insertToDo(ToDo : ToDoDataItem) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoDao().insert(ToDo)
        }
    }


}