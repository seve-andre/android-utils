package com.mitch.androidutils.utils.designsystem.components.dialogs

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.composables.core.DialogProperties
import com.mitch.androidutils.utils.designsystem.util.ComposeUnstyledSystemBarsFix

@Composable
fun AppFullScreenDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    foregroundColor: Color = MaterialTheme.colorScheme.onSurface,
    shape: Shape = RectangleShape,
    enterTransition: EnterTransition = slideInVertically(initialOffsetY = { it / 2 }),
    exitTransition: ExitTransition = slideOutVertically(targetOffsetY = { it / 2 }),
    content: @Composable () -> Unit
) {
    ComposeUnstyledDialog(
        onDismiss = onDismiss,
        useScrim = false,
        modifier = modifier
            .displayCutoutPadding()
            .systemBarsPadding()
            .fillMaxSize(),
        properties = properties,
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        shape = shape,
        enterTransition = enterTransition,
        exitTransition = exitTransition
    ) {
        ComposeUnstyledSystemBarsFix()
        content()
    }
}
