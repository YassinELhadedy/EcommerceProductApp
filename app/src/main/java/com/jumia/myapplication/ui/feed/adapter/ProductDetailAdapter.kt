package com.jumia.myapplication.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jumia.myapplication.R
import com.jumia.myapplication.databinding.ItemProductDetailBinding


class ProductDetailAdapter(private val images:List<String>) :
    RecyclerView.Adapter<ProductDetailAdapter.ProductDetailViewHolder>() {
    inner class ProductDetailViewHolder(val itemProductCardsBindingImpl: ItemProductDetailBinding) :
        RecyclerView.ViewHolder(itemProductCardsBindingImpl.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailViewHolder =  ProductDetailViewHolder(DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.item_product_detail,
        parent,
        false
    ))

    override fun onBindViewHolder(holder: ProductDetailViewHolder, position: Int) {
        holder.itemProductCardsBindingImpl.item = images[position]
    }

    override fun getItemCount(): Int = images.size

}