package com.example.stockmanagementsym.logic.classes

import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.logic.ProductRESTLogic
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.list_manager.IListManager
import com.example.stockmanagementsym.presentation.fragment.ICart
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class ProductLogic : AbstractListLogic() {

    private var productRestLogic: ProductRESTLogic? = null
    private lateinit var iCart: ICart

    override fun loadData() {
        doAsyncResult {
            if (elementList.isEmpty()) {
                elementList = productLogicDao.selectProductList().toMutableList()
            }
            uiThread {
                iListManager?.reloadList(elementList.toMutableList())
                iCart.setProductList(elementList)
            }
        }
        if (!listRESTLoaded)
            doAsync {
                getRESTLogic().searchProductList()
                addProductListREST(getRESTLogic().getProductList())
                listRESTLoaded = true
            }
    }

    override fun insert(element: Any) {
        doAsyncResult {
            try {
                productLogicDao.insert(element as Product)
                uiThread {
                    doAsyncResult {
                        updateMutableList()
                        notifyUserTransactionSuccess()
                    }
                }
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun update(element: Any) {
        val thread = doAsyncResult {
            try {
                productLogicDao.update(element as Product)
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
        if (thread.isDone)
            doAsyncResult {
                updateMutableList()
                notifyUserTransactionSuccess()
                uiThread {
                    if (notifyUserTransaction)
                        notifyUserTransactionSuccess()
                }
            }
    }

    override fun updateMutableList() {
        doAsyncResult {
            elementList = productLogicDao.selectProductList().toMutableList()
            uiThread {
                elementList.addAll(listREST)
                iCart.setProductList(elementList)
                if (notifyUserTransaction)
                    notifyUserTransactionSuccess()
            }
        }
    }

    override fun delete(element: Any) {
        doAsyncResult {
            try {
                productLogicDao.delete(element as Product)
                uiThread {
                    doAsyncResult {
                        updateMutableList()
                        notifyUserTransactionSuccess()
                    }
                }
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun searchTextInMutableList(searchedText: String) {
        val listSearched = (elementList as MutableList<Product>).filter {
            it.getName().toLowerCase().contains(searchedText.toLowerCase())
        }
        iListManager?.reloadList(listSearched.toMutableList())
    }

    override fun searchByIDInMutableList(id: Long): Product {
        return (elementList as MutableList<Product>).filter { it.idProduct == id }[0]
    }

    override fun setListManager(iListManager: IListManager) {
        this.iListManager = iListManager
    }

    override fun setCartListener(iCart: ICart) {
        this.iCart = iCart
    }

    override fun decreaseMutableListQuantity(mutableList: MutableList<Any>) {
        try {
            doAsync {
                notifyUserTransaction = false
                (mutableList as MutableList<Product>).forEach {
                    val product = searchByIDInMutableList(it.idProduct)
                    product.setQuantity(product.getQuantity() - it.getQuantity())
                    update(product)
                }
                iListManager?.showToastMessage(MESSAGES.PRODUCT_LIST_UPDATE_SUCCESS)
                notifyUserTransaction = true
            }
        } catch (e: Exception) {
            iListManager?.showToastMessage(MESSAGES.PRODUCT_LIST_UPDATE_FAILURE)
        }
    }

    private fun addProductListREST(list: List<Product>) {
        listREST = list
        elementList.addAll(list)
        iCart.setProductList(elementList)
    }

    private fun getRESTLogic(): ProductRESTLogic {
        if (productRestLogic == null)
            productRestLogic = ProductRESTLogic(iListManager)
        return productRestLogic!!
    }
}