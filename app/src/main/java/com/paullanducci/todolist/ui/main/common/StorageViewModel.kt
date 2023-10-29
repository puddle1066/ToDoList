package com.paullanducci.todolist.ui.main.common


import androidx.lifecycle.viewModelScope
import com.paullanducci.todolist.LIST_ID_KEY
import com.paullanducci.todolist.base.BaseViewModel
import com.paullanducci.todolist.di.dataStorage.DataStoreProvider
import kotlinx.coroutines.launch

open class StorageViewModel(private val dataStoreProvider: DataStoreProvider) : BaseViewModel() {
    fun saveListId(listId: String) {
        viewModelScope.launch {
            dataStoreProvider.storeValue(LIST_ID_KEY, listId)
        }
    }

    fun getListId(value: (value: String) -> Unit) {
        viewModelScope.launch {
            dataStoreProvider.readValue(LIST_ID_KEY, "") {
                value.invoke(this)
            }
        }
    }

}