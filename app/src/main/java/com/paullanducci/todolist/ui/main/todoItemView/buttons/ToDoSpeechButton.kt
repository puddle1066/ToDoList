package com.paullanducci.todolist.ui.main.todoItemView.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ToDoSpeechButton(model: ToDoItemModel, toggleSpeechButton: MutableState<Boolean>) {

    val colorSelected = MaterialTheme.colorScheme.surface
    val colorUnSelected = MaterialTheme.colorScheme.primary
    val colorPulse = MaterialTheme.colorScheme.primary
    val backgroundColor = remember { mutableStateOf(colorUnSelected) }

    val animationDuration = 100
    val scaleDown = 0.9f
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    // button toggle actions
    if (toggleSpeechButton.value) {
        model.speechInputDevice.tryToGetInput(true)
        backgroundColor.value = colorSelected
    } else {
        backgroundColor.value = colorUnSelected
    }

    Row(modifier = Modifier.padding(10.dp)) {

        Button(
            modifier = Modifier
                .scale(scale = scale.value)
                .fillMaxWidth()
                .height(90.dp),

            onClick = {
                coroutineScope.launch {
                    scale.animateTo(
                        scaleDown,
                        animationSpec = tween(animationDuration),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(animationDuration),
                    )
                    delay((animationDuration).toLong())    //Wait for anim to finish before launching

                    toggleSpeechButton.value = !toggleSpeechButton.value
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor.value),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(25), // = 50% percent

        ) {
            var targetState = (backgroundColor.value == colorSelected)
            AnimatedContent(targetState = targetState, label = "") { isSpeaking ->
                if (isSpeaking) {
                    val infiniteTransition = rememberInfiniteTransition(label = "")

                    val radius = infiniteTransition.animateFloat(
                        initialValue = 50f,
                        targetValue = 130f,
                        animationSpec = InfiniteRepeatableSpec(
                            animation = tween(1000),
                            repeatMode = RepeatMode.Restart
                        ), label = ""
                    )

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(Modifier.fillMaxSize(),
                            onDraw = {
                                drawCircle(color = colorPulse, radius.value)
                            })
                        Icon(
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp),
                            imageVector = Icons.Rounded.Stop,
                            contentDescription = stringResource(id = R.string.missing_resource)
                        )
                    }

                    backgroundColor.value = colorSelected
                } else {
                    Icon(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp),
                        imageVector = Icons.Rounded.Mic,
                        contentDescription = stringResource(id = R.string.missing_resource)
                    )
                    backgroundColor.value = colorUnSelected
                }
            }
        }
    }
}