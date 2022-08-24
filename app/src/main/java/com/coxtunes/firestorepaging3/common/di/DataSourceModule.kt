package com.coxtunes.firestorepaging3.common.di

import com.coxtunes.firestorepaging3.data.datasource.remote.ProductDataSource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideProductsDataSource(
        db: FirebaseFirestore,
    ): ProductDataSource {
        return ProductDataSource(db)
    }
}
