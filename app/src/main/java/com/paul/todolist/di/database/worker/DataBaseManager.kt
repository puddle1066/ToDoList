package com.paul.todolist.di.database.worker

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.paul.todolist.DATABASE_NAME
import com.paul.todolist.di.database.dao.ImageDataDao
import com.paul.todolist.di.database.dao.ListItemsDao
import com.paul.todolist.di.database.dao.ToDoItemsDao
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.listState_Finished
import com.paul.todolist.listState_Normal
import com.paul.todolist.listState_all_incomplete
import com.paul.todolist.ui.main.MainActivity
import java.util.UUID

@Database(
    entities = [
        ListDataItem::class,
        ToDoDataItem::class,
        ToDoImageData::class
    ], version = 1
)

abstract class DataBaseManager : RoomDatabase() {
    abstract fun listItemsDao(): ListItemsDao
    abstract fun toDoItemsDao(): ToDoItemsDao
    abstract fun imageDataDao(): ImageDataDao

    companion object {
        private val TAG = DataBaseManager::class.java.name

        private var instance: DataBaseManager? = null

        @Synchronized
        fun getInstance(): DataBaseManager {
            if (instance == null)
                instance = MainActivity.context?.let {
                    Room.databaseBuilder(
                        it, DataBaseManager::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.e(TAG, "Create Database")

                try {
                    db.execSQL(
                        "INSERT INTO ListsData(listId, title, type) VALUES('" + UUID.randomUUID()
                            .toString() + "','ToDo', '" + listState_Normal + "');"
                    )
                    db.execSQL(
                        "INSERT INTO ListsData(listId, title, type) VALUES('" + UUID.randomUUID()
                            .toString() + "','Finished', '" + listState_Finished + "');"
                    )
                    db.execSQL("INSERT INTO ListsData(listId, title, type) VALUES('0','All', '" + listState_all_incomplete + "');")

                } catch (ex: Exception) {
                    Log.e(TAG, "Error seeding database", ex)
                }
            }
        }
    }
}