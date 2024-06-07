package com.nestummilofezan.findmeflight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nestummilofezan.findmeflight.ui.HomeScreen
import com.nestummilofezan.findmeflight.ui.theme.FindMeFlightTheme
import com.nestummilofezan.findmeflight.ui.viewmodel.FlightViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FindMeFlightTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: FlightViewModel = viewModel(factory = FlightViewModel.factory)
                    HomeScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}

