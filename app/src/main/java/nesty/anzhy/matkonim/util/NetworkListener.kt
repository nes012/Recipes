package nesty.anzhy.matkonim.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkListener: ConnectivityManager.NetworkCallback() {
    //we need to override here 2 methods. onAvailable/onLost


    //default value will be false, but it doesn't even matter
    private val isNetworkAvailable = MutableStateFlow(false)


    override fun onAvailable(network: Network) {
       isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }


    fun checkNetworkAvailability(context: Context):MutableStateFlow<Boolean>{
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false

        connectivityManager.allNetworks.forEach{ network->

            //we're going to check each network there is and check if we are online or not
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                //here we're checking if our device has internet connection. if it has then we want to set the value to our is connected variable
                if(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    isConnected = true
                    return@forEach
                }
            }
        }

        isNetworkAvailable.value = isConnected

        return isNetworkAvailable
    }
}