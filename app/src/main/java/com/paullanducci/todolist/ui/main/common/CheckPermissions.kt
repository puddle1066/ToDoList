package com.paullanducci.todolist.ui.main.common

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel

@Composable
fun CheckPermissions(hasPermission: MutableState<Boolean>, model: ToDoItemModel) {
    var count = 0

    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.INTERNET
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission: Map<String, @JvmSuppressWildcards Boolean> ->
        for (p in permission) {
            if (p.value) {
                when (p.key) {
                    Manifest.permission.CAMERA -> model.isPhotoCaptureEnabled = true
                    Manifest.permission.RECORD_AUDIO -> model.isSpeechToTextEnabled = true
                    Manifest.permission.INTERNET -> count++
                }
                if (count == permissions.size - 2) {
                    hasPermission.value = true
                }
            } else {
                Log.e("checkPermissions", "missing permission ${p.key}")
            }
        }
    }

    SideEffect {
        launcher.launch(permissions)
    }
}
