package com.paul.todolist.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.HandlerThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow

class LocationUtil(context: Context) {

    companion object {
        const val MIN_TIME: Long = 1000L
        const val MIN_DISTANCE: Float = 0.0f
    }

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var locationListener: LocationListener? = null

    private val locationStateFlow = MutableStateFlow<Location>(Location(LocationManager.GPS_PROVIDER))
    private val gpsProviderState = mutableStateOf(false)
    private val isStart: MutableState<Boolean> = mutableStateOf(false)

    private val locHandlerThread = HandlerThread("LocationUtil Thread")

    init {
        locHandlerThread.start()
    }

    @SuppressLint("MissingPermission")
    fun start(minTimeMs: Long = MIN_TIME, minDistanceM: Float = MIN_DISTANCE) {
        locationListener().let {
            locationListener = it
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeMs, minDistanceM, it, locHandlerThread.looper)
        }
        gpsProviderState.value = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isStart.value = true
    }

    fun stop() {
        locationListener?.let {
            locationManager.removeUpdates(it)
        }
        isStart.value = false
    }

    private fun locationListener() = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationStateFlow.value = location
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String) {
            gpsProviderState.value = true
        }

        override fun onProviderDisabled(provider: String) {
            gpsProviderState.value = false
        }
    }
}

fun getLocationManager(context: Context): LocationManager =
    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

fun isLocEnable(context: Context): Boolean {
    val locationManager = getLocationManager(context)
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

