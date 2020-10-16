package com.example.stockmanagementsym.data.dao

import androidx.room.*
import com.example.stockmanagementsym.logic.business.Customer

@Dao
interface CustomerDao {

    @Insert
    fun insert(customer: Customer)

    @Delete
    fun delete(customer: Customer)

    @Update
    fun update(customerToEdit: Customer, customerEdited: Customer)

    @Query("SELECT * FROM CUSTOMER")
    fun select():List<Customer>

}

