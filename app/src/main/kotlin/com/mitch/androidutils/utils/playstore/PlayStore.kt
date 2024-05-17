package com.mitch.androidutils.utils.playstore

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openAppOnPlayStore(appId: String) = this.openPlayStore(PlayStoreSearch.AppDetails(appId))
fun Context.openDeveloperOnPlayStore(developerId: String) = this.openPlayStore(PlayStoreSearch.DevApps(developerId))
fun Context.searchOnPlayStore(query: String) = this.openPlayStore(PlayStoreSearch.Query(query))

private const val PlayStoreBaseLocalUri = "market"
private const val PlayStoreBaseWebUri = "https://play.google.com/store"

private sealed interface PlayStoreSearch {
    data class AppDetails(val appPackage: String) : PlayStoreSearch
    data class DevApps(val devId: String) : PlayStoreSearch
    data class Query(val query: String) : PlayStoreSearch

    fun toUri(): PlayStoreUri {
        val (localAppend, webAppend) = when (this) {
            is AppDetails -> "details?id=${this.appPackage}" to "apps/details?id=${this.appPackage}"
            is DevApps -> "search?q=pub:${this.devId}" to "search?q=pub:${this.devId}"
            is Query -> "search?q=${this.query}&c=apps" to "search?q=${this.query}&c=apps"
        }
        return PlayStoreUri(
            local = "$PlayStoreBaseLocalUri://$localAppend",
            web = "$PlayStoreBaseWebUri/$webAppend"
        )
    }
}

private data class PlayStoreUri(
    val local: String,
    val web: String
)

private fun Context.openPlayStore(search: PlayStoreSearch) {
    val uri = search.toUri()
    try {
        this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.local)))
    } catch (e: ActivityNotFoundException) {
        // Timber.e("Couldn't open Play Store; opening web page instead")
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(uri.web)
            )
        )
    }
}
