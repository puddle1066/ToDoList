package com.paullanducci.todolist.di.dataStorage

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class DataStoreManagerTest {
    private val testPreferenceKey = stringPreferencesKey("TEST _STORE_VALUE")
    private val testPreferenceValue = "AA"

    @Test
    fun testDataStore() = runBlocking() {
        val store = DataStoreManager(ApplicationProvider.getApplicationContext())
        store.storeValue(testPreferenceKey, testPreferenceValue)
        store.readValue(testPreferenceKey) {
            assertEquals(this, testPreferenceValue)
        }
    }
}