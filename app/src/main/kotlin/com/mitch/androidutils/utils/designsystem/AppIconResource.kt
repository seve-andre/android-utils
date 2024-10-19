package com.mitch.androidutils.utils.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppIconResource(val imageVectorProvider: @Composable () -> ImageVector) {
    data object Add : AppIconResource(imageVectorProvider = { Icons.Default.Add })
}
