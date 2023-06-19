package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ToDoDataItem

@Dao
interface ToDoItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoDataItem: ToDoDataItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(toDoDataItem: ToDoDataItem)

    @Query("SELECT * FROM ToDoDataItem where listId = :listId AND finishedDate = '0' Order by sequence")
    fun getAllForListId(listId: String): List<ToDoDataItem>

    @Query("SELECT * FROM ToDoDataItem where finishedDate = '0'")
    fun getAll(): List<ToDoDataItem>

    @Query("SELECT * FROM ToDoDataItem where finishedDate <> '0'")
    fun getAllFinished(): List<ToDoDataItem>

    @Query("SELECT * FROM ToDoDataItem Order By sequence DESC")
    fun getLastSequence(): ToDoDataItem

    @Query("DELETE FROM ToDoDataItem where itemId = :itemId")
    fun deleteItem(itemId: String)

    @Query("UPDATE ToDoDataItem SET FinishedDate = :finishedDate where itemId = :itemId")
    fun setFinishedDate(itemId: String, finishedDate: String)

    @Query("SELECT * FROM ToDoDataItem where itemId = :itemId")
    fun getToDoItem(itemId: String): ToDoDataItem

    @Query(value = "SELECT count(*) FROM ToDoDataItem where listId = :listId")
    fun getListCount(listId: String): Int

}