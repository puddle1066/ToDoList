package com.paul.todolist.ui.main.common

import androidx.lifecycle.viewModelScope
import com.paul.todolist.IMAGE_KEY
import com.paul.todolist.ITEM_ID_KEY
import com.paul.todolist.LIST_ID_KEY
import com.paul.todolist.base.BaseViewModel
import com.paul.todolist.di.dataStorage.DataStoreProvider
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

    fun setItemId(itemId: String) {
        viewModelScope.launch {
            dataStoreProvider.storeValue(ITEM_ID_KEY, itemId)
        }
    }

    fun getItemId(value: (value: String) -> Unit) {
        viewModelScope.launch {
            dataStoreProvider.readValue(ITEM_ID_KEY, "") {
                value.invoke(this)
            }
        }
    }

    fun setImage(image: String) {
        viewModelScope.launch {
            dataStoreProvider.storeValue(IMAGE_KEY, image)
        }
    }

    fun getImage(key: (value: String) -> Unit) {
        viewModelScope.launch {
            dataStoreProvider.readValue(IMAGE_KEY, "") {
                key.invoke(this)
            }
        }
    }

}