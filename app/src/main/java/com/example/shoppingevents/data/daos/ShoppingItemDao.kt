package com.example.shoppingevents.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingevents.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Insert
    suspend fun insert(shoppingItem: ShoppingItem)

    @Update
    suspend fun update(shoppingItem: ShoppingItem)

    @Delete
    suspend fun delete(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun getItems(): Flow<List<ShoppingItem>>
}