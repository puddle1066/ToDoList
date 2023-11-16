package com.paullanducci.todolist.ui.main.todoItemView.datePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paullanducci.todolist.ui.widgets.CustomNumberPicker
import java.util.Calendar

@Composable
fun dueDateTimePicker(initialDate: Calendar) {
    Row(
        modifier = Modifier
            .padding(70.dp, 0.dp, 70.dp, 0.dp)
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        AndroidView(
            modifier = Modifier
                .weight(0.20f)
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
                .weight(0.20f)
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

}