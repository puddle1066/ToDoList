package com.paul.todolist.di

import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.network.ApiProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RoomDataProvider::class,
        ApiProvider::class,
        DataStoreProvider::class
    ]
)
interface AppComponent