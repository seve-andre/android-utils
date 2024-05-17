package com.mitch.androidutils.utils.formatting

import android.icu.text.RelativeDateTimeFormatter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.mitch.androidutils.utils.locale.getLocale
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.time.format.FormatStyle
import java.util.Locale

@Composable
fun relativeTimeFormatted(
    instant: Instant,
    style: FormatStyle = FormatStyle.SHORT,
    locale: Locale = getLocale(),
    useRelative: Boolean = true
): String {
    @RequiresApi(Build.VERSION_CODES.N)
    @Composable
    fun kotlin.time.Duration.formatRelative(): String {
        // 0s <= x < 60s -> "now"
        // 60s (1min) <= x < 60min (1h) -> "x minutes"
        // 1h <= x < 24h (1day) -> "x hours"
        // else -> date
        return when {
            this.isLessThanAMinute() -> formatNow()

            this.isLessThanAnHour() -> formatAgo(
                time = this.inWholeMinutes,
                unit = RelativeDateTimeFormatter.RelativeUnit.MINUTES
            )

            this.isLessThanADay() -> formatAgo(
                time = this.inWholeHours,
                unit = RelativeDateTimeFormatter.RelativeUnit.HOURS
            )

            else -> dateFormatted(instant = instant, style = style, locale = locale)
        }
    }

    val difference = Clock.System.now() - instant
    return if (useRelative && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        difference.formatRelative()
    } else {
        if (difference.isLessThanADay()) {
            timeFormatted(instant = instant, style = style, locale = locale)
        } else {
            dateFormatted(instant = instant, style = style, locale = locale)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
private fun formatNow(): String {
    return RelativeDateTimeFormatter.getInstance(getLocale()).format(
        RelativeDateTimeFormatter.Direction.PLAIN,
        RelativeDateTimeFormatter.AbsoluteUnit.NOW
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
private fun formatAgo(time: Long, unit: RelativeDateTimeFormatter.RelativeUnit): String {
    return RelativeDateTimeFormatter.getInstance(getLocale()).format(
        time.toDouble(),
        RelativeDateTimeFormatter.Direction.LAST,
        unit
    )
}

private val SecondsOpenRange = 0..<60
private val MinutesOpenRange = 0..<60
private val HoursOpenRange = 0..<24
fun kotlin.time.Duration.isLessThanAMinute(): Boolean = this.inWholeSeconds in SecondsOpenRange
fun kotlin.time.Duration.isLessThanAnHour(): Boolean = this.inWholeMinutes in MinutesOpenRange
fun kotlin.time.Duration.isLessThanADay(): Boolean = this.inWholeHours in HoursOpenRange
