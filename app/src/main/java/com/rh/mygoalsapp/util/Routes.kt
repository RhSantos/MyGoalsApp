package com.rh.mygoalsapp.util

sealed class Routes(val route:String) {
    object StartScreen:Routes("StartScreen")
    object RegisterScreen:Routes("RegisterScreen")
    object LoginScreen:Routes("LoginScreen")
    object HomeScreen:Routes("HomeScreen")
    object CalendarScreen:Routes("CalendarScreen")
    object GoalsScreen:Routes("GoalsScreen")
    object NewGoalScreen:Routes("NewGoalScreen")
    object ProfileScreen:Routes("ProfileScreen")
}
