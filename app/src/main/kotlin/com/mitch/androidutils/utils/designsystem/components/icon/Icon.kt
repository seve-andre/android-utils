package com.mitch.androidutils.utils.designsystem.components.icon

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import com.mitch.androidutils.utils.designsystem.AppIconResource
import com.mitch.androidutils.utils.designsystem.util.AppLayoutId

@Composable
fun AppIcon(
    icon: AppIconResource,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current
) {
    Icon(
        imageVector = icon.imageVectorProvider(),
        contentDescription = contentDescription,
        modifier = Modifier.layoutId(AppLayoutId.AppIconId).then(modifier),
        tint = color
    )
}
