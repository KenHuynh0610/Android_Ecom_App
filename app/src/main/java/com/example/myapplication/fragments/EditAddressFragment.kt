package com.example.myapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.AddressData
import com.example.myapplication.models.AddressData.Companion.KEY_ADDRESS
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_edit_address.*
import kotlinx.android.synthetic.main.fragment_edit_address.view.*
import kotlinx.android.synthetic.main.row_address_layout.*
import kotlinx.android.synthetic.main.row_address_layout.view.*
import org.json.JSONObject
import java.io.Serializable



/**
 * A simple [Fragment] subclass.
 * Use the [EditAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var address: AddressData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            address = it.getSerializable(KEY_ADDRESS) as AddressData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view  = inflater.inflate(R.layout.fragment_edit_address, container, false)

        var sessionManager = SessionManager(activity!!)

        view.edit_text_street.text.clear()
        view.edit_text_street.text.insert(0, address.streetName)
        view.edit_text_house.text.clear()
        view.edit_text_house.text.insert(0, address.houseNo)
        view.edit_text_zip.text.clear()
        view.edit_text_zip.text.insert(0, address.pincode.toString())
        view.edit_text_city.text.clear()
        view.edit_text_city.text.insert(0, address.city)
        view.edit_text_type.text.clear()
        view.edit_text_type.text.insert(0, address.type)

        var city = view.edit_text_city.text.toString()
        var house = view.edit_text_house.text.toString()
        var street = view.edit_text_street.text.toString()
        var zip = view.edit_text_zip.text.toString().toInt()
        var type = view.edit_text_type.text.toString()
        var address = AddressData(address?.__v, address?._id, city, house, zip, street, type, sessionManager.getUserId())

        var gson = Gson().toJson(address)
        var params = JSONObject(gson)

        var queue = Volley.newRequestQueue(activity!!)
        view.edit_button.setOnClickListener {
            var request = JsonObjectRequest(
                Request.Method.PUT,
                Endpoints.editAddress(address._id),
                params,
                Response.Listener {
                    Toast.makeText(activity!!, "Edited", Toast.LENGTH_SHORT).show()
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SelectAddressFragment()).commit()
                },
                Response.ErrorListener {
                    Toast.makeText(activity!!, "Failed!", Toast.LENGTH_SHORT).show()
                }
            )
            queue.add(request)
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
         * @return A new instance of fragment EditAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(address:Serializable) =
            EditAddressFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_ADDRESS, address)

                }
            }
    }
}