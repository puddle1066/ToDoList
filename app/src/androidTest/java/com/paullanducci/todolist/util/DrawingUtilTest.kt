package com.paullanducci.todolist.util

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.appcompat.R
import androidx.core.graphics.drawable.toBitmap
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert.assertTrue
import org.junit.Test


class DrawingUtilTest {
    private val res: Resources = getInstrumentation().targetContext.resources
    private var draw: Drawable = res.getDrawable(R.drawable.abc_btn_check_material)
    private var bm = draw.toBitmap(100, 200, null)

    @Test
    fun imageRotation() {
        val im = bm.rotate(180f)
        assertTrue(im.width == 100)
        assertTrue(im.height == 200)
    }

    @Test
    fun imageEncoding() {
        val im = decodeBase64(encodeTobase64(bm))
        assertTrue(im?.width == bm.width)
        assertTrue(im?.height == bm.height)
    }
}