package com.dilanata.nasaroverphotos.ui.RoverPhotos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dilanata.nasaroverphotos.R
import com.dilanata.nasaroverphotos.api.model.Photo
import com.dilanata.nasaroverphotos.databinding.ItemRoverPhotosBinding
import com.github.ajalt.timberkt.i


class RoverPhotoAdapter(
    private val context: Context,
    private val roverPhotos: ArrayList<Photo>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<RoverPhotoAdapter.RoverPhotoHolder>() {

    fun updateList(newList: List<Photo>) {
        roverPhotos.addAll(newList)
      i { "itemcount -> $itemCount newlist -> ${newList.size}" }
        notifyItemRangeChanged(itemCount, newList.size)
    }

    inner class RoverPhotoHolder(val itemRoverPhotosBinding: ItemRoverPhotosBinding): RecyclerView.ViewHolder(
        itemRoverPhotosBinding.root
    ) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverPhotoHolder =
        RoverPhotoHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_rover_photos,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RoverPhotoHolder, position: Int) {
        holder.itemRoverPhotosBinding.photo = roverPhotos[position]

        holder.itemRoverPhotosBinding.llPhoto.setOnClickListener {
            onClickListener.onClickForDetail(roverPhotos[position])
        }
    }

    override fun getItemCount(): Int = roverPhotos.size

    interface OnClickListener {
        fun onClickForDetail(photoDetail: Photo)
    }
}


