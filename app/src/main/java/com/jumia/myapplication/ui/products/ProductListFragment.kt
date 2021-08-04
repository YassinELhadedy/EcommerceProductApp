package com.jumia.myapplication.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.R
import com.jumia.myapplication.infrastructure.dto.JumConfiguration
import com.jumia.myapplication.ui.util.progress.WaitingDialog
import com.jumia.myapplication.ui.util.state.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalPagingApi
@AndroidEntryPoint
class ProductListFragment : Fragment() {
    @ExperimentalPagingApi
    private val productViewModel: ProductViewModel by activityViewModels()
    private val mWaitingDialog: WaitingDialog by lazy { WaitingDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        productViewModel.productInfo(1)
        getProducts()
    }

    private fun observer() {
        productViewModel.productData.observe(viewLifecycleOwner, {
            when (it?.status) {
                Status.SUCCESS -> {
                    mWaitingDialog.dismissDialog()
                    if (it.data is JumConfiguration) {
                        Toast.makeText(requireContext(), "here", Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR -> {
                    mWaitingDialog.dismissDialog()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    mWaitingDialog.showDialog()
                }
            }
        })
    }

    private fun getProducts(){
        lifecycleScope.launch {
            productViewModel.getProducts(
            ).catch {
                Toast.makeText(requireContext(), "failllled", Toast.LENGTH_LONG).show()
            }.collectLatest {
                Toast.makeText(requireContext(), "here222222222", Toast.LENGTH_LONG).show()
            }
        }
    }
}