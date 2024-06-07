package com.nestummilofezan.findmeflight.data

import kotlinx.coroutines.flow.Flow

class OfflineRepository(
    private val airportDao: AirportDao,
    private val favouriteDao: FavouriteDao
): FlightSearchRepository {
    override fun getAirportByInput(input: String): Flow<List<Airport>> = airportDao.getAirportByInput(input)
    override fun getOriginAirportDestination(originId: Int): Flow<List<Airport>> = airportDao.getOriginAirportDestination(originId)

    override suspend fun addFavourite(favourite: Favourite) = favouriteDao.addFavourite(favourite)
    override suspend fun removeFavourite(favourite: Favourite) = favouriteDao.deleteFavourite(favourite)
    override fun getAllFavourites(): Flow<List<Favourite>> = favouriteDao.getAllFavourites()

    override suspend fun getFavourite(inputOrigin: String, inputDestination: String): Favourite = favouriteDao.getFavourite(inputOrigin, inputDestination)
}