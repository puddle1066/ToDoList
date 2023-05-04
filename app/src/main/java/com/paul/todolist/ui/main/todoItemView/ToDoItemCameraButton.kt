package com.paul.todolist.ui.main.todoItemView

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ui.main.common.camera.CameraView
import com.paul.todolist.util.convertImageProxyToBitmap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Composable
fun ToDoItemCameraButton(onPictureTaken: (image: Bitmap) -> Unit) {
    val TAG = object {}::class.java.enclosingMethod.name

    val animationDuration = 100
    val scaleDown = 0.9f
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    val cameraExecutor = Executors.newSingleThreadExecutor()

    val takePictureButtonClicked = remember { mutableStateOf(false) }
    val finishedPicture = remember { mutableStateOf(false) }

    val showErrorMessage = remember { mutableStateOf("") }

    if (takePictureButtonClicked.value) {
        CameraView(
            executor = cameraExecutor,
            onImageCaptured = {
                onPictureTaken(convertImageProxyToBitmap(it))
                finishedPicture.value = true
                takePictureButtonClicked.value = false
            },
            onError = {
                Log.e(TAG, "Capture Error: {$it}")
                showErrorMessage.value = it.message.toString()
                finishedPicture.value = true
                takePictureButtonClicked.value = false
            },
            finishedPicture
        )
    }

    if (showErrorMessage.value.isNotBlank()) {
        Toast.makeText(LocalContext.current, showErrorMessage.value, Toast.LENGTH_LONG).show()
        takePictureButtonClicked.value = true //Disable camera button
    }

    Row(modifier = Modifier.padding(10.dp)) {
        if (!takePictureButtonClicked.value) {
            Button(
                modifier = Modifier
                    .scale(scale = scale.value)
                    .fillMaxWidth()
                    .height(90.dp),

                onClick = {
                    coroutineScope.launch {
                        scale.animateTo(
                            scaleDown,
                            animationSpec = tween(animationDuration),
                        )
                        scale.animateTo(
                            1f,
                            animationSpec = tween(animationDuration),
                        )
                        delay((animationDuration).toLong())    //Wait for anim to finish before launching

                        finishedPicture.value = false
                        takePictureButtonClicked.value = true

                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(25),

                ) {
                Icon(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp),
                    imageVector = Icons.Rounded.PhotoCamera,
                    contentDescription = stringResource(id = R.string.missing_resource)
                )
            }
        }
    }
}