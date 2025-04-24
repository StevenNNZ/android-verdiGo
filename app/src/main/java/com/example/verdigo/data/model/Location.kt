package com.example.verdigo.data.model

import com.google.android.gms.maps.model.LatLng

data class Location(
    val address: String,
    val latLng: LatLng
)