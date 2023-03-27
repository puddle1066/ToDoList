package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ListDataItem

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: ListDataItem)

    @Query("SELECT * FROM Lists Order BY fixed ASC")
    fun getAllSortedASC(): List<ListDataItem>

    @Query(value = "SELECT * FROM Lists where fixed = 0")
    //Only return user defined lists
    fun getUser(): List<ListDataItem>

    @Query("DELETE FROM lists where listId = :listId")
    fun deleteListItem(listId: String)

    @Query(value = "SELECT title FROM Lists where listId = :listId")
    fun getListTitle(listId: String): String
}