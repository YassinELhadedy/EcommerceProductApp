package com.jumia.myapplication.ui.util

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.jumia.myapplication.R

object ImageHelper {

    val requestOptionsOrderItem = RequestOptions()
        .circleCrop()
        .placeholder(R.drawable.circle_gray_stroke_white_solid)
        .error(R.drawable.circle_gray_stroke_white_solid)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

    fun getResizeImage200x200(imageUrl: String? = ""): String {
        return getResizeImage(imageUrl, 200)
    }

    fun getResizeImage230x230(imageUrl: String? = ""): String {
        return getResizeImage(imageUrl, 230)
    }

    fun getResizeImage400x400(imageUrl: String? = ""): String {
        return getResizeImage(imageUrl, 400)
    }

    fun getResizeImage2000x2000(imageUrl: String? = ""): String {
       return getResizeImage(imageUrl, 2000)
    }


    fun getResizeImage(imageUrl: String? = "", size: Int): String {

        return if (imageUrl.isNullOrEmpty()) {
            ""
        } else if (imageUrl.contains("=w200") || imageUrl.contains("=h200")
                || imageUrl.contains("=w400") || imageUrl.contains("=h400")) {
            imageUrl
        } else if (imageUrl.contains("images.nana.sa") && imageUrl.contains(".jpg")) {
            imageUrl.replace(".jpg", "==w$size=h$size.jpg")
        } else if (imageUrl.contains("images.nana.sa") && imageUrl.contains(".png")) {
            imageUrl.replace(".png", "==w$size=h$size.png")
        } else {
            imageUrl
        }

        //Old way
//        return "${imageUrl ?: ""}?w=200&h=200"
    }
}