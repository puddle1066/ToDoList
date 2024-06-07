package com.paullanducci.todolist.ui.main.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.imageView.ItemImageView
import com.paullanducci.todolist.ui.main.listItemsView.ListItemsModel
import com.paullanducci.todolist.ui.main.listItemsView.ListItemsView
import com.paullanducci.todolist.ui.main.settingsView.SettingsModel
import com.paullanducci.todolist.ui.main.settingsView.SettingsView
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemView
import com.paullanducci.todolist.ui.main.todoListView.ToDoListModel
import com.paullanducci.todolist.ui.main.todoListView.ToDoListView
import com.paullanducci.todolist.util.screen
import tutorialCarousel

@ExperimentalAnimationApi
@Composable
fun NavigationFactory(
    toDoListModel: ToDoListModel,
    listItemsModel: ListItemsModel,
    toDoItemModel: ToDoItemModel,
    settingsModel: SettingsModel
) {
    val startscreen = ToDoScreens.TutorialCarousel.name
    //TODO Add back
    //  if (settingsModel.getOption(SHOW_INSTRUCTIONS)) {
//        startscreen = ToDoScreens.ToDoListView.name
//    }

    MainActivity.navHostController = rememberNavController()

    NavHost(
        MainActivity.navHostController!!,
        startDestination = startscreen
    ) {
        screen(ToDoScreens.ToDoListView.name) { ToDoListView(toDoListModel) }
        screen(ToDoScreens.ToDoItemView.name) { ToDoItemView(toDoItemModel) }
        screen(ToDoScreens.SettingsView.name) { SettingsView(settingsModel) }
        screen(ToDoScreens.listsView.name) { ListItemsView(listItemsModel) }
        screen(ToDoScreens.ImageItemView.name) { ItemImageView() }
        screen(ToDoScreens.TutorialCarousel.name) { tutorialCarousel(settingsModel) }
    }
}

fun showView(screenId: String) {
    MainActivity.navHostController?.popBackStack()
    MainActivity.navHostController?.navigate(screenId)
}

fun showViewWithBackStack(screenId: String) {
    MainActivity.navHostController?.navigate(screenId)
}