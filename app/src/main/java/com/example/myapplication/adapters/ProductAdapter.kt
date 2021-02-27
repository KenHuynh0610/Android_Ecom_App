package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.ShowProductActivity
import com.example.myapplication.activities.SubCategoryActivity
import com.example.myapplication.app.Config
import com.example.myapplication.models.Data
import com.example.myapplication.models.ProductData
import com.example.myapplication.models.ProductData.Companion.KEY_PRODUCT
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_cart_layout.view.*
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlinx.android.synthetic.main.row_product_layout.view.*
import kotlinx.android.synthetic.main.row_product_layout.view.price
import kotlinx.android.synthetic.main.row_product_layout.view.product_image
import kotlinx.android.synthetic.main.row_product_layout.view.product_name

class ProductAdapter (var context: Context) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    var array: ArrayList<ProductData> = ArrayList()

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
            itemView.product_name.text = product.productName

            itemView.price.text = "$ ${product.price.toString()}"


            Picasso.get().load("${Config.IMAGE_URL + product.image}")
                .into(itemView.product_image)

            itemView.setOnClickListener {
                var intent = Intent(context, ShowProductActivity::class.java)
                intent.putExtra(KEY_PRODUCT, product)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }

    }
}