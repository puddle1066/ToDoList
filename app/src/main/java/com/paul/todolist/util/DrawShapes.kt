package com.paul.todolist.util

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun RoundedTriangle(color : Color, rotation : Float) {
    Canvas(modifier = Modifier.size(90.dp).rotate(rotation)) {
        val trianglePath = Path().apply {
            val height = size.height
            val width = size.width
            moveTo(width / 2.0f, 0f)
            lineTo(width, height)
            lineTo(0f, height)
        }
        drawPath(trianglePath, color = color)
    }
}
