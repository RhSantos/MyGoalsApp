package com.rh.mygoalsapp.ui

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rh.mygoalsapp.domain.models.User
import com.rh.mygoalsapp.ui.screens.LoginScreen
import com.rh.mygoalsapp.ui.screens.RegisterScreen
import com.rh.mygoalsapp.ui.screens.StartScreen
import com.rh.mygoalsapp.ui.theme.navBarColor
import com.rh.mygoalsapp.ui.theme.navLightBar
import com.rh.mygoalsapp.util.Routes


fun navigateToOtherScreen(
    navController: NavController,
    sendToOtherScreen: Boolean? = null,
    destinationScreen: Routes,
    beforeScreen: Routes? = null,
) {
    sendToOtherScreen?.let {
        if (!sendToOtherScreen) return
    }

    if (beforeScreen != null) beforeScreen.let {
        navController.navigate(destinationScreen.route) {
            popUpTo(it.route) {
                inclusive = true
            }
            popUpTo(Routes.StartScreen.route) {
                inclusive = true
            }
        }
    } else navController.navigate(destinationScreen.route)
}

@Composable
fun OutsideNavigation(
    colorScheme: ColorScheme,
    typography: Typography,
    userViewModel: UserViewModel,
    addressViewModel: AddressViewModel,
    loggedUser : MutableState<User>
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(navController = navController, startDestination = Routes.StartScreen.route) {
        composable(Routes.StartScreen.route) {
            StartScreen(
                colorScheme = colorScheme,
                typography = typography,
                navController = navController
            )
        }
        composable(Routes.LoginScreen.route) {
            LoginScreen(
                colorScheme = colorScheme,
                typography = typography,
                navController = navController,
                userViewModel = userViewModel,
                snackbarHostState = snackbarHostState,
                loggedUser = loggedUser,
                addressViewModel = addressViewModel
            )
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(
                colorScheme = colorScheme,
                typography = typography,
                navController = navController,
                userViewModel = userViewModel,
                snackbarHostState = snackbarHostState,
                loggedUser = loggedUser,
                addressViewModel = addressViewModel
            )
        }
        composable(Routes.HomeScreen.route) {

            navBarColor.value = colorScheme.tertiary
            navLightBar.value = false

            InsideNavigation(
                colorScheme = colorScheme,
                typography = typography,
                snackbarHostState = snackbarHostState,
                loggedUser = loggedUser
            )
        }
    }
}