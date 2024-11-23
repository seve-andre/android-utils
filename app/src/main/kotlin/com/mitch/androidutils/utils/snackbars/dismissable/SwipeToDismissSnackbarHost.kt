package com.mitch.androidutils.utils.snackbars.dismissable

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
private fun SwipeToDismissSnackbarHost(hostState: SnackbarHostState) {
    val dismissSnackbarState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value != SwipeToDismissBoxValue.Settled) {
                hostState.currentSnackbarData?.dismiss()
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(dismissSnackbarState.currentValue) {
        if (dismissSnackbarState.currentValue != SwipeToDismissBoxValue.Settled) {
            dismissSnackbarState.reset()
        }
    }

    SwipeToDismissBox(
        state = dismissSnackbarState,
        backgroundContent = { },
        content = {
            SnackbarHost(
                hostState = hostState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = /*choose padding*/)
            ) { snackbarData ->
                val customVisuals = snackbarData.visuals as /*YourCustomSnackbarVisuals*/
            }
        }
    )
}
