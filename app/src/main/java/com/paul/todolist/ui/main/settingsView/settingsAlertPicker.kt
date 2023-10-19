package com.paul.todolist.ui.main.settingsView

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SettingsAlertPicker(
    title: String,
    currentDays: Int,
    currentColor: Color = Color.Red,
    onDaysChanged: (Int) -> Unit,
    onColorChanged: (Color) -> Unit
) {

    val currentColorChanged = remember { mutableStateOf<Color>(currentColor) }

    val instructions = buildAnnotatedString {
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
                color = currentColorChanged.value,
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        AndroidView(
            modifier = Modifier
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
                    value = currentDays
                }
            }
        )

        Spacer(modifier = Modifier.width(20.dp))

        //Fill the list with various colors to pick from
        val list = mutableListOf(Color.White)
        var selectedIndex = 0
        var index = 4278190080
        var count = 0
        while (index < 4294967295) {
            index += 40000
            count++
            if (Color(index) != Color.White) {
                list.add(Color(index))
            }
            if (Color(index) == currentColor) {
                selectedIndex = count
                Log.e("Color", "Selected index = $selectedIndex Color $currentColor")
            }
        }

        val state = rememberLazyListState()

        if (selectedIndex > 0) {
            CoroutineScope(Dispatchers.Main).launch {
                state.scrollToItem(selectedIndex)
            }
        }

        LazyColumn(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            state = state
        ) {
            items(items = list) { item ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .clickable(onClick = {
                            onColorChanged(item)
                            currentColorChanged.value = item
                        })
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
fun SettingsAlertPickerPreview() {
    SettingsAlertPicker("Warning", currentDays = 10, currentColor = Color.Red, {}, {})
}
