package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.adapters.AddressAdapter
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.Address
import com.example.myapplication.models.AddressData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_select_address.view.*
import kotlinx.android.synthetic.main.row_address_layout.view.*
import java.io.Serializable


class SelectAddressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_select_address, container, false)
        var sessionManager = SessionManager(activity!!)
        var url = Endpoints.postAddress() + "/${sessionManager.getUserId()}"

        view.add_address_button.setOnClickListener{
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frame_layout, AddAddressFragment()).commit()
        }

        var addressAdapter = AddressAdapter(activity!!)
        view.recycler_view_address.adapter = addressAdapter
        view.recycler_view_address.layoutManager = LinearLayoutManager(activity!!)



        var queue = Volley.newRequestQueue(activity!!)


        var request = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                var gson = Gson()
                var response = gson.fromJson(it, Address::class.java)
                view.progressBar.visibility = View.GONE
                addressAdapter.setData(response.data)
                Toast.makeText(activity!!, "Succeed", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener{
                Toast.makeText(activity!!, "Failed", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(request)



        return view
    }






}