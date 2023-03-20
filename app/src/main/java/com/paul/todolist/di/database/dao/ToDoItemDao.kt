package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ToDoDataItem

@Dao
interface ToDoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ToDOItem: List<ToDoDataItem>)

    @Query("SELECT * FROM ToDoItem")
    fun getAll(): List<ToDoDataItem>
    @Delete
    fun delete(List: ToDoDataItem)
}