package com.example.myapplication.models

data class UserRegister(
    var firstName: String,
    var email: String,
    var mobile: String,
    var password: String
) {

    companion object {

        const val KEY_USER = "USER"

    }
}