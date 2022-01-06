package com.ittinder.ittinder.Modules.Chats.util

import android.widget.ImageView
import coil.load
import com.ittinder.ittinder.R
import com.ittinder.ittinder.Modules.Chats.util.ImageLoader

class CoilImageLoader : ImageLoader {
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        imageView.load(imageUrl) {
            crossfade(true)
        }
    }
}