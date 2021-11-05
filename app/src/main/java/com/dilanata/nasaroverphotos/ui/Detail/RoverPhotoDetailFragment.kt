package com.dilanata.nasaroverphotos.ui.Detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dilanata.nasaroverphotos.R
import com.dilanata.nasaroverphotos.api.model.Photo
import com.dilanata.nasaroverphotos.databinding.FragmentRoverPhotoDetailBinding
import com.dilanata.nasaroverphotos.util.BundleKeys

class RoverPhotoDetailFragment : DialogFragment() {
    lateinit var binding: FragmentRoverPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rover_photo_detail, container, false)
        binding = FragmentRoverPhotoDetailBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)

        val photoDetail: Photo = arguments?.get(BundleKeys.photoDetail) as Photo
        binding.photoDetail = photoDetail
    }
}