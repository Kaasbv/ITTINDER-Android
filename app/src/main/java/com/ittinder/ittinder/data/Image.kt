package com.ittinder.ittinder.data
import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Image(
    @PrimaryKey(autoGenerate = false)
    val imageId: Int,
    val image: String,
    val photosImagePath: String,
    val sortNr: Int

)