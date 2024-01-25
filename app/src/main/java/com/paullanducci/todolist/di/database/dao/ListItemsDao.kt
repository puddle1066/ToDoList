package com.paullanducci.todolist.di.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paullanducci.todolist.di.database.data.ListDataItem
import com.paullanducci.todolist.listState_Normal

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

    @Query(value = "SELECT Count() FROM ListsData where listId = :listId")
    fun getListItemCount(listId: String): Int
}