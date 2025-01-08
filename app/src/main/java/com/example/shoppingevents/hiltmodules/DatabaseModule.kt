package com.example.shoppingevents.hiltmodules

import android.content.Context
import com.example.shoppingevents.data.ShoppingDatabase
import com.example.shoppingevents.data.daos.ShoppingEventDao
import com.example.shoppingevents.data.daos.ShoppingItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideShoppingEventDao(@ApplicationContext context: Context): ShoppingEventDao {
        return ShoppingDatabase.getDatabase(context).shoppingEventDao()
    }

    @Provides
    fun provideShoppingItemDao(@ApplicationContext context: Context): ShoppingItemDao {
        return ShoppingDatabase.getDatabase(context).shoppingItemDao()
    }

}