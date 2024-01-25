package com.paullanducci.todolist.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import com.paullanducci.todolist.di.database.dao.ImageDataDao
import com.paullanducci.todolist.di.database.dao.ListItemsDao
import com.paullanducci.todolist.di.database.dao.ToDoItemsDao
import com.paullanducci.todolist.di.database.worker.DataBaseManager
import com.paullanducci.todolist.listState_Finished
import com.paullanducci.todolist.listState_Normal
import com.paullanducci.todolist.listState_all_incomplete
import org.junit.After
import org.junit.Before

open class BaseDataProviderTest {
    private lateinit var testDb: DataBaseManager

    lateinit var imageDataDao: ImageDataDao
    lateinit var listItemsDao: ListItemsDao
    lateinit var toDoItemsDao: ToDoItemsDao

    private val testRoomCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            db.execSQL(
                "INSERT INTO ListsData(listId, title, type) VALUES('1','ToDo', '$listState_Normal');"
            )
            db.execSQL(
                "INSERT INTO ListsData(listId, title, type) VALUES('2','Finished', '$listState_Finished');"
            )
            db.execSQL("INSERT INTO ListsData(listId, title, type) VALUES('3','All', '$listState_all_incomplete');")
        }
    }

    @Before
    fun createDb() {
        testDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataBaseManager::class.java
        )
            .allowMainThreadQueries()
            .addCallback(testRoomCallback)
            .build()

        imageDataDao = testDb.getImageDataDao()
        listItemsDao = testDb.getlistItemsDao()
        toDoItemsDao = testDb.getToDoItemsDao()
    }

    @After
    fun closeDatabase() {
        testDb.close()
    }
}