package com.mitch.androidutils.utils.gradient

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun Modifier.angledGradient(vararg colorStops: Pair<Float, Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * PI
        val x = cos(angleRad).toFloat()
        val y = sin(angleRad).toFloat()

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colorStops = colorStops,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)

fun Modifier.angledGradient(colors: List<Color>, angle: Float) = this.then(
    angledGradient(colorStops = colors.toColorStops(), angle = angle)
)

private fun List<Color>.toColorStops(): Array<Pair<Float, Color>> {
    require(this.isNotEmpty())

    if (this.size == 1) return arrayOf(1f to this.first())

    val step = 1f / (this.size - 1)
    return this.mapIndexed { index, color -> (index * step) to color }.toTypedArray()
}
