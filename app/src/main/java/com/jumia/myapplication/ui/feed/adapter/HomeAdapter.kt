package com.jumia.myapplication.ui.feed.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jumia.myapplication.R
import com.jumia.myapplication.databinding.ItemProductCardsBinding
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.ui.NavigationJourney
import com.jumia.myapplication.ui.Navigator
import com.jumia.myapplication.ui.util.click.setSafeOnClickListener

class ProductAdapter(val context: Context, private val listener: OnItemClickListener) :
    PagingDataAdapter<Product, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ProductViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_cards,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val productViewHolder = (holder as ProductViewHolder)
        productViewHolder.itemGroupProductBinding.item = getItem(position)

        productViewHolder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.anim_bottom
            )
        )
        productViewHolder.itemView.setSafeOnClickListener {
            listener.onItemClick(Navigator(NavigationJourney.PRODUCTDETAIL, Bundle().apply {
                putString("SKU", getItem(position)?.sku)
            }))
        }
    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

    inner class ProductViewHolder(val itemGroupProductBinding: ItemProductCardsBinding) :
        RecyclerView.ViewHolder(itemGroupProductBinding.root)
}

interface OnItemClickListener {
    fun onItemClick(navigator: Navigator)
}