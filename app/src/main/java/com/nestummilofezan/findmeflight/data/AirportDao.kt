package com.nestummilofezan.findmeflight.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    // CRUD It has are : Read IATA only

    @Query("SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code LIKE '%' || :input || '%' OR name LIKE '%' || :input || '%' ORDER BY passengers DESC")
    fun getAirportByInput(input: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE id IS NOT :originId ORDER BY passengers DESC")
    fun getOriginAirportDestination(originId: Int): Flow<List<Airport>>


}