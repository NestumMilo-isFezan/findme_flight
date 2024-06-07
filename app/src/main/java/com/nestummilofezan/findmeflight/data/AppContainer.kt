package com.nestummilofezan.findmeflight.data

import android.content.Context

interface AppContainer {
    val flightSearchRepository: FlightSearchRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val flightSearchRepository: FlightSearchRepository by lazy {
        OfflineRepository(
            FlightDatabase.getDatabase(context).airportDao(),
            FlightDatabase.getDatabase(context).favouriteDao()
        )
    }

}