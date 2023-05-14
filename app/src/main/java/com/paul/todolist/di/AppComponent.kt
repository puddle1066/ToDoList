package com.paul.todolist.di

import com.paul.todolist.di.dataStorage.DataStoreProvider
import com.paul.todolist.di.database.RoomDataProvider
import com.paul.todolist.di.util.ResourcesProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RoomDataProvider::class,
        DataStoreProvider::class,
        ResourcesProvider::class
    ]
)
interface AppComponent