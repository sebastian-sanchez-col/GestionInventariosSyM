package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.LoginActivity
import com.example.stockmanagementsym.R
import kotlinx.android.synthetic.main.fragment_new_product.view.*

/*
    Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
*/
class NewProductFragment: Fragment(){

    private lateinit var viewElement:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewElement = view
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.product)

        if(FragmentData.getBooleanUpdate()){
            val product = FragmentData.getProductToEdit()
            view.textViewTitleProduct.text = getString(R.string.titleAlertUpdateProd)
            view.editTextProductName.setText(product.getName())
            view.editTextProductPrice.setText(product.getPrice().toString())
            view.editTextProductDesc.setText(product.getDescription())
            view.editTextProductQuantity.setText(product.getQuantity().toString())

            FragmentData.paintPhoto(
                product.getStringBitMap(),
                view.imageViewNewProduct,
                0
            )
            view.imageViewNewProduct.visibility = View.VISIBLE
        }else{
            view.textViewTitleProduct.text = getString(R.string.titleAlertNewProd)
        }

        view.buttonNewProductToHome.setOnClickListener (FragmentData.getController())
        view.buttonNewProductToProductList.setOnClickListener (FragmentData.getController())
        view.buttonNewProduct.setOnClickListener (FragmentData.getController())
        view.buttonNewProductToGallery.setOnClickListener{
            FragmentData.setNewProductImageView(view.imageViewNewProduct)
            FragmentData.setControllerOnClickListener(it)
        }
        view.buttonNewProductToCamera.setOnClickListener{
            FragmentData.setNewProductImageView(view.imageViewNewProduct)
            FragmentData.setControllerOnClickListener(it)
        }
    }

}
