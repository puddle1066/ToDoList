package com.paullanducci.todolist.util

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class FileUtilTest {
    private var context: Context = getInstrumentation().targetContext.applicationContext
    private var DB_PATH = context.applicationInfo?.dataDir + "/databases/"

    private var srcFile1 = File(DB_PATH + "test")
    private var destFile1 = File(DB_PATH + "test1")

    @Test
    fun testFileRename() {
        assertTrue(srcFile1.createNewFile())
        copyFile(srcFile1, destFile1)
        assertTrue(destFile1.exists())
    }

    @Test
    fun testFileDelete() {
        deleteFile(DB_PATH, "test1")
        assertTrue(!destFile1.exists())
    }
}