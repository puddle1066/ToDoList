package com.paullanducci.todolist.ui.main.settingsView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.ui.main.common.showView
import com.paullanducci.todolist.ui.theme.typography


@Composable
fun SettingsLink(text: String, screen: String) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.secondary
            )
        ) {
            append(text)
            addStringAnnotation(
                tag = "URL",
                annotation = "",
                start = length - text.length,
                end = length
            )
        }
    }
    ClickableText(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp, 0.dp, 0.dp, 10.dp),
        text = annotatedString,
        onClick = {
            showView(screen)
        },
        style = typography.bodyMedium,
    )
}