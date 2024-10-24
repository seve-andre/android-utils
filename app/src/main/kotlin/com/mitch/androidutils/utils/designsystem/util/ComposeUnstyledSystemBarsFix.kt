package com.mitch.androidutils.utils.designsystem.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.LifecycleStartEffect
import com.composables.core.LocalModalWindow
import com.mitch.androidutils.ui.theme.isThemeLight
import com.mitch.androidutils.utils.activity.findActivity

@Composable
fun ComposeUnstyledSystemBarsFix() {
    // Activity
    val view = LocalView.current
    val activityWindow = view.context.findActivity()?.window ?: return
    val activityInsetsController = remember {
        WindowInsetsControllerCompat(activityWindow, view)
    }
    val wereStatusBarIconsDark = activityInsetsController.isAppearanceLightStatusBars
    val wereNavigationBarIconsDark = activityInsetsController.isAppearanceLightNavigationBars

    // Modal window
    val modalWindow = LocalModalWindow.current
    val modalInsetsController = remember {
        WindowInsetsControllerCompat(modalWindow, modalWindow.decorView)
    }

    if (!view.isInEditMode) {
        val isThemeLight = MaterialTheme.isThemeLight()

        LifecycleStartEffect(isThemeLight) {
            modalWindow.statusBarColor = Color.Transparent.toArgb()
            modalWindow.navigationBarColor = Color.Transparent.toArgb()
            modalInsetsController.isAppearanceLightStatusBars = isThemeLight
            modalInsetsController.isAppearanceLightNavigationBars = isThemeLight

            onStopOrDispose {
                if (wereStatusBarIconsDark != activityInsetsController.isAppearanceLightStatusBars) {
                    activityInsetsController.isAppearanceLightStatusBars = wereStatusBarIconsDark
                }
                if (wereNavigationBarIconsDark != activityInsetsController.isAppearanceLightNavigationBars) {
                    activityInsetsController.isAppearanceLightNavigationBars =
                        wereNavigationBarIconsDark
                }
            }
        }
    }
}
