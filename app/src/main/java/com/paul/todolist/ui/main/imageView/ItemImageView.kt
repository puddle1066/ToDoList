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
import androidx.compose.ui.unit.dp
import com.paul.todoList.R
import com.paul.todolist.ui.main.MainView
import com.paul.todolist.ui.theme.ToDoListTheme
import com.paul.todolist.ui.widgets.ZoomableBox
import com.paul.todolist.util.decodeBase64

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemImageView() {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value
    val screenHeight = configuration.screenHeightDp.dp.value

    //Create a placeholder image until we can populate the image
    val image: Bitmap =
        Bitmap.createBitmap(screenWidth.toInt(), screenHeight.toInt(), Bitmap.Config.ARGB_8888)
    val imageState = remember { mutableStateOf(image) }
    decodeBase64(MainView.image)?.let {
        imageState.value = it
    }

    ToDoListTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ImageHeadingView()

            ZoomableBox {
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
                    bitmap = imageState.value.asImageBitmap(),
                    contentDescription = stringResource(id = R.string.missing_resource)
                )
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



