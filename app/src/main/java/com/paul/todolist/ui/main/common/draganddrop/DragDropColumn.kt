package com.paul.todolist.ui.main.common.draganddrop

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> DragDropColumn(
    items: List<T>,
    onSwap: (Int, Int) -> Unit,
    backgroundColor: MutableState<Color>,
    moveAllowed: MutableState<Boolean>,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit
) {
    var overscrollJob by remember { mutableStateOf<Job?>(null) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val dragDropState = rememberDragDropState(listState) { fromIndex, toIndex ->
        onSwap(fromIndex, toIndex)
    }

    val colorUnSelected = MaterialTheme.colorScheme.primary
    val colorMoveSelected = MaterialTheme.colorScheme.onSurface


    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .pointerInput(dragDropState) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consume()
                        if (moveAllowed.value) {
                            dragDropState.onDrag(offset = offset)
                        }

                        if (overscrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        dragDropState
                            .checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let {
                                overscrollJob =
                                    scope.launch {
                                        dragDropState.state.animateScrollBy(
                                            it * 1.3f, tween(easing = FastOutLinearInEasing)
                                        )
                                    }
                            }
                            ?: run { overscrollJob?.cancel() }
                    },
                    onDragStart = {
                        if (moveAllowed.value) {
                            dragDropState.onDragStart(it)
                            backgroundColor.value = colorMoveSelected
                        }
                    },
                    onDragEnd = {
                        if (moveAllowed.value) {
                            dragDropState.onDragInterrupted()
                            backgroundColor.value = colorUnSelected
                            overscrollJob?.cancel()
                        }
                    },
                    onDragCancel = {
                        if (moveAllowed.value) {
                            dragDropState.onDragInterrupted()
                            backgroundColor.value = colorUnSelected
                            overscrollJob?.cancel()
                        }
                    }
                )
            },
        state = listState,
    ) {
        itemsIndexed(items = items) { index, item ->
            DraggableItem(
                dragDropState = dragDropState,
                index = index
            ) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)
                Card(elevation = elevation) {
                    itemContent(item)
                }
            }
        }
    }
}