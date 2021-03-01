package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.activities.AddressActivity
import com.example.myapplication.activities.PaymentActivity
import com.example.myapplication.app.Endpoints
import com.example.myapplication.models.Address
import com.example.myapplication.models.AddressData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.row_address_layout.view.*
import org.json.JSONObject
import java.io.Serializable

class AddressAdapter(var context: Context) : RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {

    var array: ArrayList<AddressData> = ArrayList()
    var listener: OnClickListener = context as AddressActivity
    var fragmentListener: OnFragmentClickListener = context as AddressActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_address_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var address = array[position]
        holder.bind(address,position)
    }

    fun setData(data: ArrayList<AddressData>){
        array = data
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var flag = false
        fun bind(address: AddressData, position:Int) {

            var queue = Volley.newRequestQueue(context)
            itemView.text_view_houseNo.text = address.houseNo + ", "
            itemView.text_view_city.text = address.city + ", "
            itemView.text_view_street.text = address.streetName + ", "
            itemView.text_view_zip.text = address.pincode.toString()
            itemView.text_view_type.text = address.type + ", "

            itemView.checkbox.setOnClickListener {
               buttonDisplay(address)
            }

            itemView.setOnClickListener {
                buttonDisplay(address)
            }

                itemView.button_edit_address.setOnClickListener {
                    fragmentListener.onFragmentClick(address)
                }
            var gson = Gson().toJson(address)
            var params = JSONObject(gson)

            itemView.button_delete_address.setOnClickListener {
                array.removeAt(position)
                notifyDataSetChanged()
                var requestDelete = JsonObjectRequest(
                    Request.Method.DELETE,
                    Endpoints.editAddress(address._id),
                    params,
                    Response.Listener{
                        Toast.makeText(context, "Delete Successfully", Toast.LENGTH_LONG).show()
                    },
                    Response.ErrorListener {
                        Toast.makeText(context, "Delete failed", Toast.LENGTH_LONG).show()
                    }

                )
                queue.add(requestDelete)

            }

            }
        fun buttonDisplay(address: AddressData){
            if (!flag) {
                itemView.checkbox.isChecked = true
                flag = true
                itemView.button_layout.visibility = View.VISIBLE

                itemView.button_deliver.setOnClickListener {
                    listener.onClick(address)
                }
            } else {
                flag = false
                itemView.button_layout.visibility = View.GONE
                itemView.checkbox.isChecked = false
            }
        }
        }

    interface OnClickListener{
        fun onClick(address: Serializable)

    }
    interface OnFragmentClickListener {
        fun onFragmentClick(address: Serializable)
    }

}

