package com.mitch.androidutils.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.core.content.getSystemService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

/**
 * Network monitoring for reporting app connectivity status
 */
interface NetworkMonitor {
    /**
     * Exposes if app connectivity status is online
     */
    val networkInfo: Flow<NetworkInfo>
}

class ConnectivityManagerNetworkMonitor(
    private val context: Context,
    ioDispatcher: CoroutineDispatcher
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
            }

            override fun onLost(network: Network) {
                networks -= network
                channel.trySend(
                    NetworkInfo(
                        isOnline = networks.isNotEmpty(),
                        isOnWifi = connectivityManager.isOnWifi()
                    )
                )
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

    private fun ConnectivityManager.isCurrentlyConnected(): Boolean {
        val isConnected = activeNetwork
            ?.let(::getNetworkCapabilities)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        return isConnected ?: false
    }

    private fun ConnectivityManager.isOnWifi(): Boolean {
        val networkCapabilities = activeNetwork?.let(::getNetworkCapabilities)

        return when {
            networkCapabilities == null -> false

            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false
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
