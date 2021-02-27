package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.SubCategoryActivity
import com.example.myapplication.app.Config
import com.example.myapplication.models.Data
import com.example.myapplication.models.Data.Companion.KEY_CAT
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout.view.*

class CategoryAdapter(var context: Context) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    var array: ArrayList<Data> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = array[position]
        holder.bind(category)
    }

    fun setData(data: ArrayList<Data>) {
        array = data
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Data) {
            itemView.text_view_name.text = category.catName
            Picasso.get().load("${Config.IMAGE_URL + category.catImage}")
                .into(itemView.image)

            itemView.setOnClickListener{
                var intent = Intent(context, SubCategoryActivity::class.java)
                intent.putExtra(KEY_CAT, category)
                context.startActivity(intent)
            }
        }
    }

}