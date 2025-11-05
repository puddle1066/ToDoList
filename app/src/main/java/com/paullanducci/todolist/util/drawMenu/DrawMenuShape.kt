package com.paullanducci.todolist.util.drawMenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.paullanducci.todolist.util.dpToPx

@Composable
fun drawMenuShape(lines: Int) = object : Shape {
    var drawHeight = dpToPx(50f + (62f * lines))

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f, 150f, 800f, drawHeight))
    }
}

