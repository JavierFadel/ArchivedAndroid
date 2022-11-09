package com.example.shopapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

class ProductListAdapter (private val productList : ArrayList<Product>) : RecyclerView.Adapter<ProductListAdapter.ListViewHolder>() {

    // Create holder + convert xml to Kotlin with LayoutInflater
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.product_list, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // Bind data to object
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val product = productList[position]

        Glide.with(holder.itemView.context)
                .load(product.photo)
                .apply(RequestOptions().override(55, 55))
                .into(holder.itemPhoto)

        holder.tvItem.text = product.item
        holder.tvVersion.text = product.version
        holder.tvPrice.text = product.price
    }

    // Declaring object
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItem : TextView = itemView.findViewById(R.id.product_title)
        var tvVersion : TextView = itemView.findViewById(R.id.product_version)
        var itemPhoto : ImageView = itemView.findViewById(R.id.imgItemPhoto)
        var tvPrice : TextView = itemView.findViewById(R.id.pricing)
    }
}