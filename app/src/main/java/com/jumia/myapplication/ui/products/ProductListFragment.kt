package com.jumia.myapplication.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.ExperimentalPagingApi
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.jumia.myapplication.R
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.infrastructure.SafeApiCaller
import com.jumia.myapplication.infrastructure.dto.JumProduct
import com.jumia.myapplication.ui.util.progress.WaitingDialog
import com.jumia.myapplication.ui.util.state.Status
import dagger.hilt.android.AndroidEntryPoint
import com.google.gson.JsonObject
import com.jumia.myapplication.infrastructure.dto.AppConfigurationResponse


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
    }

    private fun observer() {
        productViewModel.productData.observe(viewLifecycleOwner, {
            when (it?.status) {
                Status.SUCCESS -> {
                    mWaitingDialog.dismissDialog()
                    if (it.data is AppConfigurationResponse) {
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

}