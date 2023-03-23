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
import com.paul.todolist.ui.main.todoItemView.ToDoItemView
import com.paul.todolist.ui.main.todoListView.ToDoListModel
import com.paul.todolist.ui.main.todoListView.ToDoListView
import com.paul.todolist.util.screen

@ExperimentalAnimationApi
@Composable
fun NavigationFactory(todoModel : ToDoListModel, listModel : ListItemsModel ) {
    MainScreen.navHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        MainScreen.navHostController,
        startDestination = ToDoScreens.ToDoListView.name

    ) {
        screen(ToDoScreens.ToDoListView.name) { ToDoListView(todoModel) }
        screen(ToDoScreens.ToDoItemView.name+"?listId={ListId}", arguments = listOf(navArgument("listId"){defaultValue=0})) { ToDoItemView() }
        screen(ToDoScreens.SettingsView.name) { SettingsView() }
        screen(ToDoScreens.listsView.name) { ListItemsView(listModel) }
    }

}

fun showView(screenId : String) {
    MainScreen.navHostController.popBackStack()
    MainScreen.navHostController.navigate(screenId)
}
fun showView(screenId : String , listId : Int?) {
    MainScreen.navHostController.navigate(screenId+"?listId=$listId")
}

fun getToDoListId() : String {
    return MainScreen.navHostController.currentBackStackEntry?.arguments?.getString("listId","") ?: ""
}

