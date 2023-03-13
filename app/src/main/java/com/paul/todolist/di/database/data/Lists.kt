package com.paul.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Lists")
data class Lists(
    @PrimaryKey val listId: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "fixed") val fixed: String  = "0",
    )
