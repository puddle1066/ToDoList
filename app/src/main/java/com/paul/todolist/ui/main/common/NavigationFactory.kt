package com.paul.todolist.ui.main.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.MainScreen
import com.paul.todolist.ui.main.listItemsView.ListItemsModel
import com.paul.todolist.ui.main.listItemsView.ListItemsView
import com.paul.todolist.ui.main.settingsView.SettingsView
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import com.paul.todolist.ui.main.todoItemView.ToDoItemView
import com.paul.todolist.ui.main.todoListView.ToDoListModel
import com.paul.todolist.ui.main.todoListView.ToDoListView
import com.paul.todolist.util.screen

@ExperimentalAnimationApi
@Composable
fun NavigationFactory(uiState: UiState, toDoListModel : ToDoListModel, listItemsModel : ListItemsModel, toDoItemModel : ToDoItemModel) {

    MainScreen.navHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        MainScreen.navHostController,
        startDestination = ToDoScreens.ToDoListView.name

    ) {
        screen(ToDoScreens.ToDoListView.name) { ToDoListView(uiState,toDoListModel) }
        screen(ToDoScreens.ToDoItemView.name) { ToDoItemView(uiState,toDoItemModel) }
        screen(ToDoScreens.SettingsView.name) { SettingsView(uiState) }
        screen(ToDoScreens.listsView.name) { ListItemsView(uiState, listItemsModel) }
    }
}

fun showView(screenId : String) {
    MainScreen.navHostController.popBackStack()
    MainScreen.navHostController.navigate(screenId)
}
