package com.paul.todolist.util

import android.util.DisplayMetrics
import com.paul.todolist.ToDoList

fun dpToPx(dp: Float): Float {
    val metrics: DisplayMetrics = ToDoList.appContext.getResources().getDisplayMetrics()
    return dp * (metrics.densityDpi / 160f)
}