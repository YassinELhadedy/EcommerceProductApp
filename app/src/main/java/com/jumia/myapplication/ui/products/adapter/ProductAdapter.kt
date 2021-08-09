package com.jumia.myapplication.ui.products.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jumia.myapplication.R
import com.jumia.myapplication.databinding.ItemProductCardsBinding
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.ui.util.click.setSafeOnClickListener

class ProductAdapter(val context: Context,val listener:OnItemClickListener
) : PagingDataAdapter<Product, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ProductViewHolder(
            DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_product_cards,
                    parent,
                    false
            )
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val holder = (holder as ProductViewHolder)
        holder.itemGroupProductBinding.item = getItem(position)

        val animation = AnimationUtils.loadAnimation(
                context,
                R.anim.anim_bottom
        )
        holder.itemView.startAnimation(animation)
        holder.itemView.setSafeOnClickListener {
            listener.onItemClick(getItem(position)?.sku)
        }
    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                    oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                    oldItem.equals(newItem)
        }
    }

    inner class ProductViewHolder(val itemGroupProductBinding: ItemProductCardsBinding) :
            RecyclerView.ViewHolder(itemGroupProductBinding.root)
}
interface OnItemClickListener {
    fun onItemClick(productId: String?)
}