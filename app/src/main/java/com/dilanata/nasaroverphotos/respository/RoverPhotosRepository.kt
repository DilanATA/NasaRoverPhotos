package com.dilanata.nasaroverphotos.respository

import com.dilanata.nasaroverphotos.api.RoversApi
import com.dilanata.nasaroverphotos.api.model.NasaRoverPhotos
import com.dilanata.nasaroverphotos.data.Resource
import com.dilanata.nasaroverphotos.util.Constants.API_KEY
import com.github.ajalt.timberkt.i
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RoverPhotosRepository @Inject constructor(
    private val roversApi: RoversApi
) {
    fun getRoverPhotos(roverType: String, map: Map<String, String>): Flow<Resource<NasaRoverPhotos>> {
        return  flow{
            emit(Resource.loading(null))
            val roverPhotos = roversApi.getRoverPhotos(
                roverType = roverType,
                map = map
            )
            emit(Resource.success(roverPhotos))
        }.retryWhen { cause, attempt ->
            i { "attempt count -> $attempt" }
            i { "cause -> $cause" }
            (cause is Exception).also { if (it) delay(10_000) }
        }.catch {
            emit(Resource.error(it.localizedMessage, null, it))
        }.flowOn(Dispatchers.IO)
    }

    fun getRoverPhotosByCamera(roverType: String, map: Map<String, String>): Flow<Resource<NasaRoverPhotos>> {
        return  flow{
            emit(Resource.loading(null))
            val roverPhotos = roversApi.getRoverPhotosByCamera(
                roverType = roverType,
                map = map
            )
            emit(Resource.success(roverPhotos))
        }.retryWhen { cause, attempt ->
            i { "attempt count -> $attempt" }
            i { "cause -> $cause" }
            (cause is Exception).also { if (it) delay(10_000) }
        }.catch {
            emit(Resource.error(it.localizedMessage, null, it))
        }.flowOn(Dispatchers.IO)
    }
}