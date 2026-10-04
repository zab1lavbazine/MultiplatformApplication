package com.example.multiplatformapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.multiplatformapp.service.ServiceChecker
import com.example.multiplatformapp.ui.App
import com.example.multiplatformapp.viewmodel.MonitorViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val checker = ServiceChecker.createServiceChecker()
        val viewModel = MonitorViewModel( checker )




        setContent {
            App(viewModel)
        }
    }
}
