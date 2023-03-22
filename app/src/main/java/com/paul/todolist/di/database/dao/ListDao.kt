package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ListDataItem

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: ListDataItem)

    @Query("SELECT * FROM Lists")
    fun getAll(): List<ListDataItem>

    @Query("DELETE FROM lists where listId = :listId")
    fun deleteListItem(listId: String)
}