package com.paullanducci.todolist.di

import com.paullanducci.todolist.di.dataStorage.DataStoreProvider
import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.di.util.ResourcesProvider
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