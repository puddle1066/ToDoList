package com.paullanducci.todolist.ui.main.todoItemView.datePicker

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paullanducci.todolist.R
import com.paullanducci.todolist.dateFormat
import com.paullanducci.todolist.ui.theme.typography
import com.paullanducci.todolist.ui.widgets.AppButton
import com.paullanducci.todolist.ui.widgets.CustomNumberPicker
import com.paullanducci.todolist.util.playClick
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    openDialog: MutableState<Boolean>,
    currentDueDate: String,
    onDateChange: (String) -> Unit,
    onCancel: () -> Unit
) {
    val ctx = LocalContext.current

    val columnWidth: Dp = ((LocalConfiguration.current.screenWidthDp - 50) / 3).dp
    val dateFormatter = SimpleDateFormat(dateFormat, Locale.getDefault())

    val errorMessageState = remember { mutableStateOf("") }

    val initialDate = Calendar.getInstance()
    val backgoundColor = MaterialTheme.colorScheme.primary
    val buttonBackgroundColor = remember { mutableStateOf(backgoundColor) }

    if (openDialog.value) {
        if (currentDueDate != "0") {
            initialDate.time = dateFormatter.parse(currentDueDate)
        }
        errorMessageState.value = ""

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                )
                {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .background(MaterialTheme.colorScheme.onPrimary)
                    ) {
                        AndroidView(
                            modifier = Modifier
                                .width(columnWidth)
                                .clickable(enabled = false, onClick = {})
                                .background(MaterialTheme.colorScheme.primary),
                            update = {
                                it.invalidate()
                            },
                            factory = { context ->
                                CustomNumberPicker(context).apply {
                                    setOnValueChangedListener { _, _, newVal ->
                                        initialDate.set(Calendar.DAY_OF_MONTH, newVal)
                                        errorMessageState.value = validation(initialDate)
                                        playClick(ctx)
                                    }
                                    minValue = 1
                                    maxValue = 31
                                    value = initialDate.get(Calendar.DAY_OF_MONTH)
                                }
                            }
                        )

                        AndroidView(
                            modifier = Modifier
                                .width(columnWidth)
                                .clickable(enabled = false, onClick = {})
                                .background(MaterialTheme.colorScheme.primary),
                            factory = { context ->
                                CustomNumberPicker(context).apply {
                                    setOnValueChangedListener { _, _, newVal ->
                                        initialDate.set(Calendar.MONTH, newVal - 1)
                                        errorMessageState.value = validation(initialDate)
                                        playClick(ctx)
                                    }
                                    minValue = 1
                                    maxValue = 12
                                    value = getAdjustedMonth(initialDate)
                                    displayedValues = resources.getStringArray(R.array.month_names)
                                }
                            }
                        )

                        AndroidView(
                            modifier = Modifier
                                .width(columnWidth)
                                .clickable(enabled = false, onClick = {})
                                .background(MaterialTheme.colorScheme.primary),
                            factory = { context ->
                                CustomNumberPicker(context).apply {
                                    setOnValueChangedListener { _, _, newVal ->
                                        initialDate.set(Calendar.YEAR, newVal)
                                        errorMessageState.value = validation(initialDate)
                                        playClick(ctx)
                                    }
                                    minValue = initialDate.get(Calendar.YEAR)
                                    maxValue = initialDate.get(Calendar.YEAR) + 5
                                    value = initialDate.get(Calendar.YEAR)
                                }
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(70.dp, 0.dp, 0.dp, 0.dp)
                            .background(MaterialTheme.colorScheme.onPrimary)
                    ) {
                        AndroidView(
                            modifier = Modifier
                                .width(columnWidth)
                                .clickable(enabled = false, onClick = {})
                                .background(MaterialTheme.colorScheme.primary),
                            update = {
                                it.invalidate()
                            },
                            factory = { context ->
                                CustomNumberPicker(context).apply {
                                    setOnValueChangedListener { _, _, newVal ->
                                        initialDate.set(Calendar.HOUR_OF_DAY, newVal)
                                    }
                                    minValue = 1
                                    maxValue = 24
                                    value = initialDate.get(Calendar.HOUR_OF_DAY)
                                }
                            }
                        )

                        AndroidView(
                            modifier = Modifier
                                .width(columnWidth)
                                .clickable(enabled = false, onClick = {})
                                .background(MaterialTheme.colorScheme.primary),
                            factory = { context ->
                                CustomNumberPicker(context).apply {
                                    setOnValueChangedListener { _, _, newVal ->
                                        initialDate.set(Calendar.MINUTE, newVal)

                                    }
                                    minValue = 1
                                    maxValue = 50
                                    value = initialDate.get(Calendar.MINUTE)
                                }
                            }
                        )
                    }


                    Text(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(20.dp, 0.dp, 0.dp, 0.dp),
                        text = errorMessageState.value,
                        style = typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(1.dp))

                    //Button enabled or not
                    if (errorMessageState.value.isEmpty()) {
                        buttonBackgroundColor.value = MaterialTheme.colorScheme.primary
                    } else {
                        buttonBackgroundColor.value = MaterialTheme.colorScheme.background
                    }

                    AppButton(
                        onButtonPressed = {
                            if (errorMessageState.value.isEmpty()) {
                                openDialog.value = false
                                onDateChange(dateFormatter.format(initialDate.time))
                            }
                        },
                        backgroundColor = buttonBackgroundColor.value,
                        textID = R.string.ok
                    )
                    Spacer(Modifier.height(1.dp))

                    AppButton(
                        onButtonPressed = {
                            openDialog.value = false
                            onCancel()
                        },
                        textID = R.string.cancel
                    )
                    Spacer(Modifier.height(1.dp))
                }
            }
        }
    }
}

fun validation(initDate: Calendar): String {
    if (initDate.before(Calendar.getInstance())) {
        return "Due Date cannot be in the past"
    }
    if (initDate.get(Calendar.MONTH) == 1 &&
        initDate.get(Calendar.DAY_OF_MONTH) > 28
    ) {
        return "Invalid Date"
    }
    return ""
}

fun getAdjustedMonth(initialDate: Calendar): Int {
    return initialDate.get(Calendar.MONTH) + 1
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MeasurePreview() {
    val openDialog = remember { mutableStateOf(true) }
    DatePickerDialog(openDialog, "01/JAN/2023 23:59 AM", onDateChange = {}, onCancel = {})
}
