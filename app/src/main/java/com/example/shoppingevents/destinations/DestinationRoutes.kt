package com.example.shoppingevents.destinations

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object AddEventRoute

@Serializable
data class EventDetailsRoute(val id: Long, val name: String)