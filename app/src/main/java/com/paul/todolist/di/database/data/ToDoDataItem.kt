package com.paul.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDoItem")
data class ToDoDataItem(
    @PrimaryKey val itemId: String,
    @ColumnInfo(name = "ListId") var listID: String,
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "DueDate") var dueDate: String = "0",
    @ColumnInfo(name = "FinishedDate") var finishedDate: String ="0"
    )
