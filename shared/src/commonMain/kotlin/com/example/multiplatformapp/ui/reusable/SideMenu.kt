package com.example.multiplatformapp.ui.reusable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatformapp.ui.navigation.Screen

@Composable
fun SideMenu (
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    Column (
        modifier = Modifier
            .width(220.dp)
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Service Monitor",
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider()

        NavigationButton(
            text = "Monitor",
            selected = currentScreen == Screen.MONITOR,
            onClick = {
                onNavigate(Screen.MONITOR)
            }
        )

        NavigationButton(
            text = "Add service",
            selected = currentScreen == Screen.ADD_SERVICE,
            onClick = {
                onNavigate(Screen.ADD_SERVICE)
            }
        )
    }
}