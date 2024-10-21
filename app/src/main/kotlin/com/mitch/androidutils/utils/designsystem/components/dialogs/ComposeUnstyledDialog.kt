package com.mitch.androidutils.utils.designsystem.components.dialogs

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import com.composables.core.Dialog
import com.composables.core.DialogPanel
import com.composables.core.DialogProperties
import com.composables.core.rememberDialogState
import com.mitch.androidutils.utils.designsystem.components.scrim.AppScrim
import androidx.compose.material3.R as Material3R

@Composable
fun ComposeUnstyledDialog(
    onDismiss: () -> Unit,
    useScrim: Boolean,
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
    val currentOnDismiss by rememberUpdatedState(onDismiss)
    val state = rememberDialogState(initiallyVisible = true)
    val a11yDialogTitle = stringResource(id = Material3R.string.m3c_dialog)

    LaunchedEffect(state.visible) {
        if (!state.visible) {
            currentOnDismiss()
        }
    }

    Dialog(state = state, properties = properties) {
        if (useScrim) {
            AppScrim()
        }
        DialogPanel(
            modifier = modifier
                .clip(shape)
                .then(
                    if (border != null) {
                        Modifier.border(border = border, shape = shape)
                    } else {
                        Modifier
                    }
                )
                .background(backgroundColor)
                .semantics {
                    paneTitle = a11yDialogTitle
                },
            enter = enterTransition,
            exit = exitTransition
        ) {
            CompositionLocalProvider(LocalContentColor provides foregroundColor) {
                content()
            }
        }
    }
}
