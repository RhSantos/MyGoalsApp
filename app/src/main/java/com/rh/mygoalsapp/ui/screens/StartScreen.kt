package com.rh.mygoalsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rh.mygoalsapp.R
import com.rh.mygoalsapp.ui.ButtonComponent
import com.rh.mygoalsapp.ui.navigateToOtherScreen
import com.rh.mygoalsapp.util.Routes

@Composable
fun StartScreen(
    colorScheme: ColorScheme,
    typography: Typography,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, start = 30.dp, end = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(0.9f),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Spacer(Modifier.height(24.dp))
        Text(text = "Seja bem-vindo(a)", style = typography.titleMedium)
        Spacer(Modifier.height(120.dp))

        ButtonComponent(
            colorScheme = colorScheme,
            typography = typography,
            labelText = "Entrar na sua conta",
            primaryButton = true
        ) {
            navigateToOtherScreen(
                navController = navController,
                destinationScreen = Routes.LoginScreen
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        ButtonComponent(
            colorScheme = colorScheme,
            typography = typography,
            labelText = "Registrar sua conta",
            primaryButton = false
        ) {
            navigateToOtherScreen(
                navController = navController,
                destinationScreen = Routes.RegisterScreen
            )
        }
    }
}