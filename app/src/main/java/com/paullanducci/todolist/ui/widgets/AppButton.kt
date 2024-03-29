package com.paullanducci.todolist.ui.widgets

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ToDoScreens
import com.paullanducci.todolist.ui.main.common.showView
import com.paullanducci.todolist.ui.theme.typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AppButton(
    drawingID: Int = -1,
    imageVector: ImageVector? = null,
    onButtonPressed: () -> Unit,
    textID: Int = -1,
    textString: String = "",
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    buttonVisible: Boolean = true
) {

    val animationDuration = 100
    val scaleDown = 0.9f
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    AnimatedVisibility(
        visible = buttonVisible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally()
    ) {
        Button(
            modifier = Modifier
                .scale(scale = scale.value)
                .fillMaxWidth()
                .padding(10.dp)
                .height(90.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(25),
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
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

            ) {
            if (imageVector != null) {
                Image(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp),
                    imageVector = imageVector,
                    contentDescription = stringResource(id = R.string.missing_resource)
                )
            }

            if (drawingID != -1) {
                Icon(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp),
                    painter = painterResource(id = drawingID),
                    contentDescription = stringResource(id = R.string.missing_resource),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            if (textID != -1) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = stringResource(id = textID),
                    style = typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            if (textString.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = textString,
                    style = typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppButtonIcon() {
    AppButton(
        drawingID = com.google.android.material.R.drawable.ic_clock_black_24dp,
        onButtonPressed = { showView(ToDoScreens.ToDoListView.name) }
    )
}

@Preview
@Composable
fun PreviewAppButtonText() {
    AppButton(
        onButtonPressed = { showView(ToDoScreens.SettingsView.name) },
        textID = R.string.Continue
    )
}

@Preview
@Composable
fun PreviewAppButtonImage() {
    AppButton(
        onButtonPressed = { showView(ToDoScreens.SettingsView.name) },
        imageVector = Icons.Filled.Mic,
    )
}

@Preview
@Composable
fun PreviewAppButtonFreeFornatText() {
    AppButton(
        onButtonPressed = { showView(ToDoScreens.SettingsView.name) },
        textString = "Free Format Text"
    )
}

