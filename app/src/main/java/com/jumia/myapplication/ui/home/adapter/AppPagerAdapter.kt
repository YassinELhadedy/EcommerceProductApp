package com.jumia.myapplication.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class AppPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle?) :
    FragmentStateAdapter(fm, lifecycle!!) {
    private val items: MutableList<Fragment> = ArrayList()

    //    public AppPagerAdapter(@NonNull FragmentManager fm , LifecycleOwner lifecycleOwner) {
    //        super(fm , lifecycleOwner);
    //    }
    override fun createFragment(position: Int): Fragment {
        return items[position]
    }

    fun addItem(fragment: Fragment) {
        items.add(fragment)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}