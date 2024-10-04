package com.paullanducci.todolist.ui.main.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.paullanducci.todolist.SHOW_INSTRUCTIONS
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.ui.main.MainActivity
import com.paullanducci.todolist.ui.main.imageView.ItemImageView
import com.paullanducci.todolist.ui.main.listItemsView.ListItemsModel
import com.paullanducci.todolist.ui.main.listItemsView.ListItemsView
import com.paullanducci.todolist.ui.main.privacy.LegalTextModel
import com.paullanducci.todolist.ui.main.privacy.LegalTextView
import com.paullanducci.todolist.ui.main.settingsView.SettingsModel
import com.paullanducci.todolist.ui.main.settingsView.SettingsView
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemView
import com.paullanducci.todolist.ui.main.todoListView.ToDoListModel
import com.paullanducci.todolist.ui.main.todoListView.ToDoListView
import com.paullanducci.todolist.ui.main.tutorial.TutorialCarousel
import com.paullanducci.todolist.util.screen

private var lastScreenLaunchedTimeMs: Long = 0
private val now: Long get() = System.currentTimeMillis()

const val debounceDelayTimeMs = 1000L

@ExperimentalAnimationApi
@Composable
fun NavigationFactory(
    toDoListModel: ToDoListModel,
    listItemsModel: ListItemsModel,
    toDoItemModel: ToDoItemModel,
    settingsModel: SettingsModel
) {
    var startScreen = ToDoScreens.TutorialCarousel.name
    if (!settingsModel.getOption(SHOW_INSTRUCTIONS)) {
        startScreen = ToDoScreens.ToDoListView.name
    }

    MainActivity.navHostController = rememberNavController()

    NavHost(
        MainActivity.navHostController!!,
        startDestination = startScreen
    ) {
        screen(ToDoScreens.ToDoListView.name) { ToDoListView(toDoListModel) }
        screen(ToDoScreens.ToDoItemView.name) { ToDoItemView(toDoItemModel) }
        screen(ToDoScreens.SettingsView.name) { SettingsView(settingsModel) }
        screen(ToDoScreens.listsView.name) { ListItemsView(listItemsModel) }
        screen(ToDoScreens.ImageItemView.name) { ItemImageView() }

        screen(ToDoScreens.TutorialCarousel.name) { TutorialCarousel(settingsModel) }

        screen(ToDoScreens.ShowPrivacyPolicy.name) { LegalTextView(LegalTextModel("privacy_policy.txt")) }
    }
}

fun showView(screenId: String) {
    //Debounce logic to stop multiple screen launches using double tap
    if (now - lastScreenLaunchedTimeMs >= debounceDelayTimeMs) {
        MainActivity.navHostController?.popBackStack()
        MainActivity.navHostController?.navigate(screenId)
        lastScreenLaunchedTimeMs = now
    }
}

fun showViewWithBackStack(screenId: String) {
    if (now - lastScreenLaunchedTimeMs >= debounceDelayTimeMs) {
        MainActivity.navHostController?.navigate(screenId)
        lastScreenLaunchedTimeMs = now
    }
}