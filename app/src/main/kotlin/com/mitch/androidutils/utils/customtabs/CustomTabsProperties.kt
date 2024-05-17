package com.mitch.androidutils.utils.customtabs

import android.app.PendingIntent
import androidx.annotation.AnimRes
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.browser.customtabs.CustomTabsIntent
import java.util.Locale

data class CustomTabProperties(
    val toolbar: Toolbar,
    val icons: Icons = Icons(),
    val locale: Locale? = null,
    val enableInteraction: Boolean = true,
    val referrer: String? = null,
    val openLinksInApp: Boolean = true,
    val animations: Animations = Animations(),
    val enableInstantApps: Boolean = true,
    val actionButton: ActionButton? = null,
    val additionalMenuItems: List<MenuItemInfo> = emptyList(),
    val partialProperties: PartialProperties? = null,
    val shareIdentity: Boolean = false
) {
    data class Toolbar(
        @ColorInt val backgroundColorInt: Int,
        val showTitle: Boolean = true,
        val hideOnScroll: Boolean = false,
        @Dimension(unit = Dimension.DP) val cornerRadiusDp: Int? = null
    )

    data class Icons(
        val closeButton: CustomTabCloseButton = CustomTabCloseButton(),
        val shareButtonVisibility: CustomTabShareVisibility = CustomTabShareVisibility.Default,
        val showBookmarksButton: Boolean = true,
        val showDownloadButton: Boolean = true,
    )

    data class Animations(
        val startAnimation: Animation = Animation(),
        val exitAnimation: Animation = Animation()
    )

    data class Animation(
        @AnimRes val enterAnimationId: Int? = null,
        @AnimRes val exitAnimationId: Int? = null
    )

    data class ActionButton(
        @DrawableRes val iconId: Int,
        val description: String,
        val pendingIntent: PendingIntent
    )

    data class MenuItemInfo(
        val label: String,
        val pendingIntent: PendingIntent
    )

    sealed interface PartialProperties {
        data class BottomSheet(
            /**
             * The minimum partial custom tab height is 50% of the screen height.
             * If the screen height is set to a value less than 50% of the screen height,
             * Chrome automatically adjusts the Custom Tab to 50% of the screen height.
             */
            @Dimension(unit = Dimension.PX) val heightPx: Int,
            val resizable: Boolean = true
        ) : PartialProperties

        data class SideSheet(
            @Dimension(unit = Dimension.PX) val heightPx: Int,
            @Dimension(unit = Dimension.PX) val widthPx: Int,
            val resizable: Boolean = true,
            @Dimension(unit = Dimension.DP) val breakpointDp: Int,
            val decorationType: DecorationType = DecorationType.Default,
            val enableMaximization: Boolean = false,
            val position: Position = Position.Default,
            val roundedCornersPosition: RoundedCornersPosition = RoundedCornersPosition.Default
        ) : PartialProperties {
            enum class DecorationType {
                Default, None, Shadow, Divider;

                fun toApiDecorationType(): Int {
                    return when (this) {
                        Default -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_DECORATION_TYPE_NONE
                        None -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_DECORATION_TYPE_NONE
                        Shadow -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_DECORATION_TYPE_SHADOW
                        Divider -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_DECORATION_TYPE_DIVIDER
                    }
                }
            }

            enum class Position {
                Default, Start, End;

                fun toApiPosition(): Int {
                    return when (this) {
                        Default -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_POSITION_DEFAULT
                        Start -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_POSITION_START
                        End -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_POSITION_END
                    }
                }
            }

            enum class RoundedCornersPosition {
                Default,

                None,

                /**
                 * Side sheet with the inner top corner rounded
                 * (if positioned on the right of the screen, this will be the top left corner)
                 */
                External;

                fun toApiRoundedCornersPosition(): Int {
                    return when (this) {
                        Default -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_ROUNDED_CORNERS_POSITION_DEFAULT
                        None -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_ROUNDED_CORNERS_POSITION_NONE
                        External -> CustomTabsIntent.ACTIVITY_SIDE_SHEET_ROUNDED_CORNERS_POSITION_TOP
                    }
                }
            }
        }
    }
}
