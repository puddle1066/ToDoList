package com.paul.todolist.di.database.worker

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.paul.todolist.DATABASE_NAME
import com.paul.todolist.ToDoList
import com.paul.todolist.di.database.dao.*
import com.paul.todolist.di.database.data.*
import java.util.*

@Database(entities = [ListDataItem::class,ToDoDataItem::class], version = 1)
@TypeConverters(Converters::class)

abstract class DataBaseManager : RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun ToDoDao(): ToDoDao

    companion object {
        private val TAG = DataBaseManager::class.java.name

        private var instance: DataBaseManager? = null


        //TODO Dont reference TooboxApp conext as this will create a memory leak
        @Synchronized
        fun getInstance(): DataBaseManager {
            if(instance == null)
                instance = Room.databaseBuilder(ToDoList.appContext, DataBaseManager::class.java,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.e(TAG, "Create Database")

                try {
                    db.execSQL( "INSERT INTO Lists(listId, title, fixed, showAll) VALUES('"+ UUID.randomUUID().toString()+ "','ToDo', 'N', 'N');")
                    db.execSQL( "INSERT INTO Lists(listId, title, fixed, showAll) VALUES('"+ UUID.randomUUID().toString()+ "','Finished', 'Y', 'N');")
                    db.execSQL( "INSERT INTO Lists(listId, title, fixed, showAll) VALUES('0','All', 'Y', 'Y');")

                } catch (ex: Exception) {
                    Log.e(TAG, "Error seeding database", ex)
                }
            }
        }
    }
}