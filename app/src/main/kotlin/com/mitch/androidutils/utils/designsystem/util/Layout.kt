package com.mitch.androidutils.utils.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId

@Composable
fun RequireLayoutId(
    layoutId: Any?,
    errorMessage: String = "Failed requirement.",
    content: @Composable () -> Unit
) = Layout(content) { measurables, constraints ->
    val child = measurables.singleOrNull()
        ?: error("Only a single child is allowed, was: ${measurables.size}")

    // read layoutId of a single child
    require(child.layoutId == layoutId) { errorMessage }

    // do not actually measure or layout a child
    layout(0, 0) { }
}

object AppLayoutId {
    data object AppTextId
    data object AppIconId
}
