package com.coxtunes.firestorepaging3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.coxtunes.firestorepaging3.data.datasource.paging.FireStorePagingSource
import com.coxtunes.firestorepaging3.data.datasource.remote.ProductDataSource
import com.coxtunes.firestorepaging3.common.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productDataSource: ProductDataSource) :
    ViewModel() {
    val flow = Pager(
        PagingConfig(pageSize = Constants.PAGE_SIZE)
    ) {
        FireStorePagingSource(productDataSource)
    }.flow.cachedIn(viewModelScope)
}