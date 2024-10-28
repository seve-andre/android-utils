package com.mitch.androidutils.utils.clipboard

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString

@Composable
fun rememberClipboardText(): State<AnnotatedString?> {
    val clipboardManager = LocalClipboardManager.current
    val text = rememberSaveable { mutableStateOf(clipboardManager.getText()) }

    val clipboardManagerService =
        LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val callback = remember {
        ClipboardManager.OnPrimaryClipChangedListener {
            text.value = clipboardManager.getText()
        }
    }
    DisposableEffect(clipboardManagerService) {
        clipboardManagerService.addPrimaryClipChangedListener(callback)
        onDispose {
            clipboardManagerService.removePrimaryClipChangedListener(callback)
        }
    }
    return text
}
