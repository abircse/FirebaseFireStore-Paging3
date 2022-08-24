package com.coxtunes.firestorepaging3.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coxtunes.firestorepaging3.R

class ProductLoadStateAdapter : LoadStateAdapter<ProductLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.loading_layout, parent, false)
        return LoadStateViewHolder(view)
    }

    class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val loadingLayout = itemView.findViewById<ConstraintLayout>(R.id.loadingLayout)
        fun bind(loadState: LoadState) {
            loadingLayout.isVisible = loadState is LoadState.Loading
        }
    }
}