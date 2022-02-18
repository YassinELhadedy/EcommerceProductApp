package com.jumia.myapplication.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.jumia.myapplication.databinding.FragmentProductListBinding
import com.jumia.myapplication.ui.exception.ErrorMessageFactory
import com.jumia.myapplication.ui.products.adapter.OnItemClickListener
import com.jumia.myapplication.ui.products.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener {
    @ExperimentalPagingApi
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var viewDataBinding: FragmentProductListBinding
    private lateinit var adapter: ProductAdapter

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
        initAdapter()
        observer()
        getProducts()
        actions()
    }

    @InternalCoroutinesApi
    private fun observer() {
        adapter.addLoadStateListener { loadState ->
            viewDataBinding.newProductRvAllInvoices.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            viewDataBinding.newProductSrlRefresh.isRefreshing =
                loadState.source.refresh is LoadState.Loading
            if (loadState.source.refresh is LoadState.Error) {
                adapter.retry()
            }
        }

        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { viewDataBinding.newProductRvAllInvoices.scrollToPosition(0) }
        }
    }

    private fun actions() {
        viewDataBinding.newProductSrlRefresh.setOnRefreshListener {
            refreshView()
        }
    }

    private fun initAdapter() {
        adapter = ProductAdapter(requireContext(), this)
        viewDataBinding.newProductRvAllInvoices.layoutManager =
            GridLayoutManager(requireContext(), 2)
        viewDataBinding.newProductRvAllInvoices.adapter = adapter
    }

    private fun refreshView() {
        getProducts()
        adapter.refresh()
    }

    private fun getProducts() {
        lifecycleScope.launch {
            productViewModel.getProducts(
            ).catch {
                Toast.makeText(requireContext(), ErrorMessageFactory.create(requireActivity(),it), Toast.LENGTH_LONG).show()
            }.collectLatest {
                adapter.submitData(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun openProductDetailFragment(productId: Int) {
        val action =
            HomeFragmentDirections.actionProductListFragmentToProductDetailFragment(
                productId
            )
        findNavController().navigate(action)
    }

    override fun onItemClick(productId: String?) {
        openProductDetailFragment(productId?.toInt() ?: 0)
    }
}