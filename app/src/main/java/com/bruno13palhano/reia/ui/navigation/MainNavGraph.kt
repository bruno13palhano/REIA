package com.bruno13palhano.reia.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showBottomMenu: (enabled: Boolean) -> Unit = {},
    gesturesEnabled: (enabled: Boolean) -> Unit = {},
    onIconMenuClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = MainRoutes.Main,
        modifier = modifier
    ) {
        navigation<MainRoutes.Main>(startDestination = MainRoutes.Home) {
            composable<MainRoutes.Home> {
                showBottomMenu(true)
                gesturesEnabled(true)
                Text(text = "Home Screen")
            }
        }
    }
}

sealed interface MainRoutes {
    @Serializable
    data object Main : MainRoutes

    @Serializable
    data object Home : MainRoutes
}