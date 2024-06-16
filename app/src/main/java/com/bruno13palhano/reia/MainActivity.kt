package com.bruno13palhano.reia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bruno13palhano.reia.ui.navigation.MainNavGraph
import com.bruno13palhano.reia.ui.screens.components.BottomMenu
import com.bruno13palhano.reia.ui.screens.components.DrawerMenu
import com.bruno13palhano.reia.ui.theme.REIATheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            REIATheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                var showBottomMenu by rememberSaveable { mutableStateOf(false) }
                var gesturesEnabled by rememberSaveable { mutableStateOf(false) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrawerMenu(
                        drawerState = drawerState,
                        navController = navController,
                        gesturesEnabled = gesturesEnabled
                    ) {
                        val coroutineScope = rememberCoroutineScope()

                        Scaffold(
                            bottomBar = { BottomMenu(navController = navController) }
                        ) { innerPadding ->
                            MainNavGraph(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                showBottomMenu = { showBottomMenu = it },
                                gesturesEnabled = { gesturesEnabled = it }
                            ) {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}