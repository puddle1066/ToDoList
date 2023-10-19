package com.paul.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Config")
data class Config(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "value") val value: String = ""
)

