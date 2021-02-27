package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.CartAdapter

import com.example.myapplication.adapters.ProductAdapter
import com.example.myapplication.helpers.DBHelpers
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.row_cart_layout.*

class CartActivity : AppCompatActivity(), CartAdapter.OnClickListener {
    lateinit var cartAdapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        init()
        updateSummary()
    }

    private fun init() {
        tool_bar.title = "Cart"
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var dbHelpers = DBHelpers(this)
        var product = dbHelpers.getAllProduct()
        cartAdapter = CartAdapter(this, product)

        checkout_button.setOnClickListener{
            startActivity(Intent(this, AddressActivity::class.java))
        }

        cart_recycle_view.adapter = cartAdapter
        cart_recycle_view.layoutManager = LinearLayoutManager(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    fun updateSummary(){
        cartAdapter.calculateTotal()
        text_view_mrp_total.text = "Sub Total:  $${cartAdapter.subTotal}"
        text_view_price.text = "Order Amount:  $${cartAdapter.orderAmount}"
        text_view_save.text = "You Save: $${cartAdapter.discount}"
    }


    override fun onClick() {
        updateSummary()
    }
}