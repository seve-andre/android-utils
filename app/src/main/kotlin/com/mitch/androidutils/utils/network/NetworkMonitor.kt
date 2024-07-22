package com.mitch.androidutils.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.core.content.getSystemService
import com.mitch.template.di.Dispatcher
import com.mitch.template.di.TemplateDispatcher.Io
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * Network monitoring for reporting app connectivity status
 */
interface NetworkMonitor {
    /**
     * Exposes if app connectivity status is online
     */
    val networkInfo: Flow<NetworkInfo>
}

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(Io) private val ioDispatcher: CoroutineDispatcher
) : NetworkMonitor {
    override val networkInfo: Flow<NetworkInfo> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        if (connectivityManager == null) {
            channel.trySend(NetworkInfo(isOnline = false, isOnWifi = false))
            channel.close()
            return@callbackFlow
        }

        val callback = object : NetworkCallback() {
            private val networks = mutableSetOf<Network>()

            override fun onAvailable(network: Network) {
                networks += network
                channel.trySend(
                    NetworkInfo(
                        isOnline = true,
                        isOnWifi = connectivityManager.isOnWifi()
                    )
                )
                Timber.d("onAvailable: $networks with ${connectivityManager.isOnWifi()}")
            }

            override fun onLost(network: Network) {
                networks -= network
                channel.trySend(
                    NetworkInfo(
                        isOnline = networks.isNotEmpty(),
                        isOnWifi = connectivityManager.isOnWifi()
                    )
                )
                Timber.d("onLost: $networks with ${connectivityManager.isOnWifi()}")
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                channel.trySend(
                    NetworkInfo(
                        isOnline = networks.isNotEmpty(),
                        isOnWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    )
                )
                Timber.d("onCapabilitiesChanged for network $network")
            }
        }

        val request = Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(request, callback)

        channel.trySend(
            NetworkInfo(
                isOnline = connectivityManager.isCurrentlyConnected(),
                isOnWifi = connectivityManager.isOnWifi()
            )
        )

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
        .flowOn(ioDispatcher)
        .conflate()

    @Suppress("DEPRECATION")
    private fun ConnectivityManager.isCurrentlyConnected() = when {
        VERSION.SDK_INT >= VERSION_CODES.M ->
            activeNetwork
                ?.let(::getNetworkCapabilities)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        else -> activeNetworkInfo?.isConnected
    } ?: false

    @Suppress("DEPRECATION")
    private fun ConnectivityManager.isOnWifi() = when {
        VERSION.SDK_INT >= VERSION_CODES.M -> {
            val networkCapabilities = activeNetwork?.let(::getNetworkCapabilities)

            when {
                networkCapabilities == null -> false

                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        }

        else -> {
            when {
                activeNetworkInfo == null -> false

                activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetworkInfo?.type == ConnectivityManager.TYPE_ETHERNET -> true

                else -> false
            }
        }
    }
}

@Composable
fun NetworkMonitor.networkInfoState(): State<NetworkInfo> {
    return produceState(initialValue = NetworkInfo(isOnline = false, isOnWifi = false)) {
        this@networkInfoState.networkInfo.collect { value = it }
    }
}

data class NetworkInfo(
    val isOnline: Boolean,
    val isOnWifi: Boolean
)
