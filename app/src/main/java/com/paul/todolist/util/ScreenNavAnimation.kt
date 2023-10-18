package com.paul.todolist.util

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.screen(
    route: String,
    arguments: List<NamedNavArgument> = listOf(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    val animSpec: FiniteAnimationSpec<IntOffset> = tween(500, easing = FastOutSlowInEasing)

    composable(
        route,
        arguments = arguments,
        enterTransition = {
            slideInHorizontally(
                animationSpec = animSpec
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                animationSpec = animSpec
            )
        },
        exitTransition = {
            slideOutHorizontally(

                animationSpec = animSpec
            )
        },
        popExitTransition = {
            slideOutHorizontally(

                animationSpec = animSpec
            )
        },
        content = content
    )
}


