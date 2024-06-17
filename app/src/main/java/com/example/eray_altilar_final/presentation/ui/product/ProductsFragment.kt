package com.example.eray_altilar_final.presentation.ui.product

import ProductScreen
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.core.PaginationScrollListener
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.viewBinding
import com.example.eray_altilar_final.databinding.FragmentProductsBinding
import com.example.eray_altilar_final.presentation.ui.cart.CartScreen
import com.example.eray_altilar_final.presentation.ui.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Products"
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                ProductScreen(1,viewModel)
            }
        }
    }
}


/*
@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val viewModel: ProductsViewModel by viewModels()
    private val binding by viewBinding(FragmentProductsBinding::bind)
    private lateinit var adapter: ProductListAdapter

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        getProducts()
    }
    private fun getProducts() {
        lifecycleScope.launch {
            viewModel.products.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data?.products?.isNotEmpty() == true) {
                            println(it.data.products.size)
                            adapter.addProducts(it.data.products)
                            isLoading = false
                        } else {
                            isLastPage = true
                        }
                    }

                    is Resource.Error -> {
                        isLoading = false
                    }

                    is Resource.Loading -> {
                        isLoading = true
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductListAdapter(mutableListOf())

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter

            recyclerView.setOnClickListener {
               viewModel.addProductToCart(1, 1)
            }

            recyclerView.addOnScrollListener(object : PaginationScrollListener(recyclerView.layoutManager as LinearLayoutManager) {
                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

                override fun loadMoreItems() {
                    isLoading = true
                    viewModel.loadProducts()
                }
            })
        }
    }
}


 */