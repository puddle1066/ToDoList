package com.paullanducci.todolist.ui.main.tutorial

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import com.paullanducci.todolist.SHOW_INSTRUCTIONS
import com.paullanducci.todolist.ui.main.settingsView.SettingsModel
import com.paullanducci.todolist.ui.main.tutorial.pages.Screen_1
import com.paullanducci.todolist.ui.main.tutorial.pages.Screen_2
import com.paullanducci.todolist.ui.main.tutorial.pages.Screen_3
import com.paullanducci.todolist.ui.main.tutorial.pages.Screen_4
import com.paullanducci.todolist.ui.main.tutorial.pages.Screen_5


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialContainer(model: SettingsModel, pagerState: PagerState, count: Int) {

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
            0 -> Screen_1()
            1 -> Screen_2()
            2 -> Screen_3()
            3 -> Screen_4()
            4 -> {
                Screen_5()
                model.setOption(SHOW_INSTRUCTIONS, true)
            }
        }
    }
}
