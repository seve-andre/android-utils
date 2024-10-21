package com.mitch.androidutils.utils.designsystem.components.sheets.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.composables.core.DragIndication
import com.composables.core.LocalModalWindow
import com.composables.core.ModalBottomSheet
import com.composables.core.Scrim
import com.composables.core.Sheet
import com.composables.core.SheetDetent
import com.composables.core.rememberModalBottomSheetState
import com.mitch.androidutils.ui.theme.isThemeLight
import com.mitch.androidutils.utils.activity.findActivity

@Composable
fun AppModalBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    foregroundColor: Color = MaterialTheme.colorScheme.onSurface,
    shadowElevation: Dp = 4.dp,
    dragHandleDetails: DragHandleDetails = DragHandleDetails.default(),
    useScrim: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val currentOnDismiss by rememberUpdatedState(onDismiss)
    val sheetState = rememberModalBottomSheetState(initialDetent = SheetDetent.FullyExpanded)

    LaunchedEffect(sheetState.currentDetent) {
        if (sheetState.currentDetent == SheetDetent.Hidden) {
            currentOnDismiss()
        }
    }

    ModalBottomSheet(state = sheetState) {
        if (useScrim) {
            Scrim()
        }
        Sheet(
            modifier = modifier
                .shadow(shadowElevation, shape)
                .clip(shape)
                .widthIn(max = 640.dp) // m3 max width on larger screens
                .fillMaxWidth()
                .padding(
                    // make sure the sheet is not behind nav bars on landscape
                    WindowInsets.navigationBars
                        .only(WindowInsetsSides.Horizontal)
                        .asPaddingValues()
                )
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .heightIn(min = 128.dp)
            ) {
                DragIndication(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 22.dp)
                        .background(dragHandleDetails.color, dragHandleDetails.shape)
                        .width(dragHandleDetails.width)
                        .height(dragHandleDetails.height)
                )

                FixNavigationBar()
                CompositionLocalProvider(LocalContentColor provides foregroundColor) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun FixNavigationBar() {
    // Activity
    val view = LocalView.current
    val activityWindow = view.context.findActivity()?.window ?: return
    val activityInsetsController = WindowInsetsControllerCompat(activityWindow, view)

    // BottomSheet
    val modalWindow = LocalModalWindow.current
    val modalInsetsController = WindowInsetsControllerCompat(modalWindow, view)
    val wereNavigationBarIconsDark = modalInsetsController.isAppearanceLightStatusBars

    if (!view.isInEditMode) {
        val isThemeLight = MaterialTheme.isThemeLight()

        LifecycleEventEffect(Lifecycle.Event.ON_START) {
            modalWindow.navigationBarColor = Color.Transparent.toArgb()
            modalInsetsController.isAppearanceLightNavigationBars = isThemeLight
        }
        LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
            activityInsetsController.isAppearanceLightNavigationBars = wereNavigationBarIconsDark
        }
    }
}
