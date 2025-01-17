package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.adapter.CustomerListAdapter
import kotlinx.android.synthetic.main.fragment_customer_list.view.*


/**
 * Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
 */
class CustomerListFragment : Fragment(), IListListener {

    private lateinit var adapter: CustomerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.customerList)

        adapter = CustomerListAdapter(mutableListOf())
        view.recyclerViewCustomerList.adapter = adapter
        view.recyclerViewCustomerList.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        FragmentData.notifyCustomerLogic(this)

        view.buttonCustomerToSearch.setOnClickListener{
            FragmentData.setTextSearched(view.editTextSearchCustomerList.text.toString())
            FragmentData.setControllerOnClickListener(it)
        }
        view.buttonCustomerListToCreateCustomer.setOnClickListener(FragmentData.getController())
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun reloadList(mutableList: MutableList<Any>) {
        adapter.setCustomerList(mutableList as MutableList<Customer>)
        if (isAdded)                        // Is possible that the list is called from another view. It's this occurs
                                            // With this it wont change the view.
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    override fun addElementsToList(mutableList: MutableList<Any>) {
        adapter.getCustomerList().addAll(mutableList as MutableList<Customer>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }
}