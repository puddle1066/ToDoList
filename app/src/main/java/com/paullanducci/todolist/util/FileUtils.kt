package com.paullanducci.todolist.util

import android.os.FileUtils
import android.util.Log
import com.paullanducci.todolist.ui.main.MainActivity.Companion.context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


private val TAG = FileUtils::class.java.simpleName

var DB_PATH = context?.applicationInfo?.dataDir + "/databases/"


fun copyFile(src: File?, dst: File?) {
    try {
        val var2 = FileInputStream(src)
        val var3 = FileOutputStream(dst)
        val var4 = ByteArray(1024)
        var var5: Int
        while (var2.read(var4).also { var5 = it } > 0) {
            var3.write(var4, 0, var5)
        }
        var2.close()
        var3.close()
    } catch (e: Exception) {
        Log.e(TAG, "Cannot copy file because  $e.message")
    }
}

fun deleteFile(inputPath: String, inputFile: String) {
    try {
        assert(File(inputPath + inputFile).delete())
    } catch (e: Exception) {
        Log.e(TAG, "Cannot Delete File because $e.message")
    }
}