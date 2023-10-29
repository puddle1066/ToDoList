package com.paullanducci.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDoDataItem")
data class ToDoDataItem(
    @PrimaryKey var itemId: String,
    @ColumnInfo(name = "listId") var listID: String,
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "dueDate") var dueDate: String = "",
    @ColumnInfo(name = "finishedDate") var finishedDate: String = "",
    @ColumnInfo(name = "lastupdated") var lastupdated: String = "",
    @ColumnInfo(name = "sequence") var sequence: Int = 0
)

