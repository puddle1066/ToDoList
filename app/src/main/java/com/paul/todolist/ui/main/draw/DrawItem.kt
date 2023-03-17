package com.paul.todolist.ui.main.draw

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawItem(
    var icon: ImageVector,
    var title: Int = 0,
    var link: String = "",
    )
