package com.paullanducci.todolist.ui.main.todoItemView.datePicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ui.main.todoItemView.ToDoItemModel
import com.paullanducci.todolist.ui.theme.typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ToDoDueDate(
    model: ToDoItemModel,
    addButtonVisibility: MutableState<Boolean>
) {
    val animationDuration = 100
    val scaleDown = 0.9f
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    val openDialog = remember { mutableStateOf(false) }

    val backgroundColor: Color = MaterialTheme.colorScheme.primary

    val currentDueTime = stringResource(R.string.none_specified)
    val dateState = remember { mutableStateOf(currentDueTime) }

    if (model.todoDataItem.dueDate != "0") {
        dateState.value = model.todoDataItem.dueDate
    }

    DatePickerDialog(
        openDialog,
        model.todoDataItem.dueDate,
        onDateChange = {
            addButtonVisibility.value = true
            dateState.value = it
            model.todoDataItem.dueDate = it
        },
        onCancel = {},
        onDelete = {
            model.todoDataItem.dueDate = "0"
            dateState.value = currentDueTime
        }
    )

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally()
    ) {
        Row(
            modifier = Modifier
                .scale(scale = scale.value)
                .padding(10.dp, 10.dp, 10.dp, 0.dp)
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(backgroundColor)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(15.dp)
                )
        )
        {
            Column {
                Text(
                    modifier = Modifier
                        .height(20.dp)
                        .width(100.dp)
                        .padding(20.dp, 3.dp, 0.dp, 0.dp),
                    text = stringResource(R.string.due_date),
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.surface,
                )

                Button(
                    modifier = Modifier
                        .width(295.dp)
                        .height(43.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
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
                        }
                        openDialog.value = true
                    },
                ) {
                    Text(
                        modifier = Modifier
                            .padding(3.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth(),
                        text = dateState.value,
                        style = typography.bodyLarge,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Icon(
                Icons.Outlined.DateRange,
                null,
                modifier = Modifier
                    .padding(0.dp, 17.dp, 0.dp, 0.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .background(MaterialTheme.colorScheme.primary),
            )
        }
    }
}