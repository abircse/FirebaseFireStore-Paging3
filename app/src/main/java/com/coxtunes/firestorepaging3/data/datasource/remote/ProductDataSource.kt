package com.coxtunes.firestorepaging3.data.datasource.remote

import com.coxtunes.firestorepaging3.common.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    private val db: FirebaseFirestore,
) {
    suspend fun getProducts(): QuerySnapshot {
        delay(1000)
        return db.collection("product")
            .limit(Constants.PAGE_SIZE.toLong())
            .get()
            .await()
    }
}
