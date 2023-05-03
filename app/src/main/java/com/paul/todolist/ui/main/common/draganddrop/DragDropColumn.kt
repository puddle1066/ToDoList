package com.paul.todolist.ui.main.common.draganddrop

import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> DragDropColumn(
    items: List<T>,
    onSwap: (Int, Int) -> Unit,
    moveAllowed: Boolean,
    onDragStart: (index: Int) -> Unit,
    onDragEnd: (index: Int) -> Unit,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit
) {
    var TAG = object {}::class.java.enclosingMethod.name
    var overscrollJob by remember { mutableStateOf<Job?>(null) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val dragDropState = rememberDragDropState(listState) { fromIndex, toIndex ->
        onSwap(fromIndex, toIndex)
    }

    val isCurrentlyDragging = remember { mutableStateOf(false) }
    val lastIndex = remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .pointerInput(dragDropState) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consume()
                        if (moveAllowed) {
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
                        if (moveAllowed) {
                            Log.e(TAG, "onDragStart")
                            dragDropState.onDragStart(it)
                        }
                    },
                    onDragEnd = {
                        if (moveAllowed) {
                            dragDropState.onDragInterrupted()
                            overscrollJob?.cancel()
                            isCurrentlyDragging.value = false
                            onDragEnd(lastIndex.value)
                            Log.e(TAG, "onDragEnd")
                        }
                    },
                    onDragCancel = {
                        if (moveAllowed) {
                            dragDropState.onDragInterrupted()
                            overscrollJob?.cancel()
                            isCurrentlyDragging.value = false
                            onDragEnd(lastIndex.value)
                            Log.e(TAG, "onDragCancel")
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
                if (isDragging) {
                    if (!isCurrentlyDragging.value) {
                        isCurrentlyDragging.value = true
                        onDragStart(index)
                    } else {
                        lastIndex.value = index
                    }
                }

                Card(elevation = elevation) {
                    itemContent(item)
                }
            }
        }
    }
}