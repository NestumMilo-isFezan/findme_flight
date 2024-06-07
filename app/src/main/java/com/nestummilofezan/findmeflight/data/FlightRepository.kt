package com.nestummilofezan.findmeflight.data

import kotlinx.coroutines.flow.Flow

interface FlightSearchRepository {
    fun getAirportByInput(input: String): Flow<List<Airport>>
    fun getOriginAirportDestination(originId:Int): Flow<List<Airport>>

    suspend fun addFavourite(favourite: Favourite)
    suspend fun removeFavourite(favourite: Favourite)
    fun getAllFavourites(): Flow<List<Favourite>>
    suspend fun getFavourite(inputOrigin: String, inputDestination: String): Favourite
}