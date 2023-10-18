package com.paul.todolist.ui.main.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.MainActivity
import com.paul.todolist.ui.main.imageView.ItemImageView
import com.paul.todolist.ui.main.listItemsView.ListItemsModel
import com.paul.todolist.ui.main.listItemsView.ListItemsView
import com.paul.todolist.ui.main.settingsView.SettingsModel
import com.paul.todolist.ui.main.settingsView.SettingsView
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import com.paul.todolist.ui.main.todoItemView.ToDoItemView
import com.paul.todolist.ui.main.todoListView.ToDoListModel
import com.paul.todolist.ui.main.todoListView.ToDoListView
import com.paul.todolist.util.screen

@ExperimentalAnimationApi
@Composable
fun NavigationFactory(
    toDoListModel: ToDoListModel,
    listItemsModel: ListItemsModel,
    toDoItemModel: ToDoItemModel,
    settingsModel: SettingsModel
) {

    MainActivity.navHostController = rememberNavController()

    AnimatedNavHost(
        MainActivity.navHostController!!,
        startDestination = ToDoScreens.ToDoListView.name

    ) {
        screen(ToDoScreens.ToDoListView.name) { ToDoListView(toDoListModel) }
        screen(ToDoScreens.ToDoItemView.name) { ToDoItemView(toDoItemModel) }
        screen(ToDoScreens.SettingsView.name) { SettingsView(settingsModel) }
        screen(ToDoScreens.listsView.name) { ListItemsView(listItemsModel) }
        screen(ToDoScreens.ImageItemView.name) { ItemImageView() }
    }
}

fun showView(screenId: String) {
    MainActivity.navHostController?.popBackStack()
    MainActivity.navHostController?.navigate(screenId)
}

fun showViewWithBackStack(screenId: String) {
    MainActivity.navHostController?.navigate(screenId)
}