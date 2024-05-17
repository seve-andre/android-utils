package com.mitch.androidutils.utils.activity

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

private tailrec fun Context.findActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> null
    }
}
