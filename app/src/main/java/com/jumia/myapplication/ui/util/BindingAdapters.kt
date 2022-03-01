package com.jumia.myapplication.ui.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.jumia.myapplication.R
import com.jumia.myapplication.domain.StoreStatus
import com.jumia.myapplication.ui.util.ImageHelper.requestOptionsOrderItem
import de.hdodenhof.circleimageview.CircleImageView

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

@BindingAdapter("imageUrl")
fun loadImage(imageView: CircleImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(ImageHelper.getResizeImage230x230(url))
            .apply(requestOptionsOrderItem)
            .into(imageView)
    }
}

@BindingAdapter(value = ["image2", "error","placeholder","isProgress"], requireAll = true)
fun loadImageUrl(
    imageView: ImageView,
    image2: String,
    error: Drawable? = null,
    placeholder: Drawable? = null,
    isProgress: Boolean = true
) {
    if (!image2.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(ImageHelper.getResizeImage230x230(image2))
            .apply(requestOptionsOrderItem)
            .into(imageView)
    }
}

@BindingAdapter("storeBackground")
fun getStoreBackground(view: View, storeStatus: StoreStatus) {
    view.background = when (storeStatus) {
        StoreStatus.CLOSED -> ContextCompat.getDrawable(view.context, R.drawable.ic_closed)
        StoreStatus.OPEN -> ContextCompat.getDrawable(view.context, R.drawable.ic_open)
        StoreStatus.BUSY -> ContextCompat.getDrawable(view.context, R.drawable.ic_busy_store)
        StoreStatus.CLOSE_SOON-> ContextCompat.getDrawable(view.context, R.drawable.ic_within_store)
    }
}
