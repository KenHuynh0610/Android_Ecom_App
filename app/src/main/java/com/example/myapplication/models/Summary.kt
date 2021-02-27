package com.example.myapplication.models

data class OrderResponse(
    val count: Int,
    val data: ArrayList<OrderPost>,
    val error: Boolean
)

data class OrderPost(
    val __v: Int?,
    val _id: String?,
    val date: String?,
    val orderSummary: OrderSummary,
    val products: ArrayList<ProductSummary>,
    val shippingAddress: ShippingAddress,
    val user: UserInfo,
    val userId: String?
)

data class OrderSummary(
    val _id: String?,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class ProductSummary(
    val _id: String,
    val image: String,
    val mrp: Int,
    val price: Int,
    val productName: String,
    val quantity: Int
)

data class ShippingAddress(
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String
)

data class UserInfo(
    val _id: String?,
    val email: String?,
    val name: String?
)