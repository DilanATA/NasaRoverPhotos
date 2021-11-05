package com.dilanata.nasaroverphotos.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class NasaRoverPhotos(
    @SerialName("photos")
    var photos: List<Photo>
): Parcelable