package com.jumia.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.databinding.FragmentProductListBinding
import com.jumia.myapplication.ui.feed.FeedFragment
import com.jumia.myapplication.ui.futuredrobs.FutureDrobsFragment
import com.jumia.myapplication.ui.home.adapter.AppPagerAdapter
import com.jumia.myapplication.ui.releasedrobs.ReleasedDrobsFragment
import com.jumia.myapplication.ui.toprateddrobs.TopRatedDrobsFragment
import com.jumia.myapplication.ui.util.scrollToEnd
import com.jumia.myapplication.ui.util.scrollToStart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@InternalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var viewDataBinding: FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentProductListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            homeFragment = this@HomeFragment
            viewmodel = homeViewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()

    }


    private fun initViewPager() {
//        viewDataBinding.includeLayoutHomeBody.pagerContainer.let { c ->
//
//            viewDataBinding.includeLayoutHomeBody.pagerContainer.post {
//
//                viewDataBinding.includeLayoutHomeBody.fragmentIndustryVP.let { vp ->
//                    vp.post {
//                        vp.layoutParams.height = c.height
//                    }
//                }
//            }
//        }
        viewDataBinding.includeLayoutHomeBody.fragmentIndustryVP.let { viewPager2 ->

            val pagerAdapter = AppPagerAdapter(childFragmentManager, lifecycle)

            arrayOf(
                FeedFragment(),
                ReleasedDrobsFragment(),
                FutureDrobsFragment(),
                TopRatedDrobsFragment()
            ).forEach {
                pagerAdapter.addItem(it)
            }

            viewPager2.adapter = pagerAdapter

            viewPager2.isUserInputEnabled = false

            viewPager2.offscreenPageLimit = 4
        }
    }

    fun switchMenuPager(menu: Menu) {
        homeViewModel.fragmentMenuSelected.value = menu
        lifecycleScope.launch {
            delay(240)
            if (menu == Menu.TOPRATEDDROBS || menu == Menu.FUTUREDROBS) {
                viewDataBinding.includeLayoutHomeBody.includeLayoutNewMainServices
                    .horizontalScrollViewMenu.scrollToStart()
            } else {
                viewDataBinding.includeLayoutHomeBody.includeLayoutNewMainServices
                    .horizontalScrollViewMenu.scrollToEnd()
            }
        }

        navigate(menu.value)
    }

    private fun navigate(industryIndex: Int) {
        viewDataBinding.includeLayoutHomeBody.fragmentIndustryVP.currentItem = industryIndex
    }
}
