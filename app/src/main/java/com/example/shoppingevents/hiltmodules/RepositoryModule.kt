package com.example.shoppingevents.hiltmodules

import com.example.shoppingevents.data.repos.ShoppingEventRepository
import com.example.shoppingevents.data.repos.ShoppingEventRepositoryImpl
import com.example.shoppingevents.data.repos.ShoppingItemRepository
import com.example.shoppingevents.data.repos.ShoppingItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindShoppingEventRepository(
        shoppingEventRepositoryImpl: ShoppingEventRepositoryImpl
    ): ShoppingEventRepository

    @Binds
    abstract fun bindShoppingItemRepository(
        shoppingItemRepositoryImpl: ShoppingItemRepositoryImpl
    ): ShoppingItemRepository

}