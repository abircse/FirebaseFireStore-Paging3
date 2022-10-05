package com.coxtunes.firestorepaging3.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coxtunes.firestorepaging3.R
import com.coxtunes.firestorepaging3.data.dto.Product

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.Holder>() {

    private val productList: MutableList<Product> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addProducts(productLists: List<Product>) {
        productList.clear()
        productList.addAll(productLists)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false
        )
        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, @SuppressLint("RecyclerView") position: Int) {
        val product = productList[position]
        product.let {
            it.name.let { data ->
                holder.productName.text = data
            }
            holder.itemView.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.textView)
    }
}
