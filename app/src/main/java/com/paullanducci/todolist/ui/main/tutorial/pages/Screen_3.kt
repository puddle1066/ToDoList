package com.paullanducci.todolist.ui.main.tutorial.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalConfiguration
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
fun Screen_3() {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    ToDoListTheme {
        Column(
            modifier = Modifier
                .width(screenWidth)
                .height(screenHeight)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 10.dp, 50.dp, 10.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_3_6),
                style = typography.bodyLarge,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )

            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 0.dp, 10.dp, 0.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_3_1),
                style = typography.bodyMedium,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )
            Image(
                painterResource(R.drawable.menu_3_1),
                contentDescription = "menu_3_1",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp)
                    .padding(40.dp, 10.dp, 30.dp, 0.dp)
            )

            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 10.dp, 0.dp, 10.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_3_2),
                style = typography.bodyMedium,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )

            Image(
                painterResource(R.drawable.menu_3_6),
                contentDescription = "menu_3_6",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(350.dp)
                    .height(100.dp)
                    .padding(40.dp, 0.dp, 30.dp, 0.dp)
            )

            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 10.dp, 0.dp, 10.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_3_3),
                style = typography.bodyMedium,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )
            Image(
                painterResource(R.drawable.menu_3_4),
                contentDescription = "menu_3_4",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(350.dp)
                    .height(100.dp)
                    .padding(40.dp, 0.dp, 30.dp, 0.dp)
            )

            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 10.dp, 0.dp, 10.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_3_4),
                style = typography.bodyMedium,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )

            Image(
                painterResource(R.drawable.menu_3_2),
                contentDescription = "menu_3_2",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(350.dp)
                    .height(100.dp)
                    .padding(40.dp, 0.dp, 30.dp, 0.dp)
            )

            Text(
                modifier = Modifier
                    .padding(carousel_text_left_margin, 10.dp, 0.dp, 10.dp)
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.screen_3_5),
                style = typography.bodyMedium,
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.secondary,
            )

            Image(
                painterResource(R.drawable.menu_3_5),
                contentDescription = "menu_3_5",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(350.dp)
                    .height(100.dp)
                    .padding(40.dp, 0.dp, 30.dp, 0.dp)
            )
        }
    }
}


@Preview
@Composable
fun Preview_3() {
    Screen_3()
}