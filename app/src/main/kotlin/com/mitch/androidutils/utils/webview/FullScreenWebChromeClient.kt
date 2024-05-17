package com.mitch.androidutils.utils.webview

import android.view.View
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import com.kevinnzou.web.AccompanistWebChromeClient

class FullScreenWebChromeClient(val activity: ComponentActivity) : AccompanistWebChromeClient() {
    private var customView: View? = null

    override fun onHideCustomView() {
        (activity.window.decorView as FrameLayout).removeView(this.customView)
        this.customView = null
    }

    override fun onShowCustomView(paramView: View, paramCustomViewCallback: CustomViewCallback) {
        if (this.customView != null) {
            onHideCustomView()
            return
        }
        this.customView = paramView
        (activity.window.decorView as FrameLayout).addView(this.customView, FrameLayout.LayoutParams(-1, -1))
    }
}
