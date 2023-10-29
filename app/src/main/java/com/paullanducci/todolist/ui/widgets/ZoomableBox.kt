package com.paullanducci.todolist.ui.widgets

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

@Composable
fun ZoomableBox(
    modifier: Modifier = Modifier,
    minScale: Float = 0.1f,
    maxScale: Float = 5f,
    content: @Composable ZoomableBoxScope.() -> Unit
) {
    val scale = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val size = remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .clip(RectangleShape)
            .onSizeChanged { size.value = it }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale.value = maxOf(minScale, minOf(scale.value * zoom, maxScale))
                    val maxX = (size.value.width * (scale.value - 1)) / 2
                    val minX = -maxX
                    offsetX.value = maxOf(minX, minOf(maxX, offsetX.value + pan.x))
                    val maxY = (size.value.height * (scale.value - 1)) / 2
                    val minY = -maxY
                    offsetY.value = maxOf(minY, minOf(maxY, offsetY.value + pan.y))
                }
            }
    ) {
        val scope = ZoomableBoxScopeImpl(scale.value, offsetX.value, offsetY.value)
        scope.content()
    }
}

interface ZoomableBoxScope {
    val scale: Float
    val offsetX: Float
    val offsetY: Float
}

private data class ZoomableBoxScopeImpl(
    override val scale: Float,
    override val offsetX: Float,
    override val offsetY: Float
) : ZoomableBoxScope