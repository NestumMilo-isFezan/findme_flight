package com.nestummilofezan.findmeflight.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nestummilofezan.findmeflight.R
import com.nestummilofezan.findmeflight.data.Airport
import com.nestummilofezan.findmeflight.ui.viewmodel.FlightViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Flight Search Bar
@Composable
fun SearchBar(
    viewModel: FlightViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .padding(2.dp, 1.dp, 2.dp, 20.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(40.dp)),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ){
        Row (
            modifier = modifier
                .padding(5.dp)
        ) {
            OutlinedTextField(
                value = viewModel.userInput,
                onValueChange = {
                    /*TODO*/
                    newValue -> viewModel.updateSearchInput(newValue)
                    viewModel.updateCurrentAirport(null)
                    if (newValue == "") {
                        viewModel.clearSearchResultsList()
                    } else {
                        viewModel.getSearchResultsList(newValue)
                        viewModel.updateInputPreferences(newValue)
                    }
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_placeholder),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(35.dp),
                modifier = modifier
                    .fillMaxWidth()
            )
        }
    }
}

// Airport Card
@Composable
fun AirportCard(airport: Airport, modifier: Modifier = Modifier){
    Card(
        modifier = modifier
            .padding(10.dp, 5.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = modifier.padding(17.dp, 15.dp, 5.dp, 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val airportNameColor: Color = MaterialTheme.colorScheme.secondary
            val airportCodeColor: Color = MaterialTheme.colorScheme.primary
            Text(
                text = airport.iataCode,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                color = airportCodeColor
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = airport.name,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                color = airportNameColor
            )
        }
    }
}

@Composable
fun SearchResultList(
    viewModel: FlightViewModel, modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(viewModel.flightSearchUi.suggestedAirportList) { airport ->
            AirportCard(airport = airport, modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.updateCurrentAirport(airport = airport)
                    viewModel.getAllDestinationAirports()
                })
        }
    }
}


@Preview
@Composable
fun FlightSearchBarPreview(){
    SearchBar()
}

@Preview
@Composable
fun AirportCardPreview(){
    AirportCard(
        airport = Airport(name = "Kuala Lumpur", iataCode = "KUL", passengers = 50)
    )
}
