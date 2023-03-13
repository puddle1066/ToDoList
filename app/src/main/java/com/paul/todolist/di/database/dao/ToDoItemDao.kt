package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.Lists
import com.paul.todolist.di.database.data.ToDoItem

@Dao
interface ToDoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ToDOItem: List<ToDoItem>)

    @Query("SELECT * FROM ToDoItem")
    fun getAll(): List<ToDoItem>
    @Delete
    fun delete(List: ToDoItem)
}