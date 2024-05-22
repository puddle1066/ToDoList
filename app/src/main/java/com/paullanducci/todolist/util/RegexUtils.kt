package com.paullanducci.todolist.util

import java.util.regex.Pattern

object RegexUtils {
    fun replaceAll(
        pattern: Pattern,
        string: String,
        replacement: String
    ): String {
        return pattern.matcher(string).replaceAll(replacement)
    }
}