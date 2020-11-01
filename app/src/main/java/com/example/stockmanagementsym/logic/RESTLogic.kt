package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.network.RetrofitInstance
import com.example.stockmanagementsym.data.network.apis.APIService
import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.NotifierView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RESTLogic(private val listener: ListListener, private val notifierView: NotifierView) {
    fun getProductList() {
        doAsync{
            val callRetrofit =
                RetrofitInstance()
                    .getInstance()
                    .create(APIService::class.java)
                    .getProductList()
                    .execute()
            uiThread{
                if(callRetrofit.isSuccessful){
                    val productList = callRetrofit.body()?.productList?: listOf()
                    listener.addElementsToList(productList.toMutableList())
                    notifierView.showAlertMessage(R.string.titleProductListRestAdded,
                                                    R.string.messageProductListRestAdded)
                }else{
                    notifierView.showToastMessage(callRetrofit.message())
                }
            }
        }
    }
}
