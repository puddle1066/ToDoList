package com.paul.todolist.di.dataStorage

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class DataStoreProvider @Inject constructor(@ApplicationContext ctx: Context) {
    private var TAG = DataStoreManager::class.simpleName

    val context = ctx

    @Singleton
    private var datastore = DataStoreManager(context)

    suspend fun <T> readValue(key: Preferences.Key<T>, defaultValue: T, onCompleted: T.() -> Unit) {
        datastore.readValue(key) {
            if (this == null) {
                onCompleted.invoke(defaultValue)
            } else {
                onCompleted.invoke(this)
                Log.e(TAG, "storeValue  {$key} {$this}")
            }
        }
    }

    suspend fun <T> storeValue(key: Preferences.Key<T>, value: T) {
        datastore.storeValue(key, value)
    }
}
