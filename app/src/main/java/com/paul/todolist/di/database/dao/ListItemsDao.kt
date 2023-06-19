package com.paul.todolist.di.database.dao

import androidx.room.*
import com.paul.todolist.di.database.data.ListDataItem
import com.paul.todolist.listState_Normal

@Dao
interface ListItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: ListDataItem)

    @Query("SELECT * FROM ListsData Order BY type ASC")
    fun getAllSortedASC(): List<ListDataItem>

    @Query(value = "SELECT * FROM ListsData where type = '$listState_Normal'")
    fun getUserDefinedLists(): List<ListDataItem>

    @Query("DELETE FROM ListsData where listId = :listId")
    fun deleteList(listId: String)

    @Query(value = "SELECT title FROM ListsData where listId = :listId")
    fun getListTitle(listId: String): String

    @Query(value = "SELECT * FROM ListsData where listId = :listId")
    fun getListItem(listId: String): ListDataItem
}