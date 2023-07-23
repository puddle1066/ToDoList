package com.paul.todolist.ui.main.todoItemView.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paul.todolist.R
import com.paul.todolist.SPEECH_LANGUAGE
import com.paul.todolist.ui.main.common.speechToText.VoiceToTextParserState
import com.paul.todolist.ui.main.todoItemView.ToDoItemModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ToDoSpeechButton(model: ToDoItemModel, voiceState: VoiceToTextParserState) {

    val colorSelected = MaterialTheme.colorScheme.surface
    val colorUnSelected = MaterialTheme.colorScheme.primary
    val backgroundColor = remember { mutableStateOf(colorUnSelected) }

    val animationDuration = 100
    val scaleDown = 0.9f
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

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

                    if (voiceState.isSpeaking) {
                        model.voiceToText.stopListening()
                        backgroundColor.value = colorUnSelected
                    } else {
                        model.voiceToText.startListening(SPEECH_LANGUAGE)
                        voiceState.spokenText = ""
                        backgroundColor.value = colorSelected
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor.value),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(25), // = 50% percent

        ) {
            AnimatedContent(targetState = voiceState.isSpeaking) { isSpeaking ->
                if (isSpeaking) {
                    Icon(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp),
                        imageVector = Icons.Rounded.Stop,
                        contentDescription = stringResource(id = R.string.missing_resource)
                    )
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


