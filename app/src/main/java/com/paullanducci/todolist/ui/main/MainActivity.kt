package com.paullanducci.todolist.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.paullanducci.speech.input.SpeechInputDevice
import com.paullanducci.speech.input.VoskInputDevice
import com.paullanducci.speech.output.speech.AndroidTtsSpeechDevice
import com.paullanducci.speech.output.speech.SpeechOutputDevice
import com.paullanducci.todolist.LAST_LIST_ID
import com.paullanducci.todolist.ui.main.common.NavigationFactory
import com.paullanducci.todolist.ui.main.listItemsView.ListItemsModel
import com.paullanducci.todolist.ui.main.settingsView.SettingsModel
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import com.paullanducci.todolist.ui.main.todoListView.ToDoListModel
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        actionBar?.hide()

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val toDoListModel: ToDoListModel by viewModels()
        val listItemsModel: ListItemsModel by viewModels()
        val toDoItemModel: ToDoItemModel by viewModels()
        val settingsModel: SettingsModel by viewModels()

        context = this.applicationContext

        if (listId.isBlank()) {
           listId =  toDoListModel.getOptionString(LAST_LIST_ID)
        }

        setContent {

            ToDoListTheme {

                NavigationFactory(toDoListModel, listItemsModel, toDoItemModel, settingsModel)

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

        toDoItemModel.speechInputDevice = buildSpeechInputDevice()
        toDoItemModel.speechOutputDevice = buildSpeechOutputDevice()
    }

    private fun buildSpeechInputDevice(): SpeechInputDevice {
        return VoskInputDevice(this, Locale.getDefault())
    }

    private fun buildSpeechOutputDevice(): SpeechOutputDevice {
        return AndroidTtsSpeechDevice(this, Locale.getDefault())
    }

    override fun onDestroy() {
        super.onDestroy()

        //Clear any objects created bby the composite views
        context = null
        navHostController = null
        cameraProvider = null
        image = null
        listId = ""
        itemId = ""

    }

    companion object {
        var navHostController: NavHostController? = null
        var cameraProvider: ProcessCameraProvider? = null
        var context: Context? = null
        var image: Bitmap? = null

        val dispatcher: CoroutineDispatcher = Dispatchers.Default

        var listId: String = ""
        var itemId: String = ""

    }
}










