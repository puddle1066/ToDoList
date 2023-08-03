package com.paul.todolist.di.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.paul.todolist.di.database.dao.ImageDataDao
import com.paul.todolist.di.database.dao.ListItemsDao
import com.paul.todolist.di.database.dao.ToDoItemsDao
import com.paul.todolist.di.database.worker.DataBaseManager
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test

internal class RoomDataProviderTest {

    private lateinit var database: DataBaseManager
    private lateinit var imageDataDao: ImageDataDao
    private lateinit var listItemsDao: ListItemsDao
    private lateinit var toDoItemsDao: ToDoItemsDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataBaseManager::class.java
        ).allowMainThreadQueries().build()

        imageDataDao = database.imageDataDao()
        listItemsDao = database.listItemsDao()
        toDoItemsDao = database.toDoItemsDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun getAllListsSorted() {
    }

    @Test
    fun getListOfLists() {
    }

    @Test
    fun getLIstItemsCount() {
    }

    @Test
    fun insertList() {
    }

    @Test
    fun deleteList() {
    }

    @Test
    fun deleteItem() {
    }

    @Test
    fun getListTitle() {
    }

    @Test
    fun getListItem() {
    }

    @Test
    fun isValidListItem() {
    }

    @Test
    fun getToDoItems() {
    }

    @Test
    fun getAllIncompleteItems() {
    }

    @Test
    fun getFinishedItems() {
    }

    @Test
    fun insertToDo() {
    }

    @Test
    fun updateToDo() {
    }

    @Test
    fun setFinishedDate() {
    }

    @Test
    fun getLastSequence() {
    }

    @Test
    fun getToDoItem() {
    }

    @Test
    fun insertToDoImage() {
    }

    @Test
    fun getToDoImages() {
    }

    @Test
    fun deleteAllToDoImages() {
    }

    @Test
    fun deleteToDoImage() {
    }
}