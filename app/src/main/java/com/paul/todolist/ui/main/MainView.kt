package com.paul.todolist.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.lifecycle.ProcessCameraProvider
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
class MainView : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toDoListModel: ToDoListModel by viewModels()
        val toDoItemsModel: ToDoItemsModel by viewModels()
        val toDoItemModel: ToDoItemModel by viewModels()

        setContent {
            ToDoListTheme {
                NavigationFactory(toDoListModel, toDoItemsModel, toDoItemModel)

                //Permissions for TEXT TO SPEECH PROCESSING
                val recordAudioLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        toDoItemModel.isSpeechToTextEnabled = isGranted
                    }
                )


                //Check we have Camera and Storage Access
                val cameraLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        toDoItemModel.isPhotoCaptureEnabled = isGranted
                    }
                )

                LaunchedEffect(key1 = recordAudioLauncher) {
                    recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }

                LaunchedEffect(key1 = cameraLauncher) {
                    cameraLauncher.launch(Manifest.permission.CAMERA)
                }

            }
        }

        toDoItemModel.voiceToText = VoiceToTextParser(application)
    }

    companion object {
        lateinit var navHostController: NavHostController
        lateinit var cameraProvider: ProcessCameraProvider
    }
}










