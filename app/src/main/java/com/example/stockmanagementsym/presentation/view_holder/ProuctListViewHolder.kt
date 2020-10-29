package com.example.stockmanagementsym.presentation.view_holder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product) {
        val df = DecimalFormat("$###,###,###")
        itemView.textViewName.text = product.getName()
        itemView.textViewDescription.text = product.getDescription()
        itemView.textViewPrice.text = df.format(product.getPrice())
        itemView.textViewQuantity.text = "Cantidad: ${product.getQuantity()}"
        val userPhotoData = product.getStringBitMap()
        if(userPhotoData.isNotEmpty()){
            if(userPhotoData.length<400){
                try {
                    Picasso.get().load(userPhotoData).into(itemView.imageViewProduct)
                    itemView.imageViewProduct.background = null
                }catch (e:Exception){
                    FragmentData.showToastMessage(itemView.context, ""+e)
                }
            }else{
                itemView.imageViewProduct.setImageBitmap(decoderStringToBitMap(product.getStringBitMap()))
                itemView.imageViewProduct.background = null
            }
        }else{
            itemView.imageViewProduct.setImageBitmap(null)
            itemView.imageViewProduct.setBackgroundResource(R.drawable.ic_image)
        }

        itemView.buttonAddProductToCart.setOnClickListener{
            val quantityString = itemView.editTextQuantity.text.toString()
            if(quantityString.isDigitsOnly()&&quantityString.isNotEmpty()){
                val quantity:Int = quantityString.toInt()
                if(product.idProduct != 0L){
                    if((product.getQuantity() >= quantity) && (quantity > 0)){
                        val cartProduct = Product(
                            name = product.getName(),
                            description = product.getDescription(),
                            price = product.getPrice(),
                            stringBitMap = product.getStringBitMap(),
                            quantity = quantity
                        )
                        cartProduct.idProduct = product.idProduct
                        FragmentData.addProductToCart(cartProduct,it)
                    }else
                        FragmentData.showToastMessage(it.context, "Digite un numero correcto de acuerdo a la cantidad disponible")
                }else{
                    FragmentData.showToastMessage(it.context,"Producto no registrado, redirigiendo")
                    FragmentData.confirmNewProduct(product,itemView)
                }
            }else
                FragmentData.showToastMessage(it.context, "Digite un numero correcto")
        }
        itemView.buttonEditProduct.setOnClickListener {
            FragmentData.updateProduct(product,true, it)
        }
        itemView.buttonDeleteProduct.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                FragmentData.deleteProduct(product)
            }
        }
    }

    private fun decoderStringToBitMap(string: String): Bitmap? {
        val byteArrayFromString = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArrayFromString,0,byteArrayFromString.size)
    }
}