package com.paul.todolist.ui.widgets


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getListOfColors(): ArrayList<Color> {
    val list = ArrayList<Color>()

    var red = 0
    var green = 0
    var blue = 0

    val colorOffset = 50  //Spacing between color values

    while (red < 255) {
        while (green < 255) {
            while (blue < 255) {
                blue += colorOffset
                if (Color(red, green, blue) != Color.White) {
                    list.add(Color(red, green, blue))
                }
            }
            blue = 0
            green += colorOffset
        }
        green = 0
        red += colorOffset
    }

    return list
}
