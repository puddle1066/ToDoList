package com.paul.todolist.di.database.worker

import androidx.room.TypeConverter
import java.util.Calendar


/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    //DateTime
    @TypeConverter
    fun toDateTime(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun fromDateTime(value: Long): Calendar =
        Calendar.getInstance().apply {
            timeInMillis = value
        }

}
