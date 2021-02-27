package com.example.myapplication.models

import java.io.Serializable

data class Product(
    val count: Int,
    val data: ArrayList<ProductData>,
    val error: Boolean
)

data class ShowProduct(
    val productId: String,
    val name: String,
    val price: Int,
    var quantity: Int = 0,
    val image: String,
    var mrp: Int
)

data class ProductData(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val created: String,
    val description: String,
    val image: String,
    val mrp: Int,
    val position: Int,
    val price: Int,
    val productName: String,
    var quantity: Int = 0,
    val status: Boolean,
    val subId: Int,
    val unit: String
):Serializable{
    companion object{
        const val KEY_PRODUCT = "PRODUCT"
    }
}