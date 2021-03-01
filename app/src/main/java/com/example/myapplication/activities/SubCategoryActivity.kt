package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.adapters.FragmentAdapter

import com.example.myapplication.adapters.ProductAdapter
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.DBHelpers
import com.example.myapplication.models.Category
import com.example.myapplication.models.Data
import com.example.myapplication.models.Data.Companion.KEY_CAT
import com.example.myapplication.models.SubCategory
import com.example.myapplication.models.SubData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.layout_cart.view.*

class SubCategoryActivity : AppCompatActivity(),
    ProductAdapter.OnClickProductListener {

    var subCategory:ArrayList<SubCategory> = ArrayList()
    lateinit var fragmentAdapter:FragmentAdapter
    var dbHelper = DBHelpers(this)
    var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        init()
    }

    private fun init() {


        var category = intent.getSerializableExtra(KEY_CAT) as Data
        setupToolbar(category)
        getSubData(category)

        fragmentAdapter = FragmentAdapter(supportFragmentManager, tab_layout)
        view_pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(view_pager)



    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        var item = menu?.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.layout_cart)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_cart_count
        updateCart()
        return true
    }


    private fun setupToolbar(category: Data) {
        var toolbar = tool_bar
        toolbar.title = category.catName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
            R.id.menu_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.menu_profile -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
            R.id.menu_setting -> Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
        }
        return true
    }

    private fun getSubData(category:Data){


        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(category.catId),
            Response.Listener {
                var gson = Gson()
                var response = gson.fromJson(it, SubCategory::class.java)

                for(i in response.data) {
                    fragmentAdapter.addFragment(i)
                }
            },
            Response.ErrorListener{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
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

    override fun onClick() {
        updateCart()
    }
}



