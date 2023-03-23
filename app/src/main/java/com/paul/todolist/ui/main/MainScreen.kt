package com.paul.todolist.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.navigation.NavHostController
import com.paul.todolist.ui.main.common.NavigationFactory
import com.paul.todolist.ui.main.listItemsView.ListItemsModel
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import com.paul.todolist.ui.main.todoListView.ToDoListModel
import com.paul.todolist.ui.theme.ToolboxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toDoListModel: ToDoListModel by viewModels()
        val listItemsModel : ListItemsModel by viewModels()
        val toDoItemModel : ToDoItemModel by viewModels()

        setContent {
            ToolboxTheme {
                    NavigationFactory(toDoListModel,listItemsModel,toDoItemModel)
            }
        }
    }

    companion object {
        lateinit var navHostController: NavHostController
    }
}










