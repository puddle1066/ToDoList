package com.paullanducci.todolist.ui.main.common.camera

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import java.util.concurrent.Executor

fun takePhoto(
    imageCapture: ImageCapture,
    executor: Executor,
    onImageCaptured: (image: ImageProxy) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    imageCapture.takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onError(exception: ImageCaptureException) {
            onError(exception)
        }

        override fun onCaptureSuccess(image: ImageProxy) {
            super.onCaptureSuccess(image)
            onImageCaptured(image)
        }
    })
}