package com.example.multiplatformapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.example.multiplatformapp.ui.navigation.Router
import com.example.multiplatformapp.ui.navigation.Screen
import com.example.multiplatformapp.ui.screens.AddServiceScreen
import com.example.multiplatformapp.ui.screens.MonitorScreen
import com.example.multiplatformapp.viewmodel.MonitorViewModel
import kotlinx.coroutines.launch
import multiplatformapp.shared.generated.resources.Res
import org.jetbrains.compose.resources.painterResource


@Composable
fun App(
    viewModel: MonitorViewModel
) {

    val router = remember {
        Router()
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope ()

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet {

                NavigationDrawerItem(
                    label = {
                        Text("Monitor")
                    },
                    selected = router.currentScreen == Screen.MONITOR,
                    onClick = {
                        router.navigateTo(Screen.MONITOR)

                        scope.launch {
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    label = {
                        Text("Add Service")
                    },
                    selected = router.currentScreen == Screen.ADD_SERVICE,
                    onClick = {
                        router.navigateTo(Screen.ADD_SERVICE)

                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
//                NavigationDrawerItem(
//                    label = {
//                        Text("Settings")
//                    },
//                    selected = router.currentScreen == Screen.SETTINGS,
//                    onClick = {
//                        router.navigate(Screen.SETTINGS)
//
//                        scope.launch {
//                            drawerState.close()
//                        }
//                    }
//                )
            }
        }
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
        ) {
            // header with button
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Lucide.Menu,
                        contentDescription = "Menu"
                    )
                }

                Text (
                    "Service Monitor",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            when (router.currentScreen) {
                Screen.MONITOR -> {
                    MonitorScreen(
                        viewModel = viewModel
                    )
                }
                Screen.ADD_SERVICE -> {
                    AddServiceScreen(
                        viewModel = viewModel
                    )
                }
                Screen.SETTINGS -> TODO()
            }
        }
    }
}