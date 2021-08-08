package com.jumia.myapplication.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.databinding.FragmentProductListBinding
import com.jumia.myapplication.domain.Product
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
    private lateinit var viewDataBinding: FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentProductListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = productViewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProducts()
    }


    private fun getProducts(){
        lifecycleScope.launch {
            productViewModel.getProducts(
            ).catch {
                Toast.makeText(requireContext(), "failllled", Toast.LENGTH_LONG).show()
            }.collectLatest {
                Toast.makeText(requireContext(), "here222222222", Toast.LENGTH_LONG).show()
                openProductDetailFragment(2)
            }
        }
    }

    private fun openProductDetailFragment(productId: Int) {
        val action =
            ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                productId
            )
        findNavController().navigate(action)
    }
}