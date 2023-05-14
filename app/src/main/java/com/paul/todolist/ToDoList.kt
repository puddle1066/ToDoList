package com.paul.todolist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ToDoList : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}

