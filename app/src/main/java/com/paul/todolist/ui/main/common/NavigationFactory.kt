package com.paul.todolist.ui.main.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.navArgument
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
fun NavigationFactory(toDoListModel : ToDoListModel, listItemsModel : ListItemsModel, toDoItemModel : ToDoItemModel) {
    MainScreen.navHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        MainScreen.navHostController,
        startDestination = ToDoScreens.ToDoListView.name

    ) {
        screen(ToDoScreens.ToDoListView.name) { ToDoListView(toDoListModel) }
        screen(ToDoScreens.ToDoItemView.name+"?listId={ListId}", arguments = listOf(navArgument("listId"){defaultValue=""})) { ToDoItemView(toDoItemModel) }
        screen(ToDoScreens.SettingsView.name) { SettingsView() }
        screen(ToDoScreens.listsView.name) { ListItemsView(listItemsModel) }
    }

}

fun showView(screenId : String) {
    MainScreen.navHostController.popBackStack()
    MainScreen.navHostController.navigate(screenId)
}
fun showView(screenId : String , listId : String?) {
    MainScreen.navHostController.navigate(screenId+"?listId=$listId")
}

fun getListId() : String {
    return MainScreen.navHostController.currentBackStackEntry?.arguments?.getString("listId","") ?: ""
}

