package com.mitch.androidutils.utils.locale

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

@Composable
fun LocalizedClickableText(
    text: String,
    linkTexts: List<LinkText>,
    modifier: Modifier = Modifier,
    onClick: ((String) -> Unit)? = null, // default is open url in browser
    color: Color = Color.Unspecified,
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null,
    maxLines: Int = Int.MAX_VALUE,
    letterSpacing: TextUnit? = null,
    lineHeight: TextUnit? = null,
    textOverflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign? = null
) {
    val annotatedString = buildAnnotatedString {
        append(text)
        linkTexts.forEach { linkText ->
            val startIndex = text.indexOf(linkText.text)
            val endIndex = startIndex + linkText.text.length
            addStyle(
                style = linkText.style,
                start = startIndex,
                end = endIndex
            )
            addLink(
                url = LinkAnnotation.Url(
                    linkText.url,
                    linkInteractionListener = onClick?.let {
                        { annotation ->
                            val url = (annotation as LinkAnnotation.Url).url
                            it(url)
                        }
                    }
                ),
                start = startIndex,
                end = endIndex
            )
        }
    }

    Text(
        text = annotatedString,
        modifier = modifier,
        style = TextStyle(
            color = color,
            fontSize = fontSize ?: TextUnit.Unspecified,
            fontWeight = fontWeight,
            letterSpacing = letterSpacing ?: TextUnit.Unspecified,
            textAlign = textAlign ?: TextAlign.Unspecified,
            lineHeight = lineHeight ?: TextUnit.Unspecified
        ),
        overflow = textOverflow,
        maxLines = maxLines
    )
}

data class LinkText(
    val text: String,
    val url: String,
    val style: SpanStyle
)

@Preview
@Composable
private fun LocalizedClickableTextPreview() {
    LocalizedClickableText(
        text = "My template",
        linkTexts = listOf(
            LinkText(
                text = "template",
                url = "https://github.com/seve-andre/jetpack-compose-template",
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
            )
        )
    )
}
