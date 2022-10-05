package com.coxtunes.firestorepaging3.ui

import android.os.Bundle
import android.view.View
import android.view.View.OnScrollChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coxtunes.firestorepaging3.R
import com.coxtunes.firestorepaging3.data.dto.Product
import com.coxtunes.firestorepaging3.databinding.ActivityMain2Binding
import com.coxtunes.firestorepaging3.ui.adapter.ProductAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity2 : AppCompatActivity() {


    private lateinit var binding: ActivityMain2Binding
    private var productAdapter = ProductAdapter()
    var limit = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        lifecycleScope.launch {
            loadProduct(limit)
        }

        /** Config RecyclerViews **/
        binding.recyclerview2.apply {
            layoutManager = LinearLayoutManager(this@MainActivity2)
            adapter = productAdapter
        }

        binding.buttonPaging.setOnClickListener {
            limit += limit
            lifecycleScope.launch {
                loadProduct(limit)
            }
        }

        binding.recyclerview2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy > 0)
                {
                    binding.buttonPaging.visibility = View.VISIBLE
                }
                else if (!recyclerView.canScrollVertically(-1) && dy < 0)
                {
                    binding.buttonPaging.visibility = View.GONE
                }

            }
        })
    }

    private suspend fun loadProduct(limit: Int) {
        val productList: MutableList<Product> = arrayListOf()
        FirebaseFirestore.getInstance().collection("product")
            .limit(limit.toLong())
            .get()
            .await().map { data ->
                val productData = data.toObject(Product::class.java)
                productList.add(productData)
                binding.buttonPaging.visibility = View.GONE
            }

        productAdapter.addProducts(productList)
    }
}