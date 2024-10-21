package com.mitch.androidutils.utils.designsystem.components.dialogs

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composables.core.DialogProperties

@Composable
fun AppDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    foregroundColor: Color = MaterialTheme.colorScheme.onSurface,
    shape: Shape = MaterialTheme.shapes.medium,
    border: BorderStroke? = null,
    enterTransition: EnterTransition = slideInVertically(),
    exitTransition: ExitTransition = slideOutVertically(),
    content: @Composable () -> Unit
) {
    ComposeUnstyledDialog(
        onDismiss = onDismiss,
        useScrim = true,
        modifier = modifier
            .systemBarsPadding()
            .widthIn(min = 280.dp, max = 560.dp),
        properties = properties,
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        shape = shape,
        border = border,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        content = content
    )
}
