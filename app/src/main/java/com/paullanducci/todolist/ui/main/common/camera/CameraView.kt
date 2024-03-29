package com.paullanducci.todolist.ui.main.common.camera

import android.annotation.SuppressLint
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paullanducci.todolist.R
import com.paullanducci.todolist.imageHeight
import com.paullanducci.todolist.imageWidth
import com.paullanducci.todolist.ui.main.MainActivity.Companion.cameraProvider
import java.util.concurrent.Executor

@SuppressLint("RestrictedApi")
@Composable
fun CameraView(
    executor: Executor,
    onImageCaptured: (image: ImageProxy) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    finished: MutableState<Boolean>
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember {
        ImageCapture
            .Builder()
            .setDefaultResolution(Size(imageWidth, imageHeight))
            .build()
    }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val colorSelected = MaterialTheme.colorScheme.onPrimary
    val colorUnSelected = MaterialTheme.colorScheme.primary
    val buttonColor = remember { mutableStateOf(colorUnSelected) }

    val enableButton = remember { mutableStateOf(true) }

    LaunchedEffect(lensFacing) {
        cameraProvider = context.getCameraProvider()
        cameraProvider!!.unbindAll()
        cameraProvider!!.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    if (!finished.value) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        )
        {
            Column(
                modifier = Modifier
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(5.dp, 55.dp, 5.dp, 55.dp)
            ) {
                AndroidView(
                    { previewView }
                )
            }

            IconButton(
                modifier = Modifier.padding(bottom = 5.dp),
                onClick = {
                    buttonColor.value = colorSelected
                    enableButton.value = false
                    takePhoto(
                        imageCapture = imageCapture,
                        executor = executor,
                        onImageCaptured = onImageCaptured,
                        onError = onError
                    )
                },
                enabled = enableButton.value,
                content = {
                    Icon(
                        imageVector = Icons.Sharp.Lens,
                        contentDescription = stringResource(id = R.string.missing_resource),
                        tint = buttonColor.value,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(1.dp)
                            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    )
                }
            )
        }
    }
}