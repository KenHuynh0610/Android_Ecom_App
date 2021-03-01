package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.CartActivity
import com.example.myapplication.activities.ShowProductActivity
import com.example.myapplication.activities.SubCategoryActivity
import com.example.myapplication.app.Config
import com.example.myapplication.helpers.DBHelpers
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.Data
import com.example.myapplication.models.PriceSummary
import com.example.myapplication.models.ProductData
import com.example.myapplication.models.ProductData.Companion.KEY_PRODUCT
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_product.*
import kotlinx.android.synthetic.main.row_cart_layout.view.*
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlinx.android.synthetic.main.row_product_layout.view.*
import kotlinx.android.synthetic.main.row_product_layout.view.price
import kotlinx.android.synthetic.main.row_product_layout.view.product_image
import kotlinx.android.synthetic.main.row_product_layout.view.product_name

class ProductAdapter (var context: Context) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    var listener: OnClickProductListener = context as SubCategoryActivity
    var array: ArrayList<ProductData> = ArrayList()
    var dbHelper = DBHelpers(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_product_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = array[position]
        holder.bind(product)
    }

    fun setData(productData: ArrayList<ProductData>) {
        array = productData
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(product: ProductData) {
            itemView.product_mrp.paintFlags = itemView.product_mrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            itemView.product_name.text = product.productName
            itemView.price.text = "$ ${product.price.toString()}"


            Picasso.get().load("${Config.IMAGE_URL + product.image}")
                .into(itemView.product_image)

            product.quantity = 0
            displayBasedOnQuantity(product.productName)

            itemView.add_product_to_cart.setOnClickListener {
                if (!dbHelper.exists(product.productName)) {
                    dbHelper.addToCart(product)
                    product.quantity++
                    dbHelper.updateQuantity(product.productName, product.quantity)
                    itemView.text_view_quantity_product.text = product.quantity.toString()
                    displayBasedOnQuantity(product.productName)
                }
            }

            itemView.button_increment_product.setOnClickListener {
                product.quantity = dbHelper.getQuantity(product.productName)
                if (dbHelper.exists(product.productName)) {
                    product.quantity++
                    dbHelper.updateQuantity(product.productName, product.quantity)
                    itemView.text_view_quantity_product.text = product.quantity.toString()
                    displayBasedOnQuantity(product.productName)
                    calculation(product)
                }
                else{
                    dbHelper.addToCart(product)
                }
            }
            itemView.button_decrement_product.setOnClickListener {
                product.quantity = dbHelper.getQuantity(product.productName)
                if (dbHelper.exists(product.productName) && product.quantity>0) {
                    product.quantity--
                    dbHelper.updateQuantity(product.productName, product.quantity)
                    itemView.text_view_quantity_product.text = product.quantity.toString()
                    displayBasedOnQuantity(product.productName)
                    calculation(product)
                }
            }

            itemView.save_for_later_button.setOnClickListener {

                dbHelper.addToCart(product)
                dbHelper.updateQuantity(product.productName, 0)
                var product = dbHelper.getSaveforLater()
                for(i in product)
                Log.d("abc", "$i")
            }


            itemView.setOnClickListener {
                var intent = Intent(context, ShowProductActivity::class.java)
                intent.putExtra(KEY_PRODUCT, product)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
        private fun calculation(product: ProductData){
            var sessionManager = SessionManager(context)
            var totalPrice = product.quantity * product.price
            var discount = (product.mrp - product.price)*product.quantity
            var totalMrp = product.quantity * product.mrp
            var summary = PriceSummary(totalPrice, totalMrp, discount)
            itemView.price.text = "$${totalPrice}"
            itemView.product_mrp.text = "$${totalMrp}"
            itemView.product_discounnt.text = "-$${discount}"

            sessionManager.checkOut(summary)


        }
        private fun displayBasedOnQuantity(productName:String){

            var quantity = dbHelper.getQuantity(productName)

            if(quantity == 0){
                itemView.add_product_to_cart.visibility = View.VISIBLE
                itemView.layout_button_product.visibility = View.GONE
                dbHelper.deleteItem(productName)

            }else{
                itemView.add_product_to_cart.visibility = View.GONE
                itemView.layout_button_product.visibility = View.VISIBLE
                itemView.text_view_quantity_product.text = quantity.toString()
                listener.onClick()
            }

        }



    }
    interface OnClickProductListener {
        fun onClick()
    }
}


