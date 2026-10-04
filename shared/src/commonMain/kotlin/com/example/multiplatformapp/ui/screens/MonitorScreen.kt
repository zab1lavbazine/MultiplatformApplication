package com.example.multiplatformapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatformapp.ui.reusable.ServicesTable
import com.example.multiplatformapp.viewmodel.MonitorViewModel


@Composable
fun MonitorScreen (
    viewModel: MonitorViewModel,
) {
    val services by viewModel.services.collectAsState()


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "Service Monitor",
            style = MaterialTheme.typography.headlineMedium
        )

        HorizontalDivider()

        ServicesTable(
            services = services,
            onCheck = { serviceId ->
                viewModel.checkService(serviceId)
            },
            onDelete = { serviceId ->
                viewModel.deleteServiceById(serviceId)
            }
        )
    }
}
