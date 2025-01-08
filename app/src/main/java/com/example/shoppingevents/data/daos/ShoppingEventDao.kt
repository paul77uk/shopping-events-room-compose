package com.example.shoppingevents.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingevents.data.entities.ShoppingEvent
import com.example.shoppingevents.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingEventDao {
    @Insert
    suspend fun insert(shoppingEvent: ShoppingEvent)

    @Update
    suspend fun update(shoppingEvent: ShoppingEvent)

    @Delete
    suspend fun delete(shoppingEvent: ShoppingEvent)

    @Query(
        "SELECT e.id, e.name, e.initial_budget, e.event_date, e.completed, " +
                "SUM(i.price * i.quantity) AS total_cost " +
                "FROM shopping_events e " +
                "LEFT JOIN shopping_items i ON e.id = i.event_id " +
                "GROUP BY e.id"
    )
    fun getEvents(): Flow<List<ShoppingEvent>>

    @Query(
        "SELECT se.id, se.name, se.initial_budget, se.event_date, se.completed," +
                "(SELECT SUM(i.price * i.quantity) FROM shopping_items i WHERE i.event_id = se.id) AS total_cost," +
                "i.itemId, i.event_id, i.item_name, i.price, i.quantity " +
                "FROM shopping_events se LEFT JOIN shopping_items i ON se.id = i.event_id " +
                "WHERE se.id = :id"
    )
    fun getEventAndItems(id: Long): Flow<Map<ShoppingEvent, List<ShoppingItem>>>


}