package com.paul.todolist.ui.main.todoItemView.datePicker

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.material.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyDatePickerPreview() {
    ToDoDatePicker(initialDate = Date(), onDateChange = {}, onCancel = {})
}

@Preview
@Composable
fun MyTimePickerPreview() {
    //  TimeDialog()
}


@SuppressLint("RestrictedApi", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoDatePicker(
    initialDate: Date = Date(),
    onDateChange: (newDate: Date) -> Unit,
    onCancel: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
                onCancel()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        datePickerState.selectedDateMillis?.let { Date(it) }
                            ?.let { onDateChange(it) }
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onCancel()
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

fun dateFor(ld: LocalDate): Date {
    return Date.from(
        ld.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
    )
}

fun localDateFor(date: Date): LocalDate {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

@Composable
fun TimeDialog() {
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    ).show()
}