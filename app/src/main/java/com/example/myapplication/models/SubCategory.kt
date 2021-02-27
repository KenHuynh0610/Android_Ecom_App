package com.example.myapplication.models

data class SubCategory(
    val count: Int,
    val data: ArrayList<SubData>,
    val error: Boolean
)

data class SubData(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    val subName: String
){
    companion object {
        const val KEY_SUB_CAT = "SUB_CAT"
    }
}