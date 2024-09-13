package com.paullanducci.todolist.util

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.paullanducci.todolist.R
import org.junit.Test

class ResourceProviderTest {

    private var context: Context = getInstrumentation().targetContext.applicationContext
    private val resourcesProvider = ResourcesProvider(context)

    @Test
    fun testResourceProvider() {
        assert(resourcesProvider.getString(R.string.vosk_model_unsupported_language).isNotEmpty())
    }
}