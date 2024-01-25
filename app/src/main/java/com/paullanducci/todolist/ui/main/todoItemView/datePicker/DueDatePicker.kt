package com.paullanducci.todolist.ui.main.todoItemView.datePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ui.widgets.CustomNumberPicker
import java.util.Calendar

@Composable
fun dueDatePicker(initialDate: Calendar, errorMessageState: MutableState<String>) {

    Row(
        modifier = Modifier
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        AndroidView(
            modifier = Modifier
                .weight(0.33f)
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
                    }
                    minValue = 1
                    maxValue = 31
                    value = initialDate.get(Calendar.DAY_OF_MONTH)
                }
            }
        )

        AndroidView(
            modifier = Modifier
                .weight(0.33f)
                .clickable(enabled = false, onClick = {})
                .background(MaterialTheme.colorScheme.primary),
            factory = { context ->
                CustomNumberPicker(context).apply {
                    setOnValueChangedListener { _, _, newVal ->
                        initialDate.set(Calendar.MONTH, newVal - 1)
                        errorMessageState.value = validation(initialDate)
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
                .weight(0.33f)
                .clickable(enabled = false, onClick = {})
                .background(MaterialTheme.colorScheme.primary),
            factory = { context ->
                CustomNumberPicker(context).apply {
                    setOnValueChangedListener { _, _, newVal ->
                        initialDate.set(Calendar.YEAR, newVal)
                        errorMessageState.value = validation(initialDate)
                    }
                    minValue = initialDate.get(Calendar.YEAR)
                    maxValue = initialDate.get(Calendar.YEAR) + 5
                    value = initialDate.get(Calendar.YEAR)
                }
            }
        )
    }

}