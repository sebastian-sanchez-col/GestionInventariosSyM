package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.DialogObject
import com.example.stockmanagementsym.presentation.Model
import kotlinx.android.synthetic.main.item_customer.view.*

class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Customer) {
        itemView.textViewName.text = item.getName()
        itemView.textViewAddress.text = item.getAddress()
        itemView.textViewPhone.text = "Telefono: ${item.getPhone()}"
        itemView.textViewCity.text = "Ciudad: ${item.getCity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonEditCustomer.setOnClickListener{
            Model.setCustomerToEdit(item)
            DialogObject.dialogNewCustomer(itemView,false)
        }
    }
}