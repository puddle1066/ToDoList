package com.paullanducci.todolist.di.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paullanducci.todolist.listState_Normal

@Entity(tableName = "ListsData")
data class ListDataItem(
    @PrimaryKey var listId: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "type") var type: String = listState_Normal
)

fun ListDataItemInitail(): ListDataItem {
    return ListDataItem("", "", "")
}
