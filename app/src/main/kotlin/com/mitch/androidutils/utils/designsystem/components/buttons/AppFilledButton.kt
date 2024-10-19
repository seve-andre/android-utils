package com.mitch.androidutils.utils.designsystem.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.mitch.androidutils.utils.designsystem.AppIconResource
import com.mitch.androidutils.utils.designsystem.components.buttons.util.AppButtonContent
import com.mitch.androidutils.utils.designsystem.components.buttons.util.AppButtonDefaults
import com.mitch.androidutils.utils.designsystem.components.buttons.util.ButtonIcon
import com.mitch.androidutils.utils.designsystem.components.buttons.util.FilledButtonColors
import com.mitch.androidutils.utils.designsystem.components.buttons.util.GradientButton
import com.mitch.androidutils.utils.designsystem.components.buttons.util.buttonPaddingValues
import com.mitch.androidutils.utils.designsystem.components.icon.AppIcon
import com.mitch.androidutils.utils.designsystem.components.text.AppText

@Composable
fun AppFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ButtonIcon? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: FilledButtonColors = AppButtonDefaults.filledButtonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    contentPadding: PaddingValues = buttonPaddingValues(icon),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.background,
            contentColor = colors.foreground
        ),
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        AppButtonContent(text = text, icon = icon)
    }
}

@Composable
fun AppFilledButton(
    onClick: () -> Unit,
    brush: Brush,
    modifier: Modifier = Modifier,
    icon: ButtonIcon? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: FilledButtonColors = AppButtonDefaults.filledButtonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    contentPadding: PaddingValues = buttonPaddingValues(icon),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: @Composable () -> Unit
) {
    GradientButton(
        onClick = onClick,
        brush = brush,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.background,
            contentColor = colors.foreground
        ),
        elevation = elevation,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        AppButtonContent(text = text, icon = icon)
    }
}

@PreviewLightDark
@Composable
private fun AppButtonColorPreview() {
    MaterialTheme {
        Column {
            AppFilledButton(onClick = { }) {
                AppText("Ciao")
            }
            val gradientColors = listOf(Color.Cyan, Color.Blue)
            AppFilledButton(onClick = { }, brush = Brush.linearGradient(gradientColors)) {
                AppText("Ciao")
            }
        }
    }
}

@PreviewLightDark
@Preview(locale = "ar")
@Composable
private fun AppButtonIconsPreview() {
    MaterialTheme {
        Column {
            AppFilledButton(
                onClick = { },
                icon = ButtonIcon.LeadingIcon {
                    AppIcon(icon = AppIconResource.Add, contentDescription = null)
                }
            ) {
                AppText("Ciao")
            }

            AppFilledButton(
                onClick = { },
                icon = ButtonIcon.TrailingIcon {
                    AppIcon(icon = AppIconResource.Add, contentDescription = null)
                }
            ) {
                AppText("Ciao")
            }
        }
    }
}
