package com.example.contact_app.data.model

import android.net.Uri
import androidx.annotation.DrawableRes

sealed interface Image {
    data class ImageDrawable(@DrawableRes val drawable: Int): Image
    data class ImageUri(val uri: Uri): Image
}