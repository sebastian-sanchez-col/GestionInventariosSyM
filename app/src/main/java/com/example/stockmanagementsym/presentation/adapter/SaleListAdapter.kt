package com.example.stockmanagementsym.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.view_holder.SaleListViewHolder


class SaleListAdapter(private var saleList: MutableList<Sale>): RecyclerView.Adapter<SaleListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_sale, parent, false)
        return SaleListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaleListViewHolder, position: Int) {
        holder.bind(saleList[position])
    }

    override fun getItemCount(): Int {
        return saleList.size
    }
    
    fun getSaleList():MutableList<Sale>{
        return saleList
    }
    fun setSaleList(saleList:MutableList<Sale>){
        this.saleList = saleList
    }
}