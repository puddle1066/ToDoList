package com.paullanducci.todolist.ui.main.tutorial

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import com.paullanducci.todolist.ui.main.settingsView.SettingsModel

import com.paullanducci.todolist.ui.main.tutorial.pages.screen_1
import com.paullanducci.todolist.ui.main.tutorial.pages.screen_2
import com.paullanducci.todolist.ui.main.tutorial.pages.screen_3
import com.paullanducci.todolist.ui.main.tutorial.pages.screen_last

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun tutorialContainer(model: SettingsModel, pagerState: PagerState, count: Int) {

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(count)
    )

    HorizontalPager(
        state = pagerState,
        beyondBoundsPageCount = count,

        flingBehavior = fling
    ) { page ->
        when (page) {
            0 -> {
                screen_1()
            }

            1 -> {
                screen_2()
            }

            2 -> {
                screen_3()
            }

            3 -> {
                screen_last()
                model.setShowInstructions(true)
            }
        }
    }
}
