package com.paul.todolist.ui.main.todoItemView.datePicker

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paul.todoList.R
import com.paul.todolist.ui.widgets.AppButton
import com.paul.todolist.ui.widgets.CustomNumberPicker
import java.util.Calendar
import java.util.GregorianCalendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    openDialog: MutableState<Boolean>,
    initialDate: Calendar = Calendar.getInstance(),
    onDateChange: (Calendar) -> Unit,
    onCancel: () -> Unit
) {

    val columnWidth: Dp = ((LocalConfiguration.current.screenWidthDp - 50) / 3).dp
    val maxDaysInMonth = getLastDayOfMonth(initialDate)
    val maxDaysInMonthState = remember { mutableStateOf(maxDaysInMonth) }

    if (openDialog.value) {
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
                                .background(MaterialTheme.colorScheme.primary),
                            update = {
                                it.invalidate()
                            },
                            factory = { context ->
                                CustomNumberPicker(context).apply {
                                    setOnValueChangedListener { _, _, newVal ->
                                        initialDate.set(Calendar.DAY_OF_MONTH, newVal)
                                        onDateChange(initialDate)
                                    }
                                    minValue = 1
                                    maxValue = maxDaysInMonthState.value
                                    value = initialDate.get(Calendar.DAY_OF_MONTH)
                                }
                            }
                        )

                        AndroidView(
                            modifier = Modifier
                                .width(columnWidth)
                                .background(MaterialTheme.colorScheme.primary),
                            factory = { context ->
                                CustomNumberPicker(context).apply {
                                    setOnValueChangedListener { _, _, newVal ->
                                        initialDate.set(Calendar.MONTH, newVal)
                                        maxDaysInMonthState.value =
                                            getLastDayOfMonth(initialDate)
                                        onDateChange(initialDate)
                                    }
                                    minValue = 1
                                    maxValue = 12
                                    value = getAdjustedMonth(initialDate)
                                    displayedValues = arrayOf(
                                        "Jan",
                                        "Feb",
                                        "Mar",
                                        "Apr",
                                        "May",
                                        "Jun",
                                        "Jul",
                                        "Aug",
                                        "Sep",
                                        "Oct",
                                        "Nov",
                                        "Dec"
                                    )
                                }
                            }
                        )

                        AndroidView(
                            modifier = Modifier
                                .width(columnWidth)
                                .background(MaterialTheme.colorScheme.primary),
                            factory = { context ->
                                CustomNumberPicker(context).apply {
                                    setOnValueChangedListener { _, _, newVal ->
                                        initialDate.set(Calendar.YEAR, newVal)
                                        maxDaysInMonthState.value =
                                            getLastDayOfMonth(initialDate)
                                        onDateChange(initialDate)
                                    }
                                    minValue = initialDate.get(Calendar.YEAR)
                                    maxValue = initialDate.get(Calendar.YEAR) + 5
                                    value = initialDate.get(Calendar.YEAR)
                                }
                            }
                        )
                    }
                    Spacer(Modifier.height(1.dp))
                    AppButton(
                        onButtonPressed = {
                            openDialog.value = false
                        },
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

fun getLastDayOfMonth(initialDate: Calendar): Int {
    val daysInMonth = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val cal: GregorianCalendar = initialDate as GregorianCalendar
    if (cal.isLeapYear(initialDate.get(Calendar.YEAR))) daysInMonth[1] = 29

    return daysInMonth[initialDate.get(Calendar.MONTH)]
}

fun getAdjustedMonth(initialDate: Calendar): Int {
    return initialDate.get(Calendar.MONTH) + 1
}




