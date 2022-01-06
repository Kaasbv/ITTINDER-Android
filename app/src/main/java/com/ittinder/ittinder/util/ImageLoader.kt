package com.ittinder.ittinder.util

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageUrl: String, imageView: ImageView)
}