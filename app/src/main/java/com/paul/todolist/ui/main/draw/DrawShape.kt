package com.paul.todolist.ui.main.draw

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.paul.todolist.util.dpToPx

fun drawShape(lines : Int) =  object : Shape {
    var drawHeight =   dpToPx(50f + (56f * lines))

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
          return Outline.Rectangle(Rect(0f,150f,800f, drawHeight))
    }
}

