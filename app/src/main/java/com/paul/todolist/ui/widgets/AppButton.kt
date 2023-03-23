package com.paul.todolist.ui.widgets

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ToDoList
import com.paul.todolist.ToDoScreens
import com.paul.todolist.ui.main.common.showView
import com.paul.todolist.ui.theme.typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppButtonIcon() {
    AppButton(
        drawingID = R.drawable.ic_measure,
        onButtonPressed = {showView(ToDoScreens.ToDoListView.name) }
    )
}

@Preview
@Composable
fun PreviewAppButtonText() {
    AppButton(
        onButtonPressed = {showView(ToDoScreens.SettingsView.name) },
        textID = R.string.Continue
    )
}



@Composable
fun AppButton() {
    Box(
        modifier = Modifier
            .scale(scale = 1f)
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(25))
            .fillMaxWidth()
            .height(90.dp)

    ) {}

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AppButton(
    drawingID : Int = -1,
    onButtonPressed: () -> Unit,
    textID : Int = -1
) {

    val animationDuration: Int = 100
    val scaleDown: Float = 0.9f
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    Button(
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
                onButtonPressed()
            }
        },
        modifier = Modifier
            .scale(scale = scale.value)
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(25))
            .fillMaxWidth()
            .height(90.dp)

    ) {
            if (drawingID != -1) {
                Icon(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp),
                    painter = painterResource(id = drawingID),
                    contentDescription = "exit app"
                )
            }

            if (textID != -1) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = stringResource(id =textID),
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
    }

    Spacer(modifier = Modifier.height(20.dp))
}
