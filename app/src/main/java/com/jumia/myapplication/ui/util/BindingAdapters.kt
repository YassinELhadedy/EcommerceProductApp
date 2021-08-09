package com.jumia.myapplication.ui.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.jumia.myapplication.ui.util.ImageHelper.requestOptionsOrderItem


/**
 * Data Binding adapters specific to the app.
 * Note BindingAdapter in Kotlin must be Top Level functions
 */


@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(ImageHelper.getResizeImage230x230(url))
            .apply(requestOptionsOrderItem)
            .into(imageView)
    }
}