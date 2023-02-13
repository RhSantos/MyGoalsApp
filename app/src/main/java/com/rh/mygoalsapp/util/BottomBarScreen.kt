package com.rh.mygoalsapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.rh.mygoalsapp.R

sealed class BottomBarScreen(
    val route: String,
    val icon: Int
) {
    object Home : BottomBarScreen(
        Routes.HomeScreen.route,
        R.drawable.home
    )

    object Calendar : BottomBarScreen(
        Routes.CalendarScreen.route,
        R.drawable.calendar
    )

    object List : BottomBarScreen(
        Routes.GoalsScreen.route,
        R.drawable.clipboard
    )

    object User : BottomBarScreen(
        Routes.ProfileScreen.route,
        R.drawable.user
    )
}
