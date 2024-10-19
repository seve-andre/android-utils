package com.mitch.androidutils.utils.designsystem.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.mitch.androidutils.utils.designsystem.AppIconResource
import com.mitch.androidutils.utils.designsystem.components.buttons.util.AppButtonContent
import com.mitch.androidutils.utils.designsystem.components.buttons.util.AppButtonDefaults
import com.mitch.androidutils.utils.designsystem.components.buttons.util.ButtonIcon
import com.mitch.androidutils.utils.designsystem.components.buttons.util.OutlinedButtonColors
import com.mitch.androidutils.utils.designsystem.components.buttons.util.buttonPaddingValues
import com.mitch.androidutils.utils.designsystem.components.icon.AppIcon
import com.mitch.androidutils.utils.designsystem.components.text.AppText

@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ButtonIcon? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: OutlinedButtonColors = AppButtonDefaults.outlinedButtonColors(),
    borderWidth: Dp = AppButtonDefaults.outlinedBorderWidth,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    contentPadding: PaddingValues = buttonPaddingValues(icon),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: @Composable () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = colors.foreground,
            disabledContentColor = colors.disabledForeground
        ),
        elevation = elevation,
        border = BorderStroke(
            width = borderWidth,
            color = if (enabled) colors.border else colors.disabledBorder
        ),
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        AppButtonContent(text = text, icon = icon)
    }
}

@Preview
@Composable
private fun AppOutlinedButtonPreview() {
    MaterialTheme {
        Column {
            AppOutlinedButton(onClick = { }) {
                AppText(text = "Ciao")
            }
            AppOutlinedButton(
                onClick = { },
                icon = ButtonIcon.LeadingIcon {
                    AppIcon(icon = AppIconResource.Add, contentDescription = null)
                }
            ) {
                AppText(text = "Ciao")
            }
        }
    }
}
