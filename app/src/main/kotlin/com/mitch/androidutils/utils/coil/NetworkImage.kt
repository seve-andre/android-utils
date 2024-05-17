package com.mitch.androidutils.utils.coil

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.mitch.androidutils.R

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    if (LocalInspectionMode.current) {
        FallbackImage()
    } else {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            modifier = modifier.fillMaxSize(),
            contentScale = contentScale,
            placeholder = rememberAnimatedVectorPainter(
                AnimatedImageVector.animatedVectorResource(R.drawable.animated_loading_indicator),
                false
            ),
            fallback = painterResource(id = R.drawable.network_image_fallback)
        )
    }
}

@Composable
fun FallbackImage() {
    Image(
        painter = painterResource(id = R.drawable.network_image_fallback),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}
