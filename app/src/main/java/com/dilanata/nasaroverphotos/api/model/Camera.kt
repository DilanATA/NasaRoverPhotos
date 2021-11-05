package com.dilanata.nasaroverphotos.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Camera(
    @SerialName("full_name")
    val full_name: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("rover_id")
    val rover_id: Int
): Parcelable