package com.paul.todolist.di.database.worker

import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.paul.todolist.DATABASE_NAME
import com.paul.todolist.di.database.dao.ConfigDao
import com.paul.todolist.di.database.dao.ImageDataDao
import com.paul.todolist.di.database.dao.ListItemsDao
import com.paul.todolist.di.database.dao.ToDoItemsDao
import com.paul.todolist.di.database.data.Config
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem
import com.paul.todolist.di.database.data.ToDoImageData
import com.paul.todolist.di.database.migrations.MIGRATION_1_2
import com.paul.todolist.listState_Finished
import com.paul.todolist.listState_Normal
import com.paul.todolist.listState_all_incomplete
import com.paul.todolist.ui.main.MainActivity
import java.util.UUID

@Database(
    entities = [
        ListDataItem::class,
        ToDoDataItem::class,
        ToDoImageData::class,
        Config::class
    ], version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)

abstract class DataBaseManager : RoomDatabase() {
    abstract fun getlistItemsDao(): ListItemsDao
    abstract fun getToDoItemsDao(): ToDoItemsDao
    abstract fun getImageDataDao(): ImageDataDao

    abstract fun getConfigDao(): ConfigDao

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
                        .addMigrations(MIGRATION_1_2)
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
                    db.execSQL("INSERT INTO ListsData(listId, title, type) VALUES('0','All', '$listState_all_incomplete');")

                } catch (ex: Exception) {
                    Log.e(TAG, "Error seeding database", ex)
                }
            }
        }
    }
}