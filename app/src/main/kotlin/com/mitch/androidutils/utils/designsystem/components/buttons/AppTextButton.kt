package com.mitch.androidutils.utils.designsystem.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.mitch.androidutils.utils.designsystem.AppIconResource
import com.mitch.androidutils.utils.designsystem.components.buttons.util.AppButtonContent
import com.mitch.androidutils.utils.designsystem.components.buttons.util.AppButtonDefaults
import com.mitch.androidutils.utils.designsystem.components.buttons.util.ButtonIcon
import com.mitch.androidutils.utils.designsystem.components.buttons.util.TextButtonColors
import com.mitch.androidutils.utils.designsystem.components.buttons.util.buttonPaddingValues
import com.mitch.androidutils.utils.designsystem.components.icon.AppIcon
import com.mitch.androidutils.utils.designsystem.components.text.AppText

@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ButtonIcon? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: TextButtonColors = AppButtonDefaults.textButtonColors(),
    contentPadding: PaddingValues = buttonPaddingValues(icon),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: @Composable () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = colors.foreground,
            disabledContentColor = colors.disabledForeground
        ),
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        AppButtonContent(text = text, icon = icon)
    }
}

@Preview
@Composable
private fun AppTextButtonPreview() {
    MaterialTheme {
        Column {
            AppTextButton(onClick = { }) {
                AppText(text = "Ciao")
            }
            AppTextButton(
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
