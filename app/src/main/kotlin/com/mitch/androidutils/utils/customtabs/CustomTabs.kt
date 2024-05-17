package com.mitch.androidutils.utils.customtabs

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.launchCustomTab(
    uri: Uri,
    properties: CustomTabProperties
) {
    val customTabsIntent = CustomTabsIntent.Builder().apply {
        toolbar(properties.toolbar)
        icons(properties.icons, this@launchCustomTab)
        animations(properties.animations, this@launchCustomTab)
        if (properties.actionButton != null) actionButton(properties.actionButton, this@launchCustomTab)
        menuItems(properties.additionalMenuItems)
        setBackgroundInteractionEnabled(properties.enableInteraction)
        setInstantAppsEnabled(properties.enableInstantApps)
        setSendToExternalDefaultHandlerEnabled(properties.openLinksInApp)
        if (properties.locale != null) setTranslateLocale(properties.locale)
        setShareIdentityEnabled(properties.shareIdentity)
        if (properties.partialProperties != null) {
            partial(properties.partialProperties)
        }
    }.build()

    val referrer = properties.referrer ?: "android-app://${this.packageName}"
    customTabsIntent.intent.putExtra(
        Intent.EXTRA_REFERRER,
        Uri.parse(referrer)
    )
    customTabsIntent.launchUrl(this, uri)
}
