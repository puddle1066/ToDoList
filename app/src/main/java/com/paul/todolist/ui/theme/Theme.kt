package com.paul.todolist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun ToDoListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val selectedColorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = selectedColorScheme,
        typography = typography,
        shapes = Shapes,
        content = content
    )

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.LightGray,
            darkIcons = true
        )
    }

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = Color.LightGray,
            darkIcons = true
        )
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.LightGray,
            darkIcons = true
        )
    }

}


