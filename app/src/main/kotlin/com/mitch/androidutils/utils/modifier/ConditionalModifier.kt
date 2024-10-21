package com.mitch.androidutils.utils.modifier

import androidx.compose.ui.Modifier

fun Modifier.applyIf(
    condition: Boolean,
    onTrue: Modifier.() -> Modifier,
    onFalse: Modifier.() -> Modifier = { Modifier }
): Modifier {
    return this.then(
        if (condition) {
            onTrue()
        } else {
            onFalse()
        }
    )
}
