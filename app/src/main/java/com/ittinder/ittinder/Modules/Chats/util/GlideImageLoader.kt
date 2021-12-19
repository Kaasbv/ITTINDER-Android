package com.ittinder.ittinder.Modules.Chats.util

import android.content.Context
import android.widget.ImageView
import com.ittinder.ittinder.R
import com.bumptech.glide.Glide
import com.ittinder.ittinder.Modules.Chats.util.ImageLoader

class GlideImageLoader(private val context: Context) : ImageLoader {
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)
    }
}