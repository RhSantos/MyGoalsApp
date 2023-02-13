package com.rh.mygoalsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CalendarScreen(
    colorScheme: ColorScheme,
    typography: Typography,
    navController: NavHostController
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {}
}