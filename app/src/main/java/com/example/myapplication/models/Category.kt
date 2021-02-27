package com.example.myapplication.models

import java.io.Serializable

data class Category(
    val count: Int,
    val data: ArrayList<Data>,
    val error: Boolean
)

data class Data(
    val __v: Int,
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean
): Serializable{
    companion object{
        const val KEY_CAT = "CAT"
    }
}