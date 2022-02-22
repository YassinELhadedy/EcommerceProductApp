package com.jumia.myapplication.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jumia.myapplication.R
import com.jumia.myapplication.databinding.FeedStoryItemBinding
import com.jumia.myapplication.ui.feed.FeedStoryModel
import com.jumia.myapplication.ui.util.click.setSafeOnClickListener

class FeedStoryAdapter(private val stories:List<FeedStoryModel>, private val listener:StoryOnItemClickListener): RecyclerView.Adapter<FeedStoryAdapter.FeedStoryViewHolder>()  {

    class FeedStoryViewHolder(val feedStoryItemBinding: FeedStoryItemBinding) :
        RecyclerView.ViewHolder(feedStoryItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedStoryViewHolder =
        FeedStoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.feed_story_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FeedStoryViewHolder, position: Int) {
        holder.feedStoryItemBinding.item = stories[position]
        holder.itemView.setSafeOnClickListener {
            listener.onItemClick(stories[position].id)
        }
    }

    override fun getItemCount(): Int =stories.size
}
interface StoryOnItemClickListener {
    fun onItemClick(productId: String?)
}