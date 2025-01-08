package com.example.shoppingevents.ui.eventdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.shoppingevents.data.entities.ShoppingItem
import com.example.shoppingevents.data.repos.ShoppingEventRepository
import com.example.shoppingevents.data.repos.ShoppingItemRepository
import com.example.shoppingevents.destinations.EventDetailsRoute
import com.example.shoppingevents.ui.addevent.AddEventDetails
import com.example.shoppingevents.ui.addevent.toAddEventDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val shoppingEventRepository: ShoppingEventRepository,
    private val itemRepository: ShoppingItemRepository
) : ViewModel() {
    private val detailsRoute: EventDetailsRoute = savedStateHandle.toRoute<EventDetailsRoute>()

    private val _eventDetailsUiState = MutableStateFlow(EventDetailsUiState())
    val eventDetailsUiState = _eventDetailsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            shoppingEventRepository.getEventAndItems(detailsRoute.id).collect { map ->
                Log.d("EventDetailsViewModel", map.toString())
                _eventDetailsUiState.update {
                    val entry = map.entries.firstOrNull()
                    it.copy(
                        eventDetails = entry?.key?.toAddEventDetails()
                            ?: AddEventDetails(name = detailsRoute.name),
                        itemList = entry?.value?.map { item ->
                            ItemUiState(itemDetails = item.toItemDetails())
                        } ?: emptyList()
                    )
                }
            }
        }
    }

    fun updateItemUiState(itemDetails: ItemDetails) {
        _eventDetailsUiState.update { state ->
            state.copy(
                itemList = state.itemList.map {
                    if (it.itemDetails.itemId == itemDetails.itemId) {
                        it.copy(itemDetails = itemDetails)
                    } else {
                        it
                    }
                }
            )
        }
    }

    fun enableEditMode(itemDetails: ItemDetails) {
        _eventDetailsUiState.update { state ->
            state.copy(
                itemList = state.itemList.map {
                    if (it.itemDetails.itemId == itemDetails.itemId) {
                        it.copy(isEdit = true)
                    } else {
                        it
                    }
                }
            )
        }
    }

    suspend fun addItem() {
        val item = ShoppingItem(eventId = detailsRoute.id, itemName = "Item")
        itemRepository.insert(item)
    }

    suspend fun updateItem(itemDetails: ItemDetails) {
        itemRepository.update(itemDetails.toShoppingItem())
    }

    suspend fun deleteShoppingItem(itemDetails: ItemDetails) {
        itemRepository.delete(itemDetails.toShoppingItem())
    }
}