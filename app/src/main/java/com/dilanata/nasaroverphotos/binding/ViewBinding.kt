package com.dilanata.nasaroverphotos.binding

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.imageLoader
import coil.loadAny
import coil.request.Disposable
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dilanata.nasaroverphotos.R
import com.github.ajalt.timberkt.i

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

fun AppCompatImageView.setCharacterImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .timeout(15000)
        .apply(
            RequestOptions()
                .error(R.drawable.ic_cancel)
                .fitCenter()
        )
        .fitCenter()
        .into(this)
}


/** @see ImageView.loadAny */
@JvmSynthetic
inline fun ImageView.load(
    uri: String?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable = loadAny(uri, imageLoader, builder)


@BindingAdapter("setImage")
fun setImage(image: AppCompatImageView, imageAddress: String) {
    val requestOption = RequestOptions()
        .placeholder(R.mipmap.ic_launcher)
        .centerCrop()
        .error(R.mipmap.ic_launcher)
        .dontTransform()
        .dontAnimate()
    Glide.with(image)
        .load(imageAddress)
        .apply(requestOption)
        .into(image)
}
