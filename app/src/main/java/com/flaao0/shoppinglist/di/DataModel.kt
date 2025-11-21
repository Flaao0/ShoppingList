package com.flaao0.shoppinglist.di

import android.app.Application
import com.flaao0.shoppinglist.data.AppDatabase
import com.flaao0.shoppinglist.data.ShopListDao
import com.flaao0.shoppinglist.data.ShopListRepositoryImpl
import com.flaao0.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModel {

    @Binds
    @ApplicationScope
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {


        @Provides
        @ApplicationScope
        fun provideShopListDao(
            application: Application
        ): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}