package com.rh.mygoalsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rh.mygoalsapp.domain.models.User
import com.rh.mygoalsapp.ui.theme.MyGoalsAppTheme

@Composable
fun ProfileScreen(
    colorScheme: ColorScheme,
    typography: Typography,
    navController: NavHostController,
    user: MutableState<User>
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        Text(text = user.value.endereco.city, style = typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MyGoalsAppTheme {
        val context = LocalContext.current
        ProfileScreen(
            colorScheme = MaterialTheme.colorScheme,
            typography = MaterialTheme.typography,
            navController = rememberNavController(),
            user = remember {
                mutableStateOf(User.emptyUser(context))
            }
        )
    }
}