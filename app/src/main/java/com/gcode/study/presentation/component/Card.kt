package com.gcode.study.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@Composable
fun MovieCard(title: String, subTitle: String) {
    BaseCard(
        title = { Title(title) },
        subTitle = { SubTitle(subTitle) },
        Modifier.padding(8.dp)
    )
}

@Composable
fun BaseCard(
    title: @Composable () -> Unit,
    subTitle: @Composable () -> Unit,
    modifier: Modifier
) {
    Card(modifier = modifier
    ) {
        Column {
            Box(modifier = Modifier.wrapContentSize()) {
                Column(modifier = Modifier.wrapContentSize()) {
                    title()
                    subTitle()
                }
            }
        }
    }
}



@Composable
fun Title(title: String) {
    Text(text = title, modifier = Modifier.padding(16.dp))
}

@Composable
fun SubTitle(subTitle: String) {
    Text(text = subTitle, modifier = Modifier.padding(16.dp))
}

@Composable
fun Image(url: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = url)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
    )
    BaseImage(painter = painter, modifier = modifier)
}

@Composable
fun BaseImage(painter: AsyncImagePainter, modifier: Modifier = Modifier) {
    Image(painter = painter, contentDescription = null, modifier = modifier)
}


@Preview
@Composable
fun MovieCardPreview() {
    BaseCard(
        title = { Title("Title") },
        subTitle = { SubTitle("SubTitle") },
        Modifier
            .wrapContentHeight()
            .wrapContentWidth()
    )
}