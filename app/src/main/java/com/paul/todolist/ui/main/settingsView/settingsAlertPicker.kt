package com.paul.todolist.ui.main.settingsView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.paul.todolist.R
import com.paul.todolist.ui.widgets.CustomNumberPicker

@Composable
fun SettingsAlertPicker(
    title: String,
    onDaysChanged: (days: Int) -> Unit,
    onColorChanged: (item: Color) -> Unit
) {

    var instructions = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        ) {
            append(stringResource(R.string.alerts_body_text1))
        }

        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.error,
            )
        ) {
            append(" " + title.uppercase() + " ")
        }

        withStyle(
            style = SpanStyle(
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        ) {
            append(stringResource(R.string.alerts_body_text2))
        }
    }


    Text(
        modifier = Modifier.padding(40.dp, 10.dp, 10.dp, 10.dp),
        text = instructions
    )

//    Text(
//        modifier = Modifier.padding(40.dp, 10.dp, 10.dp, 10.dp),
//        text = stringResource(R.string.alerts_body_text1) +
//               " "+ title.uppercase() + " " +
//                stringResource(R.string.alerts_body_text2),
//        style = typography.titleMedium,
//        color = MaterialTheme.colorScheme.secondary,
//    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        AndroidView(
            modifier = androidx.compose.ui.Modifier
                .width(100.dp)
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary),
            update = {
                it.invalidate()
            },
            factory = { context ->
                CustomNumberPicker(context).apply {
                    setOnValueChangedListener { _, _, days ->
                        onDaysChanged(days)
                    }
                    minValue = 0
                    maxValue = 24
                    value = 0
                }
            }
        )

        Spacer(modifier = Modifier.width(20.dp))
        val list = listOf(Color.Red, Color.Green, Color.Magenta, Color.Yellow, Color.LightGray)

        LazyColumn(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clickable(enabled = false, onClick = {})
        ) {
            items(items = list) { item ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .clickable(enabled = false, onClick = { onColorChanged(item) })
                        .border(width = 1.dp, color = Color.Black)
                        .background(item)
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun settingsAlertPickerPreview() {
    SettingsAlertPicker("Warning", {}, {})
}
