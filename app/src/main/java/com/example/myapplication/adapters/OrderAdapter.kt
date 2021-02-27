package com.example.myapplication.adapters
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.app.Config
import com.example.myapplication.models.OrderPost
import com.example.myapplication.models.ProductSummary
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_order_layout.view.*
import kotlinx.android.synthetic.main.row_product_layout.view.*
import kotlinx.android.synthetic.main.row_product_layout.view.product_image
import kotlinx.android.synthetic.main.row_product_layout.view.product_name

class OrderAdapter(var context: Context) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>()  {

var array: ArrayList<ProductSummary> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_order_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var order = array[position]
        holder.bind(order)
    }

    fun setData(data: ArrayList<ProductSummary>){
        array = data
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun bind(order:ProductSummary){
            Picasso.get().load("${Config.IMAGE_URL + order.image}")
                .into(itemView.product_image)
            itemView.product_name.text = order.productName
            itemView.order_quantity.text = "Quantity: ${order.quantity.toString()}"
        }

    }
}