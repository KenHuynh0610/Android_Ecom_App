package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.AddressActivity
import com.example.myapplication.activities.PaymentActivity
import com.example.myapplication.models.Address
import com.example.myapplication.models.AddressData
import kotlinx.android.synthetic.main.row_address_layout.view.*
import java.io.Serializable

class AddressAdapter(var context: Context) : RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {

    var array: ArrayList<AddressData> = ArrayList()
    var listener: OnClickListener = context as AddressActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_address_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var address = array[position]
        holder.bind(address)
    }

    fun setData(data: ArrayList<AddressData>){
        array = data
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(address: AddressData){
            var flag = false
            itemView.text_view_houseNo.text = address.houseNo + ", "
            itemView.text_view_city.text = address.city+ ", "
            itemView.text_view_street.text = address.streetName+ ", "
            itemView.text_view_zip.text = address.pincode.toString()
            itemView.text_view_type.text = address.type
            itemView.setOnClickListener{
                if(!flag) {
                    itemView.checkbox.isChecked = true
                    flag = true
                    itemView.button_deliver.visibility = View.VISIBLE
                    itemView.button_deliver.setOnClickListener{
                        listener.onClick(address)
                    }
                }
                else {
                    itemView.checkbox.isChecked = false
                }
            }

        }
    }
    interface OnClickListener{
        fun onClick(address: Serializable)
    }
}