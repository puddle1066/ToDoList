package com.paul.todolist.ui.main.todoItemView

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.paul.todolist.ui.main.common.camera.CameraPreviewView
import com.paul.todolist.ui.widgets.AppButton
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ToDoItemCameraButton() {
//    var lensFacing = remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
//    val imageCapture: ImageCapture = remember {ImageCapture.Builder().build()}

    val photoUri: Uri
    val bitmap = remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            bitmap.value = it as Nothing?
        }

//    val photoFile = File(
//        outputDirectory,
//        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
//    )

  ///  val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

//    imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
//        override fun onError(exception: ImageCaptureException) {
//            Log.e("kilo", "Take photo error:", exception)
//            onError(exception)
//        }
//
//        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//            val savedUri = Uri.fromFile(photoFile)
//            onImageCaptured(savedUri)
//        }
//    })
}
