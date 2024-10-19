package com.mitch.androidutils.utils.designsystem.components.buttons.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import com.mitch.androidutils.utils.designsystem.util.AppLayoutId
import com.mitch.androidutils.utils.designsystem.util.RequireLayoutId

@Composable
fun RowScope.AppButtonContent(text: @Composable () -> Unit, icon: ButtonIcon?) {
    if (icon != null) {
        RequireLayoutId(
            layoutId = AppLayoutId.AppIconId,
            errorMessage = "Only AppIcon is allowed here.",
            content = icon.content
        )
    }
    RequireLayoutId(
        layoutId = AppLayoutId.AppTextId,
        errorMessage = "Only AppText is allowed here.",
        content = text
    )

    when (icon) {
        is ButtonIcon.LeadingIcon -> AppButtonLeadingIconContent(leadingIcon = icon.content, text = text)
        is ButtonIcon.TrailingIcon -> AppButtonTrailingIconContent(text = text, trailingIcon = icon.content)
        else -> text()
    }
}

@Composable
private fun RowScope.AppButtonLeadingIconContent(
    leadingIcon: @Composable () -> Unit,
    text: @Composable () -> Unit
) {
    Box(modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
        leadingIcon()
    }
    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
    text()
}

@Composable
private fun RowScope.AppButtonTrailingIconContent(
    text: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit
) {
    text()
    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
    Box(modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
        trailingIcon()
    }
}

@Composable
fun buttonPaddingValues(icon: ButtonIcon?): PaddingValues {
    return when (icon) {
        is ButtonIcon.LeadingIcon -> ButtonDefaults.ButtonWithIconContentPadding

        is ButtonIcon.TrailingIcon -> PaddingValues(
            start = ButtonDefaults.ContentPadding.calculateStartPadding(LocalLayoutDirection.current),
            top = ButtonDefaults.ContentPadding.calculateTopPadding(),
            end = ButtonDefaults.ButtonWithIconContentPadding.calculateStartPadding(LocalLayoutDirection.current),
            bottom = ButtonDefaults.ContentPadding.calculateBottomPadding()
        )

        else -> ButtonDefaults.ContentPadding
    }
}

sealed class ButtonIcon(open val content: @Composable () -> Unit) {
    data class LeadingIcon(
        override val content: @Composable () -> Unit
    ) : ButtonIcon(content = content)

    data class TrailingIcon(
        override val content: @Composable () -> Unit
    ) : ButtonIcon(content = content)
}
