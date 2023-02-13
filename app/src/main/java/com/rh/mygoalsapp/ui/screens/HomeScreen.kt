package com.rh.mygoalsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    colorScheme: ColorScheme,
    typography: Typography,
    navController: NavHostController
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 80.dp, start = 30.dp, end = 30.dp)
            .background(colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Tudo em dia", style = typography.titleLarge, fontSize = 48.sp)
        Spacer(Modifier.height(204.dp))
        Text(
            text = "Nenhum objetivo a fazer",
            style = typography.headlineMedium,
            color = colorScheme.secondary
        )
    }
}