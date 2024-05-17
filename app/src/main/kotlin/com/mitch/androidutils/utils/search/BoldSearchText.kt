package com.mitch.androidutils.utils.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun boldSearchText(
    fullText: String,
    searchQuery: String
) = buildAnnotatedString {
    var start = 0
    while (fullText.indexOf(
            searchQuery,
            start,
            ignoreCase = true
        ) != -1 && searchQuery.isNotBlank()
    ) {
        val firstIndex =
            fullText.indexOf(
                searchQuery,
                start,
                true
            )
        val end =
            firstIndex + searchQuery.length
        append(
            fullText.substring(
                start,
                firstIndex
            )
        )
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.Bold
            )
        ) {
            append(
                fullText.substring(
                    firstIndex,
                    end
                )
            )
        }
        start = end
    }
    append(
        fullText.substring(
            start,
            fullText.length
        )
    )
    toAnnotatedString()
}
