package com.example.myapplication.models
import java.io.Serializable
data class Address(
    val count: Int,
    val data: ArrayList<AddressData>,
    val error: Boolean
)

data class AddressData(
    val __v: Int?,
    val _id: String?,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String?
): Serializable{
    companion object{
        const val KEY_ADDRESS = "ADDRESS"
    }
}