package com.paul.todolist.util

import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.paul.todolist.ToDoList

@Composable
fun dpToPx(dp: Float): Float {
    val metrics: DisplayMetrics = LocalContext.current.applicationContext.getResources().getDisplayMetrics()
    return dp * (metrics.densityDpi / 160f)
}