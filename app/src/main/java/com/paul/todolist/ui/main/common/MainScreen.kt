package com.paul.todolist.ui.main.common

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.material.Scaffold
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paul.todolist.ToDoList
import com.paul.todolist.ui.main.ToDoModel
import com.paul.todolist.ui.theme.ToolboxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val todoModel: ToDoModel by viewModels()

        setContent {
            ToolboxTheme {
                Scaffold {
                    ToDoList.NavHostController = rememberAnimatedNavController()
                    NavigationFactory(todoModel)
                }
            }
        }
    }
}








