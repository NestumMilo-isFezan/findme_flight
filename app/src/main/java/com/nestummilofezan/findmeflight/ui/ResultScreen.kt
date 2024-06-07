package com.nestummilofezan.findmeflight.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nestummilofezan.findmeflight.R
import com.nestummilofezan.findmeflight.data.Airport
import com.nestummilofezan.findmeflight.data.Favourite
import com.nestummilofezan.findmeflight.ui.viewmodel.FlightViewModel


// Flight Card
@Composable
fun FlightCard(
    originIataCode: String,
    originName: String,
    destinationIataCode: String,
    destinationName: String,
    onClick: () -> Unit,
    isPressed: Boolean,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(10.dp, 4.dp).shadow(elevation = 5.dp, shape = RoundedCornerShape(25.dp)),
        shape = RoundedCornerShape(25.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ){
        Row (
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            // Description of Flight
            Column(
                modifier = Modifier
                    .padding(20.dp, 10.dp, 15.dp, 15.dp)
                    .fillMaxWidth(0.8f)
            ) {
                val labelColor: Color = MaterialTheme.colorScheme.primary
                val textColor: Color = MaterialTheme.colorScheme.secondary

                Text(
                    text = stringResource(R.string.depart_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = labelColor,
                    fontWeight = FontWeight.Bold
                )
                Row(modifier = modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
                    Text(
                        text = "%s :".format(originIataCode),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "%s".format(originName),
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor
                    )
                }

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.arrive_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = labelColor,
                    fontWeight = FontWeight.Bold
                )
                Row(modifier = modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)) {
                    Text(
                        text = "%s :".format(destinationIataCode),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "%s".format(destinationName),
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor
                    )
                }
            }

            // Favourite Buttons
            IconButton(
                modifier = modifier.padding(10.dp),
                onClick = onClick
            ) {
                Icon(
                    imageVector = if (isPressed) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = if (isPressed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
            }

        }
    }
}

@Composable
fun FlightList(
    destinationList: List<Airport>,
    departureAirport: Airport,
    viewModel: FlightViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        items(destinationList) { destinationAirport ->
            FlightCard(
                originIataCode = departureAirport.iataCode,
                originName = departureAirport.name,
                destinationIataCode = destinationAirport.iataCode,
                destinationName = destinationAirport.name,
                onClick = {
                    viewModel.addOrRemoveFavourite(
                        Favourite(
                            departureCode = departureAirport.iataCode + " : " + departureAirport.name,
                            destinationCode = destinationAirport.iataCode + " : " + destinationAirport.name
                        )
                    )
                },
                isPressed = viewModel.isFavourite(
                    departureCode = departureAirport.iataCode + " : " + departureAirport.name,
                    destinationCode = destinationAirport.iataCode + " : " + destinationAirport.name
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun FavouriteList(
    favourites: List<Favourite>,
    viewModel: FlightViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(favourites) { favourite ->

            FlightCard(
                originIataCode = favourite.departureCode.substring(0,3),
                originName = favourite.departureCode.substringAfter(" : "),
                destinationIataCode = favourite.destinationCode.substring(0,3),
                destinationName = favourite.destinationCode.substringAfter(" : "),
                onClick = {

                    viewModel.addOrRemoveFavourite(
                        favourite = favourite
                    )
                },
                isPressed = viewModel.isFavourite(
                    departureCode = favourite.departureCode,
                    destinationCode = favourite.destinationCode
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            )
        }
    }
}