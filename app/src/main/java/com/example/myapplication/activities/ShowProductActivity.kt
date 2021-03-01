package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.example.myapplication.R
import com.example.myapplication.app.Config
import com.example.myapplication.fragments.ProductFragment
import com.example.myapplication.helpers.DBHelpers
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.Data
import com.example.myapplication.models.PriceSummary
import com.example.myapplication.models.PriceSummary.Companion.KEY_DIS_SUM
import com.example.myapplication.models.PriceSummary.Companion.KEY_MRP_SUM
import com.example.myapplication.models.PriceSummary.Companion.KEY_PRICE_SUM
import com.example.myapplication.models.ProductData
import com.example.myapplication.models.ProductData.Companion.KEY_PRODUCT
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_product.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.layout_cart.view.*
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlinx.android.synthetic.main.row_product_layout.view.*

class ShowProductActivity : AppCompatActivity() {
    var dbHelper = DBHelpers(this)
    var textViewCartCount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)

        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {

        setupToolbar()
        updateCart()
        var product = intent.getSerializableExtra(KEY_PRODUCT) as ProductData
        Picasso.get().load("${Config.IMAGE_URL + product.image}")
            .into(show_product_image)
        show_product_name.text = product.productName
        show_product_des.text = product.description
        show_product_price.text = "$${product.price}"
        show_product_mrp.text = "$${product.mrp}"
        show_product_mrp.paintFlags = show_product_mrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        show_save.text= "Save $${product.mrp - product.price}"



        product.quantity = 0
        selectQuantity(product.productName)

        add_to_cart.setOnClickListener {

            if (!dbHelper.exists(product.productName)) {
                dbHelper.addToCart(product)
                product.quantity++
                dbHelper.updateQuantity(product.productName, product.quantity)
                text_view_quantity.text = product.quantity.toString()
                selectQuantity(product.productName)
            }
        }
        button_increment.setOnClickListener {
            product.quantity = dbHelper.getQuantity(product.productName)
            if (dbHelper.exists(product.productName)) {
                product.quantity++
                dbHelper.updateQuantity(product.productName, product.quantity)
                text_view_quantity.text = product.quantity.toString()
                selectQuantity(product.productName)
                calculation(product)
            }
            else{
                dbHelper.addToCart(product)
            }
        }
        button_decrement.setOnClickListener {
            product.quantity = dbHelper.getQuantity(product.productName)
            if (dbHelper.exists(product.productName) && product.quantity>0) {
                product.quantity--
                dbHelper.updateQuantity(product.productName, product.quantity)
                text_view_quantity.text = product.quantity.toString()
                selectQuantity(product.productName)
                calculation(product)
            }
        }



    }

    private fun calculation(product: ProductData){
        var sessionManager = SessionManager(this)
        var totalPrice = product.quantity * product.price
        var discount = (product.mrp - product.price)*product.quantity
        var totalMrp = product.quantity * product.mrp
        var summary = PriceSummary(totalPrice, totalMrp, discount)
        show_product_price.text = "$${totalPrice}"
        show_product_mrp.text = "$${totalMrp}"
        show_save.text = "Save $${discount}"

        sessionManager.checkOut(summary)


    }

    private fun selectQuantity(productName:String){

        var quantity = dbHelper.getQuantity(productName)

        if(quantity == 0){
            add_to_cart.visibility = View.VISIBLE
            layout_button.visibility = View.GONE
            dbHelper.deleteItem(productName)
        }else{
            add_to_cart.visibility = View.GONE
            layout_button.visibility = View.VISIBLE
            text_view_quantity.text = quantity.toString()
            updateCart()
        }

    }

    private fun setupToolbar() {
        var toolbar = tool_bar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        var item = menu?.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.layout_cart)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount=view.text_view_cart_count
        updateCart()

        return true
    }

    private fun updateCart(){
        var count = dbHelper.getNumberOfProducts()

        if(count == 0){
            textViewCartCount?.visibility = View.GONE
        }
        else{
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
        textViewCartCount?.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
    return true
    }
}