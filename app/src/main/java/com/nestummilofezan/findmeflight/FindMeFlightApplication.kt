package com.nestummilofezan.findmeflight

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nestummilofezan.findmeflight.data.FlightDatabase
import com.nestummilofezan.findmeflight.data.UserPreferencesRepository

private const val INPUT_PREFERENCE_NAME = "input_string"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = INPUT_PREFERENCE_NAME
)

class FindMeFlightApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository
    val database: FlightDatabase by lazy { FlightDatabase.getDatabase(this) }
    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }

}

//class FindMeFlightApplication : Application() {
//    val database: FlightDatabase by lazy{ FlightDatabase.getDatabase(this) }
//}

