package com.paul.todolist.ui.main.todoItemView

import androidx.compose.ui.graphics.Color

data class ListItemAlertsData(
    val daysOverdueThreshold: Int,
    val colorOverdue: Color,
    val dayLateThreshold: Int,
    val colorLate: Color
)