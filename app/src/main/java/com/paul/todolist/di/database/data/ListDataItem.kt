package com.paul.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paul.todolist.listState_Normal

@Entity(tableName = "Lists")
data class ListDataItem(
    @PrimaryKey val listId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: String = listState_Normal
)
