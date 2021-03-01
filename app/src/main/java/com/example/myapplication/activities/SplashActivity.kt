package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.adapters.CategoryAdapter
import com.example.myapplication.app.Endpoints
import com.example.myapplication.models.Category
import com.example.myapplication.models.Data.Companion.KEY_CAT
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.content_main.*

class SplashActivity : AppCompatActivity() {
    lateinit var categoryAdapter:CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            Response.Listener {
                progress_bar.visibility = View.GONE

                var gson = Gson()
                var response = gson.fromJson(it, Category::class.java)

                var  intent = Intent(this, MainActivity::class.java)
                intent.putExtra(KEY_CAT,response.data)
                startActivity(intent)
                finish()
            },
            Response.ErrorListener{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

//                var errorShow= String(it.networkResponse.data)
//                error.text = errorShow
            }
        )
        requestQueue.add(request)
    }
}