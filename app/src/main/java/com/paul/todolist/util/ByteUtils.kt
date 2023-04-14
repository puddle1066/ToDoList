package com.paul.todolist.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


fun encodeTobase64(image: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.PNG, 90, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun decodeBase64(input: String?): Bitmap? {
    val decodedByte: ByteArray = Base64.decode(input, 0)
    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
}