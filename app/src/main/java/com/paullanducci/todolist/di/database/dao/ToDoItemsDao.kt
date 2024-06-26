package com.paullanducci.todolist.di.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.paullanducci.todolist.di.database.data.ToDoDataItem

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

    @Query("SELECT * FROM ToDoDataItem Order By sequence ASC")
    fun getFirstSequence(): ToDoDataItem

    @Query("DELETE FROM ToDoDataItem where itemId = :itemId")
    fun deleteItem(itemId: String)

    @Query("UPDATE ToDoDataItem SET FinishedDate = :finishedDate where itemId = :itemId")
    fun setFinishedDate(itemId: String, finishedDate: String)

    @Query("SELECT * FROM ToDoDataItem where itemId = :itemId")
    fun getToDoItem(itemId: String): ToDoDataItem

    @Query(value = "SELECT count(*) FROM ToDoDataItem where listId = :listId")
    fun getListCount(listId: String): Int

    @Query("SELECT * FROM ToDoDataItem where finishedDate <> '0' and description like :text")
    fun searchAllFinished(text: String): List<ToDoDataItem>

    @Query("SELECT * FROM ToDoDataItem where finishedDate = '0' and description like :text")
    fun searchToDoItem(text: String): List<ToDoDataItem>

}