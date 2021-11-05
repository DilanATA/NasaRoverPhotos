package com.dilanata.nasaroverphotos.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dilanata.nasaroverphotos.R

@BindingAdapter("roverPoster")
fun roverPoster(imageView: AppCompatImageView, url: String?) {
    if (url.isNullOrEmpty()){
        Glide.with(imageView.context)
            .load(R.color.black)
            .timeout(15000)
            .apply {
                RequestOptions()
                    .error(R.drawable.ic_cancel)
                    .fitCenter()
            }
            .fitCenter()
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .load(url)
            .timeout(15000)
            .apply {
                RequestOptions()
                    .error(R.drawable.ic_cancel)
                    .fitCenter()
            }
            .fitCenter()
            .into(imageView)
    }
}
