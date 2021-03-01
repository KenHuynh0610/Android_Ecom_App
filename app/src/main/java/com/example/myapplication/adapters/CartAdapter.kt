package com.example.myapplication.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.CartActivity
import com.example.myapplication.app.Config
import com.example.myapplication.helpers.DBHelpers
import com.example.myapplication.models.ShowProduct
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_cart_layout.view.*
import kotlinx.android.synthetic.main.row_product_layout.view.price
import kotlinx.android.synthetic.main.row_product_layout.view.product_image
import kotlinx.android.synthetic.main.row_product_layout.view.product_name

class CartAdapter(var context: Context, var array: ArrayList<ShowProduct>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    var db = DBHelpers(context)
    var listener: OnClickListener? = context as CartActivity
    var subTotal = 0
    var discount = 0
    var orderAmount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_cart_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = array[position]
        holder.bind(product, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: ShowProduct, position: Int) {

            itemView.product_name.text = product.name

            itemView.price.text = "$ ${product.price * product.quantity}"


            Picasso.get().load("${Config.IMAGE_URL + product.image}")
                .into(itemView.product_image)

            itemView.delete_button.setOnClickListener {
                array.removeAt(position)
                db.deleteItem(product.name)
                notifyDataSetChanged()
            }
            itemView.text_view_quantity.text = "${db.getQuantity(product.name)}"
            itemView.button_increment.setOnClickListener {
                if (db.exists(product.name)) {
                    product.quantity++
//                    db.updateQuantity(product.name, product.quantity)
//                    itemView.text_view_quantity.text = product.quantity.toString()
//                    itemView.price.text = "$${product.quantity * product.price}"
//                    listener?.onClick()
                    updateAndDisplay(product)
                    notifyDataSetChanged()
                }
            }
            itemView.button_decrement.setOnClickListener {
                if (db.exists(product.name) && product.quantity > 0) {
                    product.quantity--
//                    db.updateQuantity(product.name, product.quantity)
//                    itemView.text_view_quantity.text = product.quantity.toString()
//                    itemView.price.text = "$${product.quantity * product.price}"
//                    listener?.onClick()
                    updateAndDisplay(product)
                    notifyDataSetChanged()
                }
            }
        }

        fun updateAndDisplay(product: ShowProduct){
            db.updateQuantity(product.name, product.quantity)
            itemView.text_view_quantity.text = product.quantity.toString()
            itemView.price.text = "$${product.quantity * product.price}"
            listener?.onClick()
        }
    }

    fun calculateTotal(){
        subTotal = 0
        orderAmount = 0
        discount  = 0
        for(i in array){
            subTotal += (i.mrp * db.getQuantity(i.name))
            orderAmount += (i.price * db.getQuantity(i.name))
            discount += ((i.mrp - i.price)*db.getQuantity(i.name))
        }
    }

    interface OnClickListener {
        fun onClick()
    }



}
