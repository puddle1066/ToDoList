package com.paul.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDoItem")
data class ToDoDataItem(
    @PrimaryKey val itemId: String,
    @ColumnInfo(name = "ListId") val listID: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "DueDate") val dueDate: String = "0",
    @ColumnInfo(name = "FinishedDate") val finishedDate: String ="0",
    )
