package com.paul.todolist.ui.main.todoItemView

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paul.todolist.ui.widgets.AppButton

@Composable
fun ToDoItemCameraButton() {
    var lensFacing = remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }

    Row(modifier = Modifier.padding(10.dp)) {
        AppButton(
            imageVector = Icons.Filled.Camera,
            onButtonPressed = {
//                CameraPreviewView(
//                    imageCapture,
//                    lensFacing.value
//                )
            }
        )
    }
}
