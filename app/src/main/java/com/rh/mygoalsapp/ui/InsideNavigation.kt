package com.rh.mygoalsapp.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rh.mygoalsapp.domain.models.User
import com.rh.mygoalsapp.ui.screens.*
import com.rh.mygoalsapp.ui.theme.navBarColor
import com.rh.mygoalsapp.ui.theme.navLightBar
import com.rh.mygoalsapp.util.Routes
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsideNavigation(
    colorScheme: ColorScheme,
    typography: Typography,
    snackbarHostState: SnackbarHostState,
    loggedUser : MutableState<User>
) {
    val navHostController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->

            NavHost(navController = navHostController, startDestination = Routes.HomeScreen.route) {
                composable(Routes.HomeScreen.route) {
                    HomeScreen(
                        colorScheme = colorScheme,
                        typography = typography,
                        navController = navHostController
                    )
                }
                composable(Routes.CalendarScreen.route) {
                    CalendarScreen(
                        colorScheme = colorScheme,
                        typography = typography,
                        navController = navHostController
                    )
                }
                composable(Routes.NewGoalScreen.route) {
                    NewGoalScreen(
                        colorScheme = colorScheme,
                        typography = typography,
                        navController = navHostController
                    )
                }
                composable(Routes.GoalsScreen.route) {
                    GoalsScreen(
                        colorScheme = colorScheme,
                        typography = typography,
                        navController = navHostController
                    )
                }
                composable(Routes.ProfileScreen.route) {
                    ProfileScreen(
                        colorScheme = colorScheme,
                        typography = typography,
                        navController = navHostController,
                        user = loggedUser
                    )
                }
            }
            paddingValues
        },
        bottomBar = {
            CustomBottomAppBar(
                colorScheme = colorScheme,
                navController = navHostController
            )
        },
        floatingActionButton = {
            AddGoalFab(
                Modifier.offset(y = 45.dp),
                colorScheme = colorScheme
            ) { navHostController.navigate(route = Routes.NewGoalScreen.route) }
        },
        floatingActionButtonPosition = FabPosition.Center
    )

    val config = LocalConfiguration.current

    val screenOrientation by remember { mutableStateOf(config.orientation) }


    if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
        navBarColor.value = colorScheme.tertiary
        navLightBar.value = false
    } else {
        val resources = LocalContext.current.resources
        val resourceId =
            resources.getIdentifier("config_navBarInteractionMode", "integer", "android")
        val resourceValue by remember { mutableStateOf(resources.getInteger(resourceId)) }

        if (resourceValue > 1) {
            navBarColor.value = colorScheme.tertiary
            navLightBar.value = false
            return
        }
        navBarColor.value = colorScheme.background
        navLightBar.value = true
    }
}