package com.mitch.androidutils.utils.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION_CODES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

enum class AppPermission(val permissionString: String) {
    Camera(android.Manifest.permission.CAMERA),
    Notifications(android.Manifest.permission.POST_NOTIFICATIONS)
}

@RequiresApi(VERSION_CODES.M)
@Composable
fun rememberPermissionState(
    permission: AppPermission,
    onGranted: (() -> Unit)? = null,
    onShowRationale: (() -> Unit)? = null,
    onPermanentlyDenied: (() -> Unit)? = null,
    launchPermissionOnStart: Boolean = true
): PermissionState {
    val context = LocalContext.current
    val activity = context as Activity
    var isPermissionGranted by rememberSaveable {
        mutableStateOf(context.isPermissionAlreadyGranted(permission.permissionString))
    }
    var showPermissionRationale by rememberSaveable {
        mutableStateOf(activity.shouldShowRequestPermissionRationale(permission.permissionString))
    }
    var shouldDirectUserToAppSettings by rememberSaveable { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            isPermissionGranted = true
            onGranted?.invoke()
        } else {
            showPermissionRationale =
                activity.shouldShowRequestPermissionRationale(permission.permissionString)
            if (showPermissionRationale) onShowRationale?.invoke()
            shouldDirectUserToAppSettings = !showPermissionRationale && !isPermissionGranted
            if (shouldDirectUserToAppSettings) onPermanentlyDenied?.invoke()
        }
    }

    if (launchPermissionOnStart) {
        LifecycleEventEffect(Lifecycle.Event.ON_START) {
            if (!isPermissionGranted && !showPermissionRationale) {
                permissionLauncher.launch(permission.permissionString)
            }
        }
    }

    return object : PermissionState {
        override val permission: AppPermission = permission
        override val status: PermissionStatus
            get() = if (isPermissionGranted) {
                PermissionStatus.Granted
            } else if (showPermissionRationale) {
                PermissionStatus.ShowRationale
            } else if (shouldDirectUserToAppSettings) {
                PermissionStatus.PermanentlyDenied
            } else {
                PermissionStatus.Idle
            }

        override fun launchPermissionRequest() {
            permissionLauncher.launch(permission.permissionString)
        }
    }
}

private fun Context.isPermissionAlreadyGranted(permissionString: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permissionString
    ) == PackageManager.PERMISSION_GRANTED
}

@Stable
interface PermissionState {
    val permission: AppPermission
    val status: PermissionStatus
    fun launchPermissionRequest()
}

enum class PermissionStatus {
    Idle,
    Granted,
    ShowRationale,
    PermanentlyDenied
}
