package com.paullanducci.todolist.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())

fun getCurrentDateAsString(): String {
    return sdf.format(Date()).toString()
}
