package com.paul.todolist.ui.main.imageView

import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.ui.main.common.StorageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class ItemImageModel @Inject constructor(
    private val dataStoreProvider: DataStoreProvider

) : StorageViewModel(dataStoreProvider) {

}
