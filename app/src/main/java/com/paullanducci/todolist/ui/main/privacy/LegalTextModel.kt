package com.paullanducci.todolist.ui.main.privacy

import android.util.Log
import com.paullanducci.todolist.ui.main.MainActivity
import java.io.IOException

class LegalTextModel(legalTextFileName: String) {
    private var TAG = this::class.simpleName

    private var fileName = legalTextFileName

    var legalTextBody = ""

    init {
        try {
            legalTextBody = MainActivity.context?.assets?.open(fileName)
                ?.bufferedReader()
                .use { it?.readText() ?: "" }
        } catch (ioException: IOException) {
            Log.e(TAG, "IO Exception ${ioException.message} while loading $fileName ")
        }
    }
}