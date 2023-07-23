package com.paul.todolist.ui.main.imageView

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paul.todolist.R
import com.paul.todolist.ui.main.MainActivity
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.widgets.ZoomableBox
import com.paul.todolist.util.dpToPx
import com.paul.todolist.util.rotate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemImageView() {

    val imageState = remember { mutableStateOf(MainActivity.image) }

    val configuration = LocalConfiguration.current
    val screenHeight = dpToPx(dp = configuration.screenHeightDp.toFloat()).toInt() - 200
    val screenWidth = dpToPx(configuration.screenWidthDp.toFloat()).toInt()

    val image =
        imageState.value?.let {
            Bitmap.createScaledBitmap(it, screenHeight, screenWidth, false).rotate(270f)
        }

    ToDoListTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ImageHeadingView()

            ZoomableBox {
                if (image != null) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offsetX,
                                translationY = offsetY
                            ),
                        bitmap = image.asImageBitmap(),
                        contentDescription = stringResource(id = R.string.missing_resource)
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemImageViewPreview() {
    ItemImageView()
}



