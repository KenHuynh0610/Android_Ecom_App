package com.example.myapplication.models
import java.io.Serializable
class PriceSummary(var total: Int, var totalmrp: Int, var discount: Int): Serializable {

    companion object{
        const val KEY_PRICE_SUM = "SUM"
        const val KEY_MRP_SUM = "MRPSUM"
        const val KEY_DIS_SUM = "DIS"
    }
}