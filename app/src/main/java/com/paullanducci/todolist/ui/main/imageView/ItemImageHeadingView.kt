package com.paullanducci.todolist.ui.main.imageView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun ImageHeadingView() {
    ToDoListTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                modifier = Modifier.padding(40.dp, 15.dp, 10.dp, 10.dp),
                text = stringResource(R.string.image_view),
                style = typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, device = "id:8in Foldable")
@Preview(showSystemUi = true, device = "spec:width=800dp,height=1280dp, dpi=480")
@Composable
fun Preview() {
    ImageHeadingView()
}


