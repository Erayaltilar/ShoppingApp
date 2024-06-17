package com.example.eray_altilar_final.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eray_altilar_final.databinding.ProductRowBinding
import com.example.eray_altilar_final.domain.model.productmodel.Product

class ProductListAdapter(
    private val productList: MutableList<Product>
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ProductRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val adapterLayout = ProductRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.textView.text = product.title
        holder.binding.productPrice.text = product.price.toString()
        Glide.with(holder.itemView.context).load(product.thumbnail).into(holder.binding.imageView)
    }

    override fun getItemCount(): Int = productList.size

    fun addProducts(newProducts: List<Product>) {
        val oldItemCount = itemCount
        productList.addAll(newProducts)
        notifyItemRangeInserted(oldItemCount, newProducts.size)
    }
}