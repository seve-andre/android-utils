package com.mitch.androidutils.utils.gradient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Creates a linear gradient with the provided colors
 * and angle.
 *
 * @param colors Colors of gradient
 * @param stops Offsets to determine how the colors are dispersed throughout
 * the vertical gradient
 * @param tileMode Determines the behavior for how the shader is to fill a region outside
 * its bounds. Defaults to [TileMode.Clamp] to repeat the edge pixels
 * @param angle Angle of a gradient in degrees
 * @param isCssAngle Determines whether the CSS gradient angle should be used
 * by default cartesian angle is used
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/gradient/linear-gradient">
 *     linear-gradient</a>
 */
@Immutable
class LinearGradient(
    private val colors: List<Color>,
    private val stops: List<Float>? = null,
    private val tileMode: TileMode = TileMode.Clamp,
    angle: Float = 0f,
    isCssAngle: Boolean = false
) : ShaderBrush() {

    // handle edge cases like: -1235, ...
    private val normalizedAngle: Float = if (isCssAngle) {
        ((90 - angle) % 360 + 360) % 360
    } else {
        (angle % 360 + 360) % 360
    }
    private val angleInRadians: Float = Math.toRadians(normalizedAngle.toDouble()).toFloat()

    override fun createShader(size: Size): Shader {
        val (from, to) = getGradientCoordinates(size = size)

        return LinearGradientShader(
            colors = colors,
            colorStops = stops,
            from = from,
            to = to,
            tileMode = tileMode
        )
    }

    private fun getGradientCoordinates(size: Size): Pair<Offset, Offset> {
        val diagonal = sqrt(size.width.pow(2) + size.height.pow(2))
        val angleBetweenDiagonalAndWidth = acos(size.width / diagonal)
        val angleBetweenDiagonalAndGradientLine =
            if ((normalizedAngle > 90 && normalizedAngle < 180)
                || (normalizedAngle > 270 && normalizedAngle < 360)
            ) {
                PI.toFloat() - angleInRadians - angleBetweenDiagonalAndWidth
            } else {
                angleInRadians - angleBetweenDiagonalAndWidth
            }
        val halfGradientLine = abs(cos(angleBetweenDiagonalAndGradientLine) * diagonal) / 2

        val horizontalOffset = halfGradientLine * cos(angleInRadians)
        val verticalOffset = halfGradientLine * sin(angleInRadians)

        val start = size.center + Offset(-horizontalOffset, verticalOffset)
        val end = size.center + Offset(horizontalOffset, -verticalOffset)

        return start to end
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinearGradient) return false

        if (colors != other.colors) return false
        if (stops != other.stops) return false
        if (normalizedAngle != other.normalizedAngle) return false
        if (tileMode != other.tileMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = colors.hashCode()
        result = 31 * result + (stops?.hashCode() ?: 0)
        result = 31 * result + normalizedAngle.hashCode()
        result = 31 * result + tileMode.hashCode()
        return result
    }

    override fun toString(): String {
        return "LinearGradient(colors=$colors, " +
                "stops=$stops, " +
                "angle=$normalizedAngle, " +
                "tileMode=$tileMode)"
    }
}

/**
 * Creates a linear gradient with the provided colors
 * and angle.
 *
 * @param colors Colors of gradient
 * @param stops Offsets to determine how the colors are dispersed throughout
 * the vertical gradient
 * @param angle Angle of a gradient in degrees
 * @param isCssAngle Determines whether the CSS gradient angle should be used
 * by default cartesian angle is used
 * @param tileMode Determines the behavior for how the shader is to fill a region outside
 * its bounds. Defaults to [TileMode.Clamp] to repeat the edge pixels
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/gradient/linear-gradient">
 *     linear-gradient</a>
 */
@Stable
fun Brush.Companion.linearGradient(
    colors: List<Color>,
    angle: Float = 0f,
    isCssAngle: Boolean = false,
    tileMode: TileMode = TileMode.Clamp
): Brush = LinearGradient(
    colors = colors,
    stops = null,
    angle = angle,
    isCssAngle = isCssAngle,
    tileMode = tileMode
)

/**
 * Creates a linear gradient with the provided colors
 * and angle.
 *
 * @param colorStops Colors and their offset in the gradient area
 * @param angle Angle of a gradient in degrees
 * @param isCssAngle Determines whether the CSS gradient angle should be used
 * by default cartesian angle is used
 * @param tileMode Determines the behavior for how the shader is to fill a region outside
 * its bounds. Defaults to [TileMode.Clamp] to repeat the edge pixels
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/gradient/linear-gradient">
 *     linear-gradient</a>
 */
@Stable
fun Brush.Companion.linearGradient(
    vararg colorStops: Pair<Float, Color>,
    angle: Float = 0f,
    isCssAngle: Boolean = false,
    tileMode: TileMode = TileMode.Clamp
): Brush = LinearGradient(
    colors = colorStops.map { it.second },
    stops = colorStops.map { it.first },
    angle = angle,
    isCssAngle = isCssAngle,
    tileMode = tileMode
)

@Preview(device = "spec:width=1920dp,height=1080dp,dpi=160")
@Composable
private fun GradientPreview() {
    Column {
        val gradients = listOf(
            arrayOf(0F to Color.Red, 100F to Color.Blue) to 0f,
            arrayOf(0F to Color.Red, 100F to Color.Blue) to 180f,
            arrayOf(0F to Color.Red, 100F to Color.Blue) to 90f,
            arrayOf(0F to Color.Red, 100F to Color.Blue) to 45f
        )

        LazyRow(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(gradients) { gradient ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            Brush.linearGradient(
                                colorStops = gradient.first,
                                angle = gradient.second,
                                isCssAngle = false
                            )
                        )
                )
            }
        }
    }
}
