package com.mitch.androidutils.utils.designsystem.components.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.mitch.androidutils.utils.designsystem.util.AppLayoutId
import com.mitch.androidutils.utils.designsystem.util.TextLineLimit

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    lineLimit: TextLineLimit = TextLineLimit.Default,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    shadow: Shadow? = null,
    drawStyle: DrawStyle? = null,
    textIndent: TextIndent? = null
) {
    Text(
        text = text,
        modifier = Modifier.layoutId(AppLayoutId.AppTextId).then(modifier),
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        minLines = when (lineLimit) {
            TextLineLimit.SingleLine -> 1
            is TextLineLimit.MultiLine -> lineLimit.min
        },
        maxLines = when (lineLimit) {
            TextLineLimit.SingleLine -> 1
            is TextLineLimit.MultiLine -> lineLimit.max
        },
        onTextLayout = onTextLayout,
        style = LocalTextStyle.current.merge(
            shadow = shadow,
            drawStyle = drawStyle,
            textIndent = textIndent
        )
    )
}

@Preview
@Composable
private fun AppTextColorPreview() {
    MaterialTheme {
        AppText(
            text = "Hello World",
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AppText(
    text: String,
    brush: Brush,
    modifier: Modifier = Modifier,
    alpha: Float = Float.NaN,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    lineLimit: TextLineLimit = TextLineLimit.Default,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    shadow: Shadow? = null,
    drawStyle: DrawStyle? = null,
    textIndent: TextIndent? = null,
) {
    Text(
        text = text,
        modifier = Modifier.layoutId(AppLayoutId.AppTextId).then(modifier),
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        minLines = when (lineLimit) {
            TextLineLimit.SingleLine -> 1
            is TextLineLimit.MultiLine -> lineLimit.min
        },
        maxLines = when (lineLimit) {
            TextLineLimit.SingleLine -> 1
            is TextLineLimit.MultiLine -> lineLimit.max
        },
        onTextLayout = onTextLayout,
        style = TextStyle(
            brush = brush,
            alpha = alpha
        ).merge(
            shadow = shadow,
            drawStyle = drawStyle,
            textIndent = textIndent
        )
    )
}

@Preview
@Composable
private fun AppTextGradientPreview() {
    MaterialTheme {
        val gradientColors = listOf(Color.Cyan, Color.Blue)
        AppText(
            text = "Hello World",
            brush = Brush.linearGradient(colors = gradientColors)
        )
    }
}
