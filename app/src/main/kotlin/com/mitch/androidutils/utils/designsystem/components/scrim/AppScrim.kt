package com.mitch.androidutils.utils.designsystem.components.scrim

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.composables.core.DialogScope
import com.composables.core.ModalBottomSheetScope
import com.composables.core.Scrim

@Composable
fun DialogScope.AppScrim() {
    Scrim(scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = ScrimAlpha))
}

@Composable
fun ModalBottomSheetScope.AppScrim() {
    Scrim(scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = ScrimAlpha))
}

private const val ScrimAlpha = 0.32f
