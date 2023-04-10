package com.paul.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Lists")
data class ListDataItem(
    @PrimaryKey val listId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "fixed") val fixed: String = "N",
    @ColumnInfo(name = "showAll") val showAll: String = "N"
)
