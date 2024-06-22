package com.bruno13palhano.reia.ui.screens.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bruno13palhano.reia.R
import com.bruno13palhano.reia.ui.navigation.MainRoutes
import kotlinx.coroutines.launch

@Composable
fun BottomMenu(navController: NavController) {
    val items =
        listOf(
            Screen.Home
        )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { screen ->
            val selected = currentDestination?.selectedRoute(screen = screen)

            NavigationBarItem(
                label = { Text(text = stringResource(id = screen.resourceId)) },
                selected = selected == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = screen.icon, contentDescription = null) }
            )
        }
    }
}

@Composable
fun ComponentsMenu(onItemClick: (subtype: String, type: ComponentType) -> Unit) {
    val items =
        listOf(
            ComponentOptions.Box,
            ComponentOptions.Bind,
            ComponentOptions.Electric
        )

    var selected by remember { mutableStateOf(items[0]) }

    NavigationBar {
        items.forEach { component ->
            var expanded by remember { mutableStateOf(false) }

            NavigationBarItem(
                label = { Text(text = stringResource(id = component.resourceId)) },
                selected = selected == component && expanded,
                onClick = {
                    selected = component
                    expanded = true
                },
                icon = {
                    Icon(imageVector = component.icon, contentDescription = null)

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        component.subComponents.forEach { subComponentsOptions ->
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = subComponentsOptions.resourceId)) },
                                onClick = {
                                    expanded = false
                                    onItemClick(subComponentsOptions.name, subComponentsOptions.type)
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    navController: NavController,
    gesturesEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val items =
        listOf(
            Screen.Home
        )

    val orientation = LocalConfiguration.current.orientation
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            ModalDrawerSheet(
                modifier =
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        Modifier.fillMaxWidth(.78F)
                    } else {
                        Modifier
                    },
                drawerShape = RectangleShape
            ) {
                LazyColumn {
                    stickyHeader {
                        Text(
                            modifier =
                                Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth(),
                            text = stringResource(id = R.string.app_name),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    items(items = items) { screen ->
                        val selected = currentDestination?.selectedRoute(screen = screen)

                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0, 50, 50, 0),
                            icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                            label = { Text(text = stringResource(id = screen.resourceId)) },
                            selected = selected == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            modifier =
                                Modifier
                                    .padding(top = 4.dp, bottom = 4.dp, end = 8.dp)
                                    .height(52.dp)
                        )
                    }
                }
            }
        },
        content = content
    )
}

fun NavDestination.selectedRoute(screen: Screen<MainRoutes>): Boolean {
    return this.hierarchy.any {
        it.route?.split(".")?.lastOrNull() == screen.route.toString()
    }
}

sealed class Screen<T : MainRoutes>(
    val route: T,
    val icon: ImageVector,
    @StringRes val resourceId: Int
) {
    data object Home : Screen<MainRoutes>(
        route = MainRoutes.Home,
        icon = Icons.Filled.Home,
        resourceId = R.string.app_name
    )
}