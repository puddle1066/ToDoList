package com.paullanducci.todolist.di

import com.paullanducci.todolist.di.database.RoomDataProvider
import com.paullanducci.todolist.util.ResourcesProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RoomDataProvider::class,
        ResourcesProvider::class
    ]
)
interface AppComponent