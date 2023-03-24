package com.paul.todolist

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ToDoList : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    //Create global reference for app context
    companion object {
        lateinit var appContext: Context                            //App Context for reference
    }
}

