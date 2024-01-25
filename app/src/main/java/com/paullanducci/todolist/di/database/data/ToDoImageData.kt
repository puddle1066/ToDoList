package com.paullanducci.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDoImageData")
data class ToDoImageData(
    @PrimaryKey val key: String,
    @ColumnInfo(name = "itemId") var itemId: String,
    @ColumnInfo(name = "image") var image: String,
)
