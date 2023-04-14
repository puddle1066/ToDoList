package com.paul.todolist.di.database.worker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*


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
