package com.dilanata.nasaroverphotos.ui.RoverPhotos

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dilanata.nasaroverphotos.R
import com.dilanata.nasaroverphotos.api.model.NasaRoverPhotos
import com.dilanata.nasaroverphotos.api.model.Photo
import com.dilanata.nasaroverphotos.base.BaseFragment
import com.dilanata.nasaroverphotos.data.Status
import com.dilanata.nasaroverphotos.databinding.FragmentRoverPhotosBinding
import com.dilanata.nasaroverphotos.extension.hide
import com.dilanata.nasaroverphotos.extension.navigateSafe
import com.dilanata.nasaroverphotos.extension.selectedRover
import com.dilanata.nasaroverphotos.extension.show
import com.dilanata.nasaroverphotos.helper.queryMap
import com.dilanata.nasaroverphotos.helper.queryMapByCamera
import com.dilanata.nasaroverphotos.ui.RoverPhotos.adapter.RoverPhotoAdapter
import com.dilanata.nasaroverphotos.util.BundleKeys
import com.dilanata.nasaroverphotos.util.RoverTypes
import com.github.ajalt.timberkt.i
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Suppress("COMPATIBILITY_WARNING")
@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class RoverPhotosFragment :
    BaseFragment<FragmentRoverPhotosBinding>(R.layout.fragment_rover_photos),
    RoverPhotoAdapter.OnClickListener {

    private var loading: Boolean = false
    private var page: Int = 1
    lateinit var roverPhotoAdapter: RoverPhotoAdapter
    lateinit var nasaRoverPhotos: NasaRoverPhotos

    var tabPosition: Int = 0
    var tabSelection = RoverTypes.CURIOSITY.name


    private val roverPhotosVM: RoverPhotosVM by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedRover = RoverTypes.CURIOSITY
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRoverTab()
        setupSpinnerCamera()
        pagination()

        roverPhotosVM.getRoverPhotos(
            selectedRover.typeName,
            map = queryMap(
                page = 1
            )
        )

        roverPhotosVM.photos.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    i { "datalar -> ${it.data}" }
                    nasaRoverPhotos = it.data!!
                    if (page == 1) {
                        roverPhotoAdapter = RoverPhotoAdapter(
                            requireContext(),
                            nasaRoverPhotos.photos as ArrayList<Photo>,
                            this
                        )
                        binding.rvRoverPhotos.adapter = roverPhotoAdapter
                    } else {
                        roverPhotoAdapter.updateList(nasaRoverPhotos.photos)
                    }
                    binding.progressBar.hide()
                    loading = false
                }
                Status.ERROR -> i { "error ${it.throwable}" }
                Status.LOADING -> i { "Loading" }
            }
        })

    }

    private fun pagination() {
        binding.rvRoverPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvRoverPhotos.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val isReachedToEnd = lastVisibleItemPosition + 5 > totalItemCount
                if (isReachedToEnd && totalItemCount > 0 && !loading) {
                    page += 1
                    roverPhotosVM.getRoverPhotos(
                        roverType = selectedRover.typeName,
                        map = queryMap(
                            page = page
                        )
                    )
                    loading = true
                }
            }
        })
    }

    fun setupRoverTab() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(RoverTypes.CURIOSITY.name))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(RoverTypes.OPPORTUNITY.name))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(RoverTypes.SPIRIT.name))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.progressBar.show()
                tabPosition = tab!!.position
                tabSelection = tab.text.toString()
                when (tabSelection) {
                    RoverTypes.CURIOSITY.name -> {
                        page = 1
                        selectedRover = RoverTypes.CURIOSITY
                        onClick()
                    }
                    RoverTypes.OPPORTUNITY.name -> {
                        page = 1
                        selectedRover = RoverTypes.OPPORTUNITY
                        onClick()
                    }
                    RoverTypes.SPIRIT.name -> {
                        page = 1
                        selectedRover = RoverTypes.SPIRIT
                        onClick()
                    }
                }
                binding.progressBar.hide()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun setupSpinnerCamera() {
        val camerasList: MutableList<String> =
            resources.getStringArray(R.array.cameras_array).toMutableList()
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, camerasList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnrCameras.adapter = adapter

        binding.spnrCameras.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val name = parent?.getItemAtPosition(position).toString()
                page = 1
                if (name.contains("All Photos")) {
                    onClick()
                } else {
                    filterByCamera(name)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }

    fun onClick() {
        binding.spnrCameras.setSelection(0)

        roverPhotosVM.getRoverPhotos(
            selectedRover.typeName, map = queryMap(
                page = 1
            )
        )
    }

    override fun onClickForDetail(photoDetail: Photo) {
        val bundle = Bundle()
        bundle.putParcelable(BundleKeys.photoDetail, photoDetail)
        navigateSafe(R.id.action_roverPhotosFragment_to_roverPhotoDetailFragment, bundle)
    }

    fun filterByCamera(camera: String) {
            i {"filtered"}
        roverPhotosVM.getRoverPhotosByCamera(
            selectedRover.typeName,
            map = queryMapByCamera(
                page = 1,
                camera = camera
            )
        )

        roverPhotosVM.photosByCamera.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    i { "datalar -> ${it.data}" }
                    nasaRoverPhotos = it.data!!
                    if (page == 1) {
                        roverPhotoAdapter = RoverPhotoAdapter(
                            requireContext(),
                            nasaRoverPhotos.photos as ArrayList<Photo>,
                            this
                        )
                        binding.rvRoverPhotos.adapter = roverPhotoAdapter
                    } else {
                        roverPhotoAdapter.updateList(nasaRoverPhotos.photos)
                    }
                    binding.progressBar.hide()
                    loading = false
                }
                Status.ERROR -> i { "error ${it.throwable}" }
                Status.LOADING -> i { "Loading" }
            }
        })
    }
}