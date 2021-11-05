package com.dilanata.nasaroverphotos.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Rover(
    @SerialName("id")
    val id: Int,
    @SerialName("landing_date")
    val landing_date: String,
    @SerialName("launch_date")
    val launch_date: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String
): Parcelable