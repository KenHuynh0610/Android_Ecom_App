package com.example.myapplication.helpers

import android.content.Context

import android.content.SharedPreferences
import com.example.myapplication.models.PriceSummary

import com.example.myapplication.models.User
import java.io.Serializable


class SessionManager (var context: Context){
    private val KEY_IS_LOGIN = "isLoggedIn"
    private val KEY_FILE = "user_info"
    private val KEY_EMAIL = "email"
    private val KEY_NAME = "name"
    private val KEY_PASSWORD = "password"
    private val KEY_USER_ID = "id"
    private val KEY_PRICE_TOTAL = "price"
    private val KEY_MRP_TOTAL  = "mrp"
    private val KEY_SAVE_TOTAL  = "save"
    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor

    init{
        sharedPreferences = context.getSharedPreferences(KEY_FILE, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }


    fun loginSuccess(user: User){
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_NAME, user.firstName)
        editor.putString(KEY_PASSWORD, user.password)
        editor.putBoolean(KEY_IS_LOGIN, true)
        editor.putString(KEY_USER_ID, user._id)
        editor.commit()
    }

    fun checkOut(summary: PriceSummary){
        editor.putInt(KEY_PRICE_TOTAL, summary.total)
        editor.putInt(KEY_MRP_TOTAL, summary.totalmrp)
        editor.putInt(KEY_SAVE_TOTAL, summary.discount)
        editor.commit()
    }

    fun getPriceSummary(): Int{
        return sharedPreferences.getInt(KEY_PRICE_TOTAL, 0)
    }

    fun getMRPSummary(): Int{
        return sharedPreferences.getInt(KEY_MRP_TOTAL, 0)
    }
    fun getSaveSummary(): Int{
        return sharedPreferences.getInt(KEY_MRP_TOTAL, 0)
    }

    fun isLoggedIn():Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, false)
    }

    fun getUser():String?{
        return sharedPreferences.getString(KEY_EMAIL,"")
    }

    fun getUserName():String?{
        return sharedPreferences.getString(KEY_NAME, "")
    }

    fun getUserId(): String?{
        return sharedPreferences.getString(KEY_USER_ID,"")
    }

    fun logout(){
        editor.clear()
        editor.commit()
    }
}