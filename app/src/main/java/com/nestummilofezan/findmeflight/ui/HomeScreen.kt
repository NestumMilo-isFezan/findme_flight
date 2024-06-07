package com.nestummilofezan.findmeflight.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nestummilofezan.findmeflight.R
import com.nestummilofezan.findmeflight.ui.viewmodel.FlightViewModel

// Header
@Composable
fun HeaderTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.shadow(elevation = 5.dp, spotColor = Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight().padding(bottom = 10.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun HeaderTitlePreview() {
    HeaderTitle(title = "Find Me Flight")
}

// Flight Result Screen
@Composable
fun HomeScreen(
    viewModel: FlightViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = { HeaderTitle(title = "Find Me Flight", modifier = Modifier.fillMaxHeight(0.12f))}
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            Column (modifier = modifier.padding(10.dp)) {
                SearchBar(modifier = Modifier.fillMaxWidth())

                if (viewModel.flightSearchUi.originAirport == null && viewModel.userInput != "") {
                    SearchResultList(
                        viewModel = viewModel
                    )
                }
                if (viewModel.flightSearchUi.originAirport != null) {
                    Text(
                        text = stringResource(id = R.string.flights_from) + " " + viewModel.flightSearchUi.originAirport!!.iataCode + " " + viewModel.flightSearchUi.originAirport!!.name,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    FlightList(
                        destinationList = viewModel.flightSearchUi.destinationAirportList,
                        departureAirport = viewModel.flightSearchUi.originAirport!!,
                        viewModel = viewModel
                    )
                } else if (viewModel.userInput == "") {
                    viewModel.updateFavourites()
                    Text(
                        if (viewModel.favouriteUi.favourites.isEmpty()){
                            stringResource(id = R.string.no_results)
                        } else {
                            stringResource(id = R.string.favourite_routes)
                        },
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    FavouriteList(
                        favourites = viewModel.favouriteUi.favourites,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

