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

    @Query("SELECT * FROM ListsData Order BY type ASC, title")
    fun getAllSortedASC(): List<ListDataItem>

    @Query(value = "SELECT * FROM ListsData where type = '$listState_Normal' ORDER BY title ASC")
    fun getUserDefinedLists(): List<ListDataItem>

    @Query("DELETE FROM ListsData where listId = :listId")
    fun deleteList(listId: String)

    @Query(value = "SELECT title FROM ListsData where listId = :listId")
    fun getListTitle(listId: String): String

    @Query(value = "SELECT type FROM ListsData where listId = :listId")
    fun getListType(listId: String): String

    @Query(value = "SELECT * FROM ListsData where listId = :listId")
    fun getListItem(listId: String): ListDataItem

    @Query(value = "DELETE From ToDoDataItem where finishedDate != 0")
    fun removeAllFinished()

    @Query(value = "SELECT Count() FROM ListsData where listId = :listId")
    fun getListItemCount(listId: String): Int

    @Query("UPDATE ListsData SET title = :listTitle where listId = :listId")
    fun updateList(listId: String, listTitle: String)
}