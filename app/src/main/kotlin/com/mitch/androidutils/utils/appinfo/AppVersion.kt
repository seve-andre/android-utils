package com.mitch.androidutils.utils.appinfo

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.pm.PackageInfoCompat

data class AppVersion(
    /**
     * App string version e.g. 1.0.0, 1.1.0, ...
     */
    val versionName: String,

    /**
     * App code version e.g. 1, 2, 3, ...
     */
    val versionCode: Long
)

fun Context.getAppVersion(): AppVersion? {
    return try {
        val packageManager = this.packageManager
        val packageInfo = packageManager.getPackageInfoCompat(this.packageName)
        AppVersion(
            versionName = packageInfo.versionName,
            versionCode = PackageInfoCompat.getLongVersionCode(packageInfo)
        )
    } catch (e: PackageManager.NameNotFoundException) {
        // Timber.e(e)
        null
    }
}
