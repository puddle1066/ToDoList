package com.paullanducci.todolist.util

import androidx.core.os.LocaleListCompat
import com.paullanducci.speech.input.VoskInputDevice.Companion.MODEL_URLS
import org.junit.Assert
import org.junit.Test
import java.util.Locale

class LocaleUtilTest {

    @Test
    fun testRetrieveLocaleList() {
        try {
            val result = LocaleUtils.resolveSupportedLocale(
                LocaleListCompat.create(Locale.getDefault()),
                MODEL_URLS.keys
            )

            assert(result.supportedLocaleString.isNotEmpty())
        } catch (e: LocaleUtils.UnsupportedLocaleException) {
            Assert.fail("Unsupported Locale exception")
        }
    }
}