package com.mitch.androidutils.utils.edgetoedge

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.mitch.androidutils.utils.activity.findActivity

@Composable
fun SetSystemBarsColor(color: SystemBarsColor) {
    val view = LocalView.current
    val activity = view.context.findActivity()
    val window = activity?.window ?: return
    val insetsController = WindowInsetsControllerCompat(window, view)
    val wereStatusBarIconsDark = insetsController.isAppearanceLightStatusBars
    val wereNavigationBarIconsDark = insetsController.isAppearanceLightStatusBars
    if (!view.isInEditMode) {
        when (color) {
            is SystemBarsColor.Both -> {
                LifecycleEventEffect(Lifecycle.Event.ON_START) {
                    insetsController.isAppearanceLightStatusBars = !color.statusDarkIcons
                    insetsController.isAppearanceLightNavigationBars = !color.navigationDarkIcons
                }
                LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
                    insetsController.isAppearanceLightStatusBars = wereStatusBarIconsDark
                    insetsController.isAppearanceLightNavigationBars = wereNavigationBarIconsDark
                }
            }

            is SystemBarsColor.NavigationBar -> {
                LifecycleEventEffect(Lifecycle.Event.ON_START) {
                    insetsController.isAppearanceLightNavigationBars = !color.darkIcons
                }
                LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
                    insetsController.isAppearanceLightNavigationBars = wereNavigationBarIconsDark
                }
            }

            is SystemBarsColor.StatusBar -> {
                LifecycleEventEffect(Lifecycle.Event.ON_START) {
                    insetsController.isAppearanceLightStatusBars = !color.darkIcons
                }
                LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
                    insetsController.isAppearanceLightStatusBars = wereStatusBarIconsDark
                }
            }
        }
    }
}

sealed interface SystemBarsColor {
    data class Both(val statusDarkIcons: Boolean, val navigationDarkIcons: Boolean) : SystemBarsColor
    data class StatusBar(val darkIcons: Boolean) : SystemBarsColor
    data class NavigationBar(val darkIcons: Boolean) : SystemBarsColor
}
