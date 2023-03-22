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
    suspend fun getListAllRows(): List<ListDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getAll()
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

    @Singleton
    @Provides
    suspend fun getToDoItems(): List<ToDoDataItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemDao().getAll()
        }
    }

    suspend fun deleteItem(listId : String) {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().deleteListItem(listId)
        }
    }


}