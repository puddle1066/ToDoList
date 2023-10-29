package com.paullanducci.todolist.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import android.util.DisplayMetrics
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


@Composable
fun dpToPx(dp: Float): Float {
    val metrics: DisplayMetrics =
        LocalContext.current.applicationContext.getResources().getDisplayMetrics()
    return dp * (metrics.densityDpi / 160f)
}

fun convertImageProxyToBitmap(image: ImageProxy): Bitmap {
    val byteBuffer: ByteBuffer = image.planes[0].buffer
    byteBuffer.rewind()
    val bytes = ByteArray(byteBuffer.capacity())
    byteBuffer.get(bytes)
    val clonedBytes = bytes.clone()
    return BitmapFactory.decodeByteArray(clonedBytes, 0, clonedBytes.size)
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

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