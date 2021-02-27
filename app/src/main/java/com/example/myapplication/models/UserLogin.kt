package com.example.myapplication.models

data class UserLogin(var email: String, var password: String){
    companion object {

        const val KEY_LOGIN = "LOGIN"
    }
}