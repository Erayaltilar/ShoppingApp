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