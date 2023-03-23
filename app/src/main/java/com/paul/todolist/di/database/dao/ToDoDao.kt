package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.di.database.data.ToDoDataItem

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ToDoDataItem: ToDoDataItem)

    @Query("SELECT * FROM ToDoItem")
    fun getAll(): List<ToDoDataItem>

    @Delete
    fun delete(List: ToDoDataItem)
}