package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.adapters.AddressAdapter
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.Address
import com.google.gson.Gson
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.fragment_select_address.*
import kotlinx.android.synthetic.main.fragment_select_address.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}