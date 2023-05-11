package com.paul.todolist.ui.main.common


import androidx.lifecycle.viewModelScope
import com.paul.todolist.LIST_ID_KEY
import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.ui.main.MainView
import kotlinx.coroutines.launch

open class StorageViewModel(private val dataStoreProvider: DataStoreProvider) : BaseViewModel() {
    fun saveListId(listId: String) {
        MainView.listId = listId
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