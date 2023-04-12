package com.paul.todolist.di.database.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDoImageData")
data class ToDoImageData(
    @PrimaryKey val itemId: String,
    @ColumnInfo(name = "image") var image: Bitmap,
)
