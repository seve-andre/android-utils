package com.mitch.androidutils.utils.designsystem.components.scrim

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.mitch.androidutils.R

@Composable
fun AppScrim(onClose: () -> Unit, modifier: Modifier = Modifier) {
    val a11Close = stringResource(id = R.string.close)
    Box(
        modifier = modifier
            .pointerInput(onClose) { detectTapGestures(onTap = { onClose() }) }
            .semantics(mergeDescendants = true) {
                contentDescription = a11Close
                onClick {
                    onClose()
                    true
                }
            }
            .onKeyEvent {
                if (it.key == Key.Escape) {
                    onClose()
                    true
                } else {
                    false
                }
            }
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = ScrimAlpha))
    )
}

private const val ScrimAlpha = 0.32f
