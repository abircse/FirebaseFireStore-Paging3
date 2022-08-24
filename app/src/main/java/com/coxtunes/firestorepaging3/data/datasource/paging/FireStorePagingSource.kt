package com.coxtunes.firestorepaging3.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coxtunes.firestorepaging3.data.dto.Product
import com.coxtunes.firestorepaging3.data.datasource.remote.ProductDataSource
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FireStorePagingSource(private val productDataSource: ProductDataSource) : PagingSource<QuerySnapshot, Product>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Product> {
        return try {

            val products = productDataSource.getProducts()
            val currentPage = params.key ?: products
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = products.query.startAfter(lastVisibleProduct).get().await()
            LoadResult.Page(
                data = currentPage.toObjects(Product::class.java),
                prevKey = null,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Product>): QuerySnapshot? {
        return null
    }

}