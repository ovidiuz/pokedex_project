package com.example.pokedexproject.network

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import timber.log.Timber

object LoadImage {
    fun loadImage(context: Context, ImageView: ImageView, url: String) {
        Timber.d("Loading image at url: $url")
        Glide.with(context)
            .load(url)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(ImageView);
    }
}