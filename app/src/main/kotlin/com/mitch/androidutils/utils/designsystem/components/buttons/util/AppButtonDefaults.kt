package com.mitch.androidutils.utils.designsystem.components.buttons.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object AppButtonDefaults {
    val outlinedBorderWidth = 1.dp

    @Composable
    fun filledButtonColors(
        background: Color = MaterialTheme.colorScheme.primary,
        foreground: Color = MaterialTheme.colorScheme.onPrimary,
        disabledBackground: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
        disabledForeground: Color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f)
    ): FilledButtonColors {
        return FilledButtonColors(
            background = background,
            foreground = foreground,
            disabledBackground = disabledBackground,
            disabledForeground = disabledForeground
        )
    }

    @Composable
    fun outlinedButtonColors(
        border: Color = MaterialTheme.colorScheme.primary,
        foreground: Color = MaterialTheme.colorScheme.primary,
        disabledBorder: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
        disabledForeground: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    ): OutlinedButtonColors {
        return OutlinedButtonColors(
            border = border,
            foreground = foreground,
            disabledBorder = disabledBorder,
            disabledForeground = disabledForeground
        )
    }

    @Composable
    fun textButtonColors(
        foreground: Color = MaterialTheme.colorScheme.primary,
        disabledForeground: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    ): TextButtonColors {
        return TextButtonColors(
            foreground = foreground,
            disabledForeground = disabledForeground
        )
    }
}

data class FilledButtonColors(
    val background: Color,
    val foreground: Color,
    val disabledBackground: Color,
    val disabledForeground: Color
)

data class OutlinedButtonColors(
    val border: Color,
    val foreground: Color,
    val disabledBorder: Color,
    val disabledForeground: Color
)

data class TextButtonColors(
    val foreground: Color,
    val disabledForeground: Color
)
