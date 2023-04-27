package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.listState_Normal

@Dao
interface ListItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: ListDataItem)

    @Query("SELECT * FROM Lists Order BY type ASC")
    fun getAllSortedASC(): List<ListDataItem>

    @Query(value = "SELECT * FROM Lists where type = '" + listState_Normal + "'")
    //Only return user defined lists
    fun getUser(): List<ListDataItem>

    @Query("DELETE FROM lists where listId = :listId")
    fun deleteList(listId: String)

    @Query(value = "SELECT title FROM Lists where listId = :listId")
    fun getListTitle(listId: String): String

    @Query(value = "SELECT * FROM Lists where listId = :listId")
    fun getListItem(listId: String): ListDataItem

    @Query(value = "SELECT count(*) FROM ToDoItem where listId = :listId")
    fun getListCount(listId: String): Int

}