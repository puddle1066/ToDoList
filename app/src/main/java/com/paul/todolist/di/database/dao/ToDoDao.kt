package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ToDoDataItem

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoDataItem: ToDoDataItem)

    @Query("SELECT * FROM ToDoItem where listId = :listId AND FinishedDate = '0'")
    fun getAllForListId(listId : String): List<ToDoDataItem>

    @Query("SELECT * FROM ToDoItem where FinishedDate = '0'" )
    fun getAll() : List<ToDoDataItem>

    @Delete
    fun delete(List: ToDoDataItem)
}