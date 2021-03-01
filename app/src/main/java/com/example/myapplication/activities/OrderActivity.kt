package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.adapters.OnClickListener
import com.example.myapplication.adapters.OrderAdapter
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.OrderPost
import com.example.myapplication.models.OrderResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        init()
    }

    private fun init() {

        var sessionManager = SessionManager(this)
        var orderAdapter = OrderAdapter(this)
        recycler_view_order.adapter = orderAdapter
        recycler_view_order.layoutManager = LinearLayoutManager(this)


        //TODO: call for order API, implement UI and adapter UI, connect to recycler view adapter, add progress bar
        var queue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getOrder(sessionManager.getUserId()),
            Response.Listener{
                Log.d("abc","${Endpoints.getOrder(sessionManager.getUserId())}")
                var gson = Gson()
                var response = gson.fromJson(it, OrderResponse::class.java)
                for(i in response.data) {
                    orderAdapter.setData(i.products)
                }
            },
            Response.ErrorListener {
                var str = String(it.networkResponse.data)
                text_view_error.text = str
            }
        )
        queue.add(request)


        }

    override fun onClick() {

    }
}
