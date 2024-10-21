package com.mitch.androidutils.utils.designsystem.components.sheets.bottom

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DragHandleDetails(
    val color: Color,
    val shape: Shape,
    val width: Dp,
    val height: Dp
) {
    companion object {
        @Composable
        fun default(): DragHandleDetails {
            return DragHandleDetails(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(100),
                width = 32.dp,
                height = 4.dp
            )
        }
    }
}
