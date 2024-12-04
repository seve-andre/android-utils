package com.mitch.androidutils.utils.formatting

import android.icu.text.ListFormatter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mitch.androidutils.utils.locale.getLocale
import java.util.Locale

@Composable
fun <T> List<T>.formatted(
    locale: Locale = getLocale(),
    joiner: ListJoiner = ListJoiner.And
): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.androidListFormatter(locale, joiner)
    } else {
        this.toString()
    }
}

enum class ListJoiner {
    And, Or
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun <T> List<T>.androidListFormatter(
    locale: Locale,
    joiner: ListJoiner = ListJoiner.And
): String {
    val formatterType = when (joiner) {
        ListJoiner.And -> ListFormatter.Type.AND
        ListJoiner.Or -> ListFormatter.Type.OR
    }
    val formatter = ListFormatter.getInstance(locale, formatterType, ListFormatter.Width.WIDE)
    return formatter.format(this)
}

@Preview
@Composable
private fun ListFormatterAndPreview() {
    val authors = listOf(
        "Andrea Severi",
        "Cristina Iannuzzi",
        "Matteo Lauria",
        "Lorenzo Vergani"
    )
    Text(text = authors.formatted())
}

@Preview
@Composable
private fun ListFormatterOrPreview() {
    val authors = listOf(
        "Andrea Severi",
        "Cristina Iannuzzi",
        "Matteo Lauria",
        "Lorenzo Vergani"
    )
    Text(text = authors.formatted(joiner = ListJoiner.Or))
}
