package com.example.multiplatformapp.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Router(
    initialScreen: Screen = Screen.MONITOR
) {

    var currentScreen by mutableStateOf(initialScreen)
        private set


    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    fun goToMonitor(){
        currentScreen = Screen.MONITOR
    }

    fun goToAddService() {
        currentScreen = Screen.ADD_SERVICE
    }

    fun goToSettings() {
        currentScreen = Screen.SETTINGS
    }
}