package com.paul.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ToDoItem")
data class ToDoItem(
    @PrimaryKey val itemId: Int,
    @ColumnInfo(name = "ListId") val listID: Int?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "DueDate") val dueDate: Calendar?,
    @ColumnInfo(name = "FinishedDate") val finishedDate: Calendar?,
    )
