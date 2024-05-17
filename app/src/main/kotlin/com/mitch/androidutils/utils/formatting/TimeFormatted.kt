package com.mitch.androidutils.utils.formatting

import androidx.compose.runtime.Composable
import com.mitch.androidutils.utils.locale.getLocale
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@Composable
fun timeFormatted(
    instant: Instant,
    style: FormatStyle = FormatStyle.MEDIUM,
    locale: Locale = getLocale()
): String {
    return DateTimeFormatter
        .ofLocalizedTime(style)
        .withLocale(locale)
        .withZone(ItalianTimeZone.toJavaZoneId())
        .format(instant.toJavaInstant())
}
