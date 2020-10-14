package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.Controller
import com.example.stockmanagementsym.presentation.FragmentData
import com.example.stockmanagementsym.presentation.Model
import com.example.stockmanagementsym.presentation.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment(), ListListener {

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CartAdapter(Model.getCartList(), this)
        adapter.notifyDataSetChanged()

        recyclerViewCart.adapter = adapter
        recyclerViewCart.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        FragmentData.setCartListener(this)

        buttonCartToHome.setOnClickListener(Controller)
        buttonCartToProductList.setOnClickListener(Controller)
        buttonCartToNewSale.setOnClickListener(Controller)

    }

    override fun onResume() {
        super.onResume()
        reloadList()
    }

    override fun reloadList() {
        adapter.listProducts = Model.getCartList()
        textViewTotal.text = "Total: ${Model.getTotalPrice()}"
        adapter.notifyDataSetChanged()
    }

    override fun setList(list: MutableList<Any>) {
        adapter.listProducts = list as MutableList<Product>
        adapter.notifyDataSetChanged()
    }
}