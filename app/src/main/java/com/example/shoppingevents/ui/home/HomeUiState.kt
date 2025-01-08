package com.example.shoppingevents.ui.home

import com.example.shoppingevents.data.entities.ShoppingEvent

data class HomeUiState(
    val events: List<ShoppingEvent> = emptyList()
)