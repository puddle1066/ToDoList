package com.paul.todolist.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.paul.todolist.ui.main.common.NavigationFactory
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParser
import com.paul.todolist.ui.main.listItemsView.ToDoItemsModel
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import com.paul.todolist.ui.main.todoListView.ToDoListModel
import com.paul.todolist.ui.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toDoListModel: ToDoListModel by viewModels()
        val toDoItemsModel : ToDoItemsModel by viewModels()
        val toDoItemModel : ToDoItemModel by viewModels()

        setContent {
            ToDoListTheme {
                    NavigationFactory(toDoListModel,toDoItemsModel,toDoItemModel)

                // Creates an permission request for Voive to Text
                val recordAudioLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        toDoItemModel.canDoSpeechToText = isGranted
                    }
                )

                LaunchedEffect(key1 = recordAudioLauncher) {
                    // Launches the permission request
                    recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
        }

        toDoItemModel.voiceToText = VoiceToTextParser(application)
    }

    companion object {
        lateinit var navHostController: NavHostController
    }
}










