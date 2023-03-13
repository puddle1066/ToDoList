package com.paul.todolist.di.database

import com.paul.todolist.di.database.data.Lists
import com.paul.todolist.di.database.data.ToDoItem
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
    suspend fun getList(): List<Lists> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().listDao().getAll()
        }
    }

    @Singleton
    @Provides
    suspend fun getToDoItems(): List<ToDoItem> {
        return withContext(dispatcher) {
            DataBaseManager.getInstance().ToDoItemDao().getAll()
        }
    }


}