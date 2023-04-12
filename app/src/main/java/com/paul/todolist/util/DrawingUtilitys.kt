package com.paul.todolist.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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