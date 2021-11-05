package com.dilanata.nasaroverphotos.api

import com.dilanata.nasaroverphotos.api.model.NasaRoverPhotos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RoversApi {

    @GET("/mars-photos/api/v1/rovers/{roverType}/photos?")
    suspend fun getRoverPhotos(
        @Path("roverType") roverType: String?,
        @QueryMap map: Map<String,String>
    ): NasaRoverPhotos

    @GET("/mars-photos/api/v1/rovers/{roverType}/photos?")
    suspend fun getRoverPhotosByCamera(
        @Path("roverType") roverType: String?,
        @QueryMap map: Map<String, String>
    ): NasaRoverPhotos
}