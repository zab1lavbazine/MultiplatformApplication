package com.example.multiplatformapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiplatformapp.viewmodel.MonitorViewModel
import kotlinx.coroutines.launch

@Composable
fun AddServiceScreen(
    viewModel: MonitorViewModel,
) {
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var timeout by remember { mutableStateOf("3000") }
    var interval by remember { mutableStateOf("10") }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Scaffold (
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Add service",
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )

            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("URL") }
            )

            OutlinedTextField(
                value = timeout,
                onValueChange = { timeout = it },
                label = { Text("Timeout (ms)") }
            )

            OutlinedTextField(
                value = interval,
                onValueChange = { interval = it },
                label = { Text("Interval (sec)") }
            )

            Button(
                onClick = {
                    val timeoutMillis = timeout.toLongOrNull()
                    val intervalSeconds = interval.toLongOrNull()

                    if (
                        name.isNotBlank() &&
                        url.isNotBlank() &&
                        timeoutMillis != null &&
                        intervalSeconds != null
                    ) {
                        viewModel.addService(
                            name = name,
                            url = url,
                            timeoutMillis = timeoutMillis,
                            intervalMillis = intervalSeconds * 1000
                        )

                        name = ""
                        url = ""
                        timeout = ""
                        interval = ""

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Service added"
                            )
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Please enter valid values"
                            )
                        }
                    }
                }
            ) {
                Text("Add service")
            }
        }
    }
}