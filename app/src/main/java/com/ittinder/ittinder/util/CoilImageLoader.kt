package com.ittinder.ittinder.util

import android.widget.ImageView
import coil.load

class CoilImageLoader : ImageLoader {
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        imageView.load(imageUrl) {
            crossfade(true)
        }
    }
}