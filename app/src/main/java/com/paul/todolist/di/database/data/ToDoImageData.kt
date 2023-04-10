package com.paul.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ImageData")
data class ToDoImageData(
    @PrimaryKey val itemId: String,
    @ColumnInfo(name = "data") var data: String = "",
)
