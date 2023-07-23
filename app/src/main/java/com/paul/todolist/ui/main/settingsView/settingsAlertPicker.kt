package com.paul.todolist.ui.main.settingsView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paul.todolist.ui.widgets.CustomNumberPicker

@Composable
fun settingsAlertPicker() {
    val columnWidth: Dp = ((LocalConfiguration.current.screenWidthDp - 50) / 3).dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.primary),
    ) {
        AndroidView(
            modifier = androidx.compose.ui.Modifier
                .width(columnWidth)
                .clickable(enabled = false, onClick = {})
                .padding(20.dp, 10.dp, 10.dp, 20.dp)
                .background(MaterialTheme.colorScheme.primary),
            update = {
                it.invalidate()
            },
            factory = { context ->
                CustomNumberPicker(context).apply {
                    setOnValueChangedListener { _, _, newVal ->
                        //        initialDate.set(Calendar.HOUR_OF_DAY, newVal)
                    }
                    minValue = 1
                    maxValue = 24
                    value = 1
                }
            }
        )

        Box(
            modifier = Modifier
                .padding(top = 16.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(20))
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                    RoundedCornerShape(20)
                )
                .clickable {
                    //     colorPickerOpen = true
                }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun settingsAlertPickerPreview() {
    settingsAlertPicker()
}
