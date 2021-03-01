package com.example.myapplication.app

import com.example.myapplication.helpers.SessionManager

class Endpoints {

    companion object {

        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val URL_PRODUCT = "products/sub"
        private const val URL_REGISTER = "auth/register"
        private const val URL_LOGIN = "auth/login"
        private const val URL_ADDRESS = "address"
        private const val URL_ORDER = "orders/"
        private const val URL_EDIT_ADDRESS = "address/"

        fun getCategory(): String {
            return "${Config.BASE_URL + URL_CATEGORY}"
        }

        fun getSubCategoryByCatId(catId: Int): String {
            return "${Config.BASE_URL + URL_SUB_CATEGORY + catId}"
        }

        fun getProductBySubID(subId: Int?): String {
            return "${Config.BASE_URL + URL_PRODUCT}/$subId"
        }

        fun getRegister(): String {
            return "${Config.BASE_URL + URL_REGISTER}"
        }

        fun getLogin(): String {
            return "${Config.BASE_URL + URL_LOGIN}"
        }

        fun postAddress(): String {
            return "${Config.BASE_URL + URL_ADDRESS}"
        }

        fun postOrder(): String {
            return "${Config.BASE_URL + URL_ORDER}"
        }

        fun getOrder(userId: String?): String{
            return "${Config.BASE_URL + URL_ORDER + userId}"
        }

        fun editAddress(addressID: String?): String{
            return "${Config.BASE_URL + URL_EDIT_ADDRESS + addressID}"
        }

    }
}

