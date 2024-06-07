package com.nestummilofezan.findmeflight.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite ORDER BY id ASC")
    fun getAllFavourites(): Flow<List<Favourite>>
    @Query("SELECT * FROM favourite WHERE departure_code LIKE '%' || :inputOrigin || '%' AND destination_code LIKE '%' || :inputDestination || '%'")
    suspend fun getFavourite(inputOrigin: String, inputDestination: String): Favourite

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavourite(favourite: Favourite)

}