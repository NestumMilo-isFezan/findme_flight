package com.nestummilofezan.findmeflight.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nestummilofezan.findmeflight.FindMeFlightApplication
import com.nestummilofezan.findmeflight.data.Airport
import com.nestummilofezan.findmeflight.data.AirportDao
import com.nestummilofezan.findmeflight.data.Favourite
import com.nestummilofezan.findmeflight.data.FavouriteDao
import com.nestummilofezan.findmeflight.data.FlightSearchRepository
import com.nestummilofezan.findmeflight.data.OfflineRepository
import com.nestummilofezan.findmeflight.data.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FlightViewModel(
    private val repository: FlightSearchRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    /////////////////////////////////////////////////////////////////////////
    // Task : Kasi tunjuk results2 flight yang kita taip kat airport
    // Ini hanyalah ViewModel, tempat simpan Ui State... Malas nak eksplen
    /////////////////////////////////////////////////////////////////////////

    // #1 Kita ada uiState
    // User punya search string
    var flightSearchUi by mutableStateOf(FlightSearchUi())
        private set

    var favouriteUi by mutableStateOf(FavouriteUi())
        private set

    var userInput by mutableStateOf("")
        private set

    fun updateSearchInput(input: String) {
        userInput = input
    }

    fun updateCurrentAirport(airport: Airport?) {
        flightSearchUi = flightSearchUi.copy(
            originAirport = airport
        )
    }

    init {
        runBlocking { userInput=userPreferencesRepository.inputSearch.first()}
        getSearchResultsList(userInput)
    }

    fun getSearchResultsList(input: String) {
        viewModelScope.launch {
            flightSearchUi = flightSearchUi.copy(
                suggestedAirportList = repository.getAirportByInput("%$input%")
                    .filterNotNull()
                    .first()
            )
        }

    }

    fun clearSearchResultsList() {
        updateInputPreferences("")
        flightSearchUi = flightSearchUi.copy(
            suggestedAirportList = emptyList(),
        )
    }

    fun getAllDestinationAirports() {
        viewModelScope.launch {
            if (flightSearchUi.originAirport != null) {
                flightSearchUi = flightSearchUi.copy(
                    destinationAirportList = repository.getOriginAirportDestination(originId = flightSearchUi.originAirport!!.id)
                        .filterNotNull().first()
                )
            }
        }

    }

    fun addOrRemoveFavourite(favourite: Favourite) {
        viewModelScope.launch {
            if (repository.getFavourite(favourite.departureCode, favourite.destinationCode) == null
            ) {
                addFavourite(favourite)
            } else {
                removeFavourite(
                    repository.getFavourite(
                        favourite.departureCode, favourite.destinationCode
                    )!!
                )
            }
            updateFavourites()
        }
    }

    private fun addFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.addFavourite(favourite)
        }
    }


    private fun removeFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.removeFavourite(favourite)
        }
    }

    fun updateFavourites() {
        viewModelScope.launch {
            favouriteUi = favouriteUi.copy(
                favourites = repository.getAllFavourites().filterNotNull().first()
            )
        }

    }

    fun isFavourite(departureCode: String, destinationCode: String): Boolean {
        if (favouriteUi.favourites.isNotEmpty()) {
            favouriteUi.favourites.forEach { favourite ->
                if (favourite.departureCode == departureCode && favourite.destinationCode == destinationCode) return true
            }
        }
        return false
    }

    fun updateInputPreferences(input: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveInput(input = input)
        }
    }


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FindMeFlightApplication)
                val repository = OfflineRepository(
                    application.database.airportDao(), application.database.favouriteDao()
                )
                FlightViewModel(repository, application.userPreferencesRepository)
            }
        }
    }
}

data class SearchState(
    var searchString: String = "",
    var airportList: Flow<List<Airport>> = emptyFlow()
)

data class FlightSearchUi(
    val originAirport: Airport? = null,
    val suggestedAirportList: List<Airport> = emptyList(),
    val destinationAirportList: List<Airport> = emptyList(),
)

data class FavouriteUi(
    val favourites: List<Favourite> = listOf(),
)