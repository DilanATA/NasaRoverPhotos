package com.dilanata.nasaroverphotos.ui.RoverPhotos

import androidx.lifecycle.*
import com.dilanata.nasaroverphotos.api.model.NasaRoverPhotos
import com.dilanata.nasaroverphotos.data.Resource
import com.dilanata.nasaroverphotos.respository.RoverPhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoverPhotosVM @Inject constructor(
    private val roverPhotosRepository: RoverPhotosRepository
): ViewModel() {

    private val _photos = MutableLiveData<Resource<NasaRoverPhotos>>()
    val photos : LiveData<Resource<NasaRoverPhotos>>
    get() = _photos

    fun getRoverPhotos(roverType: String, map: Map<String, String>) {
        viewModelScope.launch {
            roverPhotosRepository.getRoverPhotos(roverType = roverType, map).collect {
                _photos.postValue(it)
            }
        }
    }

    private val _photosByCamera = MutableLiveData<Resource<NasaRoverPhotos>>()
    val photosByCamera : LiveData<Resource<NasaRoverPhotos>>
        get() = _photosByCamera
    fun getRoverPhotosByCamera(roverType: String, map: Map<String, String>) {
        viewModelScope.launch {
            roverPhotosRepository.getRoverPhotosByCamera(roverType = roverType, map).collect {
                _photosByCamera.postValue(it)
            }
        }
    }
}