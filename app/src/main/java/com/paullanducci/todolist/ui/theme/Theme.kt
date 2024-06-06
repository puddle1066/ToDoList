package com.paullanducci.todolist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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

    var barColor = MaterialTheme.colorScheme.primary

    MaterialTheme(
        colorScheme = selectedColorScheme,
        typography = typography,
        shapes = Shapes,
        content = content
    )

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = barColor,
            darkIcons = darkTheme
        )
    }

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = barColor,
            darkIcons = darkTheme
        )
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = barColor,
            darkIcons = darkTheme
        )
    }

}


