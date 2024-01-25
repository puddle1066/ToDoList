package com.paullanducci.todolist.ui.main.todoListView

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.paullanducci.todolist.dateFormat
import com.paullanducci.todolist.di.database.data.ToDoDataItem
import com.paullanducci.todolist.ui.main.todoItemView.ListItemAlertsData
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Composable
fun checkOverdueOrLate(alerts: ListItemAlertsData, item: ToDoDataItem): Color {
    var color = MaterialTheme.colorScheme.secondary

    if (item.dueDate == "0") return color

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
    val from = LocalDate.parse(item.dueDate, dateFormatter)
    val period = Period.between(from, LocalDate.now())

    if (period.days > alerts.daysOverdueThreshold) {
        color = alerts.colorOverdue
    }
    if (period.days > alerts.dayLateThreshold) {
        color = alerts.colorLate
    }

    Log.e("AAAA", "Due date ${item.dueDate}  -  ${period.days}")

    return color
}
