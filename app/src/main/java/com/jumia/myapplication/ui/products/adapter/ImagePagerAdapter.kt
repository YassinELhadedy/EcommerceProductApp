package com.jumia.myapplication.ui.products.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.jumia.myapplication.R
import com.jumia.myapplication.ui.util.ImageHelper


class ImagePagerAdapter(val mContext: Context,val images:List<String>) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.item_image, collection, false) as ViewGroup
        val imageView = layout.findViewById(R.id.imageViewMain) as ImageView

        Glide.with(imageView.context)
            .load(ImageHelper.getResizeImage230x230(images[position]))
            .apply(ImageHelper.requestOptionsOrderItem)
            .into(imageView)

        collection.addView(layout)
        return layout

    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
                 return images.size

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

}