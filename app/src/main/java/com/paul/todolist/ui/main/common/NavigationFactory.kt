package com.paul.todolist.ui.main.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.paul.todolist.ToDoList
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.listItemsView.ListItemsModel
import com.paul.todolist.ui.main.listItemsView.ListItemsView
import com.paul.todolist.ui.main.settingsView.SettingsView
import com.paul.todolist.ui.main.todoItemView.ToDoItemView
import com.paul.todolist.ui.main.todoListView.ToDoListModel
import com.paul.todolist.ui.main.todoListView.ToDoListView
import com.paul.todolist.util.screen

@ExperimentalAnimationApi
@Composable
fun NavigationFactory(todoModel : ToDoListModel, listModel : ListItemsModel ) {
    AnimatedNavHost(
        ToDoList.NavHostController,
        startDestination = ToDoScreens.ToDoListView.name

    ) {
        screen(ToDoScreens.ToDoListView.name) { ToDoListView(todoModel) }
        screen(ToDoScreens.ToDoItemView.name) { ToDoItemView() }
        screen(ToDoScreens.SettingsView.name) { SettingsView() }
        screen(ToDoScreens.listsView.name) { ListItemsView(listModel) }
    }
}

