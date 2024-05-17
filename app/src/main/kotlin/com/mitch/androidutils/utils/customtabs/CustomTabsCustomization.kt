package com.mitch.androidutils.utils.customtabs

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.graphics.drawable.toBitmap

data class CustomTabCloseButton(
    @DrawableRes val closeIconId: Int? = null,
    val position: Position = Position.Default
) {
    enum class Position {
        Default, Start, End;

        fun toApiPosition(): Int {
            return when (this) {
                Default -> CustomTabsIntent.CLOSE_BUTTON_POSITION_DEFAULT
                Start -> CustomTabsIntent.CLOSE_BUTTON_POSITION_START
                End -> CustomTabsIntent.CLOSE_BUTTON_POSITION_END
            }
        }
    }
}

enum class CustomTabShareVisibility {
    Default, On, Off;

    fun toApiVisibility(): Int {
        return when (this) {
            Default -> CustomTabsIntent.SHARE_STATE_DEFAULT
            On -> CustomTabsIntent.SHARE_STATE_ON
            Off -> CustomTabsIntent.SHARE_STATE_OFF
        }
    }
}

fun CustomTabsIntent.Builder.toolbar(
    properties: CustomTabProperties.Toolbar
): CustomTabsIntent.Builder {
    val colorSchemeParams = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(properties.backgroundColorInt)
        .build()

    return this.apply {
        setShowTitle(properties.showTitle)
        setUrlBarHidingEnabled(properties.hideOnScroll)
        setDefaultColorSchemeParams(colorSchemeParams)
        if (properties.cornerRadiusDp != null) setToolbarCornerRadiusDp(properties.cornerRadiusDp)
    }
}

fun CustomTabsIntent.Builder.icons(
    properties: CustomTabProperties.Icons,
    context: Context
): CustomTabsIntent.Builder {
    val bitmapIcon = properties.closeButton.closeIconId?.let {
        AppCompatResources.getDrawable(context, it)?.toBitmap()
    }

    return this.apply {
        if (bitmapIcon != null) setCloseButtonIcon(bitmapIcon)
        setCloseButtonPosition(properties.closeButton.position.toApiPosition())
        setShareState(properties.shareButtonVisibility.toApiVisibility())
        setBookmarksButtonEnabled(properties.showBookmarksButton)
        setDownloadButtonEnabled(properties.showDownloadButton)
    }
}

fun CustomTabsIntent.Builder.animations(
    properties: CustomTabProperties.Animations,
    context: Context
): CustomTabsIntent.Builder {
    val (startEnterId, startExitId) = properties.startAnimation
    val (exitEnterId, exitExitId) = properties.exitAnimation

    return this.apply {
        if (startEnterId != null && startExitId != null) setStartAnimations(context, startEnterId, startExitId)
        if (exitEnterId != null && exitExitId != null) setStartAnimations(context, exitEnterId, exitExitId)
    }
}

fun CustomTabsIntent.Builder.actionButton(
    actionButton: CustomTabProperties.ActionButton,
    context: Context
): CustomTabsIntent.Builder {
    val bitmapIcon = AppCompatResources.getDrawable(context, actionButton.iconId)?.toBitmap()

    return this.apply {
        if (bitmapIcon != null) setActionButton(bitmapIcon, actionButton.description, actionButton.pendingIntent)
    }
}

fun CustomTabsIntent.Builder.menuItems(
    menuItems: List<CustomTabProperties.MenuItemInfo>
): CustomTabsIntent.Builder {
    return this.apply {
        menuItems.forEach {
            addMenuItem(it.label, it.pendingIntent)
        }
    }
}

fun CustomTabsIntent.Builder.partial(
    properties: CustomTabProperties.PartialProperties,
): CustomTabsIntent.Builder {
    return this.apply {
        when (properties) {
            is CustomTabProperties.PartialProperties.BottomSheet -> {
                val resizeBehavior = if (properties.resizable) {
                    CustomTabsIntent.ACTIVITY_HEIGHT_ADJUSTABLE
                } else {
                    CustomTabsIntent.ACTIVITY_HEIGHT_FIXED
                }

                setInitialActivityHeightPx(properties.heightPx, resizeBehavior)
            }
            is CustomTabProperties.PartialProperties.SideSheet -> {
                val resizeBehavior = if (properties.resizable) {
                    CustomTabsIntent.ACTIVITY_HEIGHT_ADJUSTABLE
                } else {
                    CustomTabsIntent.ACTIVITY_HEIGHT_FIXED
                }

                setInitialActivityHeightPx(properties.heightPx, resizeBehavior)
                setInitialActivityWidthPx(properties.widthPx)
                setActivitySideSheetBreakpointDp(properties.breakpointDp)
                setActivitySideSheetDecorationType(properties.decorationType.toApiDecorationType())
                setActivitySideSheetMaximizationEnabled(properties.enableMaximization)
                setActivitySideSheetPosition(properties.position.toApiPosition())
                setActivitySideSheetRoundedCornersPosition(properties.roundedCornersPosition.toApiRoundedCornersPosition())
            }
        }
    }
}
