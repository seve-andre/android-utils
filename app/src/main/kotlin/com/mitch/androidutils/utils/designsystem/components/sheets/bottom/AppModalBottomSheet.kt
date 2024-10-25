package com.mitch.androidutils.utils.designsystem.components.sheets.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.core.DragIndication
import com.composables.core.ModalBottomSheet
import com.composables.core.ModalSheetProperties
import com.composables.core.Sheet
import com.composables.core.SheetDetent
import com.composables.core.rememberModalBottomSheetState
import com.mitch.androidutils.utils.designsystem.components.scrim.AppScrim
import com.mitch.androidutils.utils.designsystem.util.ComposeUnstyledSystemBarsFix
import kotlinx.coroutines.launch
import androidx.compose.material3.R as Material3R

@Composable
fun AppModalBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    properties: ModalSheetProperties = ModalSheetProperties(),
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
    val a11yBottomSheetTitle = stringResource(id = Material3R.string.m3c_bottom_sheet_pane_title)
    val a11yDragHandleDescription =
        stringResource(id = Material3R.string.m3c_bottom_sheet_drag_handle_description)
    val scope = rememberCoroutineScope()
    val animateToDismiss: () -> Unit = {
        scope.launch {
            sheetState.animateTo(SheetDetent.Hidden)
        }
    }

    LaunchedEffect(sheetState.currentDetent) {
        if (sheetState.currentDetent == SheetDetent.Hidden) {
            currentOnDismiss()
        }
    }

    ModalBottomSheet(state = sheetState, properties = properties) {
        if (useScrim) {
            AppScrim(onClose = animateToDismiss, modifier = Modifier.fillMaxSize())
        }
        Sheet(
            modifier = modifier
                .displayCutoutPadding()
                .statusBarsPadding()
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
                .semantics { paneTitle = a11yBottomSheetTitle }
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
                        .semantics { contentDescription = a11yDragHandleDescription }
                )

                ComposeUnstyledSystemBarsFix()
                CompositionLocalProvider(LocalContentColor provides foregroundColor) {
                    content()
                }
            }
        }
    }
}
