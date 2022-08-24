package com.coxtunes.firestorepaging3.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.coxtunes.firestorepaging3.R
import com.coxtunes.firestorepaging3.ui.adapter.ProductLoadStateAdapter
import com.coxtunes.firestorepaging3.ui.adapter.ProductPagingAdapter
import com.coxtunes.firestorepaging3.databinding.ActivityMainBinding
import com.coxtunes.firestorepaging3.ui.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<ProductViewModel>()
    var productAdapter = ProductPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        setupObservers()
    }

    private fun initViews() {

        /** Config RecyclerViews **/
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter.withLoadStateFooter(ProductLoadStateAdapter())
        }

        /** Load Data **/
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                productAdapter.submitData(lifecycle = lifecycle, pagingData = it)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            productAdapter.loadStateFlow.map { it.refresh }.distinctUntilChanged()
                .collect { LoadState ->
                    when (LoadState) {
                        is LoadState.Loading -> {
                            binding.recyclerview.isVisible = false
                            binding.progressBar.isVisible = true
                        }
                        is LoadState.NotLoading -> {
                            binding.progressBar.isVisible = false
                            binding.recyclerview.isVisible = true
                        }
                        is LoadState.Error -> {
                            binding.progressBar.isVisible = false
                            binding.recyclerview.isVisible = false
                        }
                    }
                }
        }
    }
}