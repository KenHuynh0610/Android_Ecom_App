package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.Address
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_address.view.*
import kotlinx.android.synthetic.main.fragment_add_address.*
import kotlinx.android.synthetic.main.fragment_add_address.view.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddAddressFragment : Fragment() {
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
        var view =  inflater.inflate(R.layout.fragment_add_address, container, false)


        view.add_button.setOnClickListener {

//            var queue = Volley.newRequestQueue(activity!!)
//            var houseNo = view.edit_text_house.text.toString()
//            var streetNo = view.edit_text_street.text.toString()
//            var type = view.edit_text_type.text.toString()
//            var city = view.edit_text_city.text.toString()
//            var zip = view.edit_text_zip.text.toString().toInt()
//            var sessionManager = SessionManager(activity!!)
//            var userId = sessionManager.getUserId()
//            Log.d("abc", "$userId")
//
//            var address = Address(houseNo, streetNo, type, city, zip, userId)
//            var gson = Gson().toJson(address, Address::class.java)
//            var params = JSONObject(gson)
//            var request = JsonObjectRequest(
//                Request.Method.POST,
//                Endpoints.postAddress(),
//                params,
//                Response.Listener {
//                    Toast.makeText(activity!!, "Address Posted!", Toast.LENGTH_LONG).show()
//
//                },
//                Response.ErrorListener {
//                    var str = String(it.networkResponse.data)
//                    view.status.text = str
//                }
//
//            )
//            queue.add(request)
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SelectAddressFragment()).commit()


        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}