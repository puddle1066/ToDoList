
package com.paul.todolist.di.database.worker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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

   //Bitmap converters
    @TypeConverter
    fun fromBitmap(value: Bitmap): String {
        val baos = ByteArrayOutputStream()
        value.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    @TypeConverter
    fun toBitmap(value: String): Bitmap {
        val imageBytes = Base64.decode(value, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

}
