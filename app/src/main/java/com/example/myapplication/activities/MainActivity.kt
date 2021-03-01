package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.CategoryAdapter
import com.example.myapplication.helpers.DBHelpers
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.Data
import com.example.myapplication.models.Data.Companion.KEY_CAT
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.nav_header_logout.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var adapter: CategoryAdapter
    lateinit var drawer_layout: DrawerLayout
    lateinit var nav_view: NavigationView
    lateinit var sessionManager:SessionManager
    lateinit var dbHelpers: DBHelpers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        setupToolbar()
        dbHelpers = DBHelpers(this)
        nav_view = navView
//        var headerView = nav_view.getHeaderView(0)
        sessionManager = SessionManager(this)
        drawer_layout = drawerLayout

        if(sessionManager.isLoggedIn()) {

            nav_view.menu.clear()
            nav_view.inflateMenu(R.menu.nav_menu)
            var headerView = nav_view.inflateHeaderView(R.layout.nav_header)
            headerView.text_view_guest.text =sessionManager.getUserName()
            headerView.text_view_email.text =sessionManager.getUser()

        }else{
            nav_view.menu.clear()
            nav_view.inflateMenu(R.menu.nav_menu_logout)
            var headerViewGuest = nav_view.inflateHeaderView(R.layout.nav_header_logout)
            headerViewGuest.text_view_guest_logout.text = "Guest"
        }

        nav_view.setNavigationItemSelectedListener(this)

        var toggle = ActionBarDrawerToggle(
            this, drawer_layout, tool_bar, 0, 0
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

//        welcome_text.text = "Welcome ${sessionManager.getUser()}"
        adapter = CategoryAdapter(this)
        getData()

        recycle_view.adapter = adapter
        recycle_view.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

    }

    private fun setupToolbar() {
        var toolbar = tool_bar
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.menu_profile -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
            R.id.menu_setting -> Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
        }
        return true
    }

    private fun getData(){
    var data = intent.getSerializableExtra(KEY_CAT) as ArrayList<Data>
    adapter.setData(data)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

       when(item.itemId) {
           R.id.item_account -> startActivity(Intent(this, AccountActivity::class.java))
           R.id.item_logout ->  {
               sessionManager.logout()
               startActivity(Intent(this, LoginActivity::class.java))
               dbHelpers.deleteAll()
           }
           R.id.item_login -> startActivity(Intent(this, LoginActivity::class.java))
           R.id.item_register -> startActivity(Intent(this, RegisterActivity::class.java))
           R.id.item_orders -> startActivity(Intent(this, OrderActivity::class.java))

       }

        return true
       }


    }
