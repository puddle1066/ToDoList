package com.paullanducci.todolist.ui.main.tutorial.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paullanducci.todolist.R
import com.paullanducci.todolist.carousel_text_left_margin
import com.paullanducci.todolist.ui.theme.ToDoListTheme
import com.paullanducci.todolist.ui.theme.typography

@Composable
fun Screen_4() {
    ToDoListTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(Modifier.height(40.dp))

            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 0.dp, 10.dp, 0.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_4_1),
                style = typography.bodyMedium,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )
            Image(
                painterResource(R.drawable.menu_4_1),
                contentDescription = "menu_4_1",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(350.dp)
                    .height(100.dp)
                    .padding(40.dp, 10.dp, 30.dp, 0.dp)
            )

            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 0.dp, 10.dp, 0.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_4_2),
                style = typography.bodyMedium,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )

            Image(
                painterResource(R.drawable.screenshot_5),
                contentDescription = "screenshot_5",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(350.dp)
                    .height(410.dp)
                    .padding(40.dp, 10.dp, 10.dp, 0.dp)
            )

            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 0.dp, 10.dp, 0.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_4_3),
                style = typography.bodyMedium,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )

        }
    }
}

@Preview
@Composable
fun Preview_4() {
    Screen_4()
}