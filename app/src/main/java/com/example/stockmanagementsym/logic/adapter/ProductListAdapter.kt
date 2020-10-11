package com.example.stockmanagementsym.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.model.business.Product
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.view_holder.ProductViewHolder


class ProductsListAdapter(var listProducts: List<Product>): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listProducts[position])
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }
}