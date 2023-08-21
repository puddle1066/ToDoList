package com.paul.todolist.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.R
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class DrawingUtilitysKtTest {
    var bm: Bitmap? = null

    @Before
    fun createTestBitmap() {
        bm = BitmapFactory.decodeResource(
            Resources.getSystem(),
            R.drawable.abc_ab_share_pack_mtrl_alpha
        )
    }

    @After
    fun cleanup() {
        bm = null
    }

    @Test
    fun convertImageProxyToBitmap() {
    }

    @Test
    fun rotate() {
        //  val image = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false).rotate(90f)..
    }

    @Test
    fun imageEncoding() {
        assertTrue(decodeBase64(bm?.let { encodeTobase64(it) }) == bm)
    }

}