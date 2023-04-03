package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ToDoDataItem

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoDataItem: ToDoDataItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(toDoDataItem: ToDoDataItem)

    @Query("SELECT * FROM ToDoItem where listId = :listId AND FinishedDate = '0'")
    fun getAllForListId(listId : String): List<ToDoDataItem>

    @Query("SELECT * FROM ToDoItem where FinishedDate = '0'" )
    fun getAll() : List<ToDoDataItem>

    @Query("SELECT * FROM ToDoItem where FinishedDate <> '0'" )
    fun getAllFinished() : List<ToDoDataItem>


    @Query("DELETE FROM ToDoItem where itemId = :itemId")
    fun deleteItem(itemId: String)

    @Query("UPDATE ToDoItem SET FinishedDate = :finishedDate where itemId = :itemId" )
    fun setFinishedDate(itemId : String, finishedDate : String )

    @Query("SELECT * FROM ToDoItem where itemId = :itemId" )
    fun getToDoItem(itemId : String) : ToDoDataItem

}