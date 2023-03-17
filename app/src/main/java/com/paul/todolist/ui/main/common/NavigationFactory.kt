package com.paul.todolist.ui.main.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.paul.todolist.ToDoList
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.*
import com.paul.todolist.util.screen


@ExperimentalAnimationApi
@Composable
fun NavigationFactory(model : ToDoModel) {
    AnimatedNavHost( ToDoList.NavHostController,
            startDestination =  ToDoScreens.MainView.name
    ) {

        screen(ToDoScreens.MainView.name) { MainView(model) }

        screen(ToDoScreens.SettingsView.name) { SettingsView() }

    }
}

