package com.paullanducci.todolist.ui.main.tutorial

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import com.paullanducci.todolist.SHOW_INSTRUCTIONS
import com.paullanducci.todolist.ui.main.settingsView.SettingsModel
import com.paullanducci.todolist.ui.main.tutorial.pages.TutorialPageFive
import com.paullanducci.todolist.ui.main.tutorial.pages.TutorialPageFour
import com.paullanducci.todolist.ui.main.tutorial.pages.TutorialPageOne
import com.paullanducci.todolist.ui.main.tutorial.pages.TutorialPageThree
import com.paullanducci.todolist.ui.main.tutorial.pages.TutorialPageTwo


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
            0 -> TutorialPageOne(model)
            1 -> TutorialPageTwo()
            2 -> TutorialPageThree()
            3 -> TutorialPageFour()
            4 -> {
                TutorialPageFive()
                model.setOption(SHOW_INSTRUCTIONS, false)
            }
        }
    }
}
