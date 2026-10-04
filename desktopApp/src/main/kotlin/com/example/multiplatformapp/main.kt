package com.example.multiplatformapp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.multiplatformapp.repository.ServiceRepository
import com.example.multiplatformapp.service.ServiceChecker
import com.example.multiplatformapp.ui.App
import com.example.multiplatformapp.viewmodel.MonitorViewModel


fun main() = application {



    val checker = remember {
        ServiceChecker.createServiceChecker()
    }

    val viewModel = remember {
        MonitorViewModel(
            checker,
        )
    }

    val database = Database

    Window(
        onCloseRequest = ::exitApplication,
        title = "MultiplatformApp",
    ) {
        App(viewModel)
    }
}