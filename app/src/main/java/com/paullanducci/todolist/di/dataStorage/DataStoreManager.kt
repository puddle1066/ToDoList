package com.paullanducci.todolist.di.dataStorage

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.paullanducci.todolist.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    private var TAG = DataStoreManager::class.simpleName

    private suspend fun <T> DataStore<Preferences>.getFromLocalStorage(
        preferencesKey: Preferences.Key<T>, func: T.() -> Unit
    ) {
        data.catch {
            Log.e(TAG, "Exception Thrown  {$it}")
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[preferencesKey]
        }.collect {
            it?.let {
                func.invoke(it as T)
            }
        }
    }

    suspend fun <T> storeValue(key: Preferences.Key<T>, value: T) {
        Log.e(TAG, "storeValue  {$key} {$value}")
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun <T> readValue(key: Preferences.Key<T>, responseFunc: T.() -> Unit) {
        context.dataStore.getFromLocalStorage(key) {
            responseFunc.invoke(this)
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            PREFERENCES_NAME
        )
    }
}