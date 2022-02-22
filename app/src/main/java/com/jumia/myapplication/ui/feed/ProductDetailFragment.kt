package com.jumia.myapplication.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.databinding.FragmentProductDetailBinding
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.ui.exception.ErrorMessageFactory
import com.jumia.myapplication.ui.feed.adapter.ProductDetailAdapter
import com.jumia.myapplication.ui.util.progress.WaitingDialog
import com.jumia.myapplication.ui.util.state.Status
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    @ExperimentalPagingApi
    private val productViewModel: ProductViewModel by activityViewModels()
    private val mWaitingDialog: WaitingDialog by lazy { WaitingDialog(requireActivity()) }
    private lateinit var viewDataBinding: FragmentProductDetailBinding
    // Creating Object of ViewPagerAdapter
    private lateinit var mViewPagerAdapter: ProductDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentProductDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = productViewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs: ProductDetailFragmentArgs by navArgs()
        observer()
        productViewModel.productInfo(safeArgs.productId)
    }

    private fun initViewPager(images:List<String>){
        mViewPagerAdapter = ProductDetailAdapter(images)
        viewDataBinding.viewpager.adapter = mViewPagerAdapter
    }

    private fun observer() {
        productViewModel.productData.observe(viewLifecycleOwner) {
            when (it?.status) {
                Status.SUCCESS -> {
                    mWaitingDialog.dismissDialog()
                    if (it.data is Product) {
                        initViewPager(it.data.imageList ?: emptyList())
                    }
                }
                Status.ERROR -> {
                    mWaitingDialog.dismissDialog()
                    Toast.makeText(
                        requireContext(),
                        ErrorMessageFactory.create(requireActivity(), it.data as Throwable),
                        Toast.LENGTH_LONG
                    ).show()
                }
                Status.LOADING -> {
                    mWaitingDialog.showDialog()
                }
                else -> {}
            }
        }
    }
}