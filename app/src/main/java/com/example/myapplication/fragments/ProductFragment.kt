package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.activities.ShowProductActivity
import com.example.myapplication.adapters.ProductAdapter
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.DBHelpers
import com.example.myapplication.models.Product
import com.example.myapplication.models.ProductData
import com.example.myapplication.models.SubData.Companion.KEY_SUB_CAT
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_show_product.*
import kotlinx.android.synthetic.main.fragment_product.view.*


class ProductFragment : Fragment() {

    private var subCatID: Int? = null
    lateinit var productAdapter: ProductAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subCatID = it.getInt(KEY_SUB_CAT)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view =  inflater.inflate(R.layout.fragment_product, container, false)
        getProduct()
        var dividerDec = DividerItemDecoration(activity!!, LinearLayout.VERTICAL)
        var dbHelper = DBHelpers(activity!!)

//        add_to_cart.setOnClickListener {
//
//            if (!dbHelper.exists(product.productName)) {
//                dbHelper.addToCart(product)
//                product.quantity++
//                dbHelper.updateQuantity(product.productName, product.quantity)
//                text_view_quantity.text = product.quantity.toString()
//                selectQuantity(product.productName)
//            }
//        }
//        button_increment.setOnClickListener {
//            product.quantity = dbHelper.getQuantity(product.productName)
//            if (dbHelper.exists(product.productName)) {
//                product.quantity++
//                dbHelper.updateQuantity(product.productName, product.quantity)
//                text_view_quantity.text = product.quantity.toString()
//                selectQuantity(product.productName)
//                calculation(product)
//            }
//            else{
//                dbHelper.addToCart(product)
//            }
//        }
//        button_decrement.setOnClickListener {
//            product.quantity = dbHelper.getQuantity(product.productName)
//            if (dbHelper.exists(product.productName) && product.quantity>0) {
//                product.quantity--
//                dbHelper.updateQuantity(product.productName, product.quantity)
//                text_view_quantity.text = product.quantity.toString()
//                selectQuantity(product.productName)
//                calculation(product)
//            }
//        }

        productAdapter = ProductAdapter(requireActivity().applicationContext)
        view.product_recycle_view.adapter = productAdapter
        view.product_recycle_view.layoutManager = LinearLayoutManager(activity!!)
        view.product_recycle_view.addItemDecoration(dividerDec)
        return view
    }

    private fun getProduct() {

        var requestQueue = Volley.newRequestQueue(activity!!)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductBySubID(subCatID),
            
            Response.Listener{
                
                var gson = Gson()
                var response = gson.fromJson(it, Product::class.java)



                productAdapter.setData(response.data)

            },

            Response.ErrorListener {
                Toast.makeText(activity!!, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }



    companion object {
        fun newInstance(subCatID:Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_SUB_CAT, subCatID)
                }
            }
    }
}



