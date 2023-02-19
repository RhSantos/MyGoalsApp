package com.rh.mygoalsapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rh.mygoalsapp.R
import com.rh.mygoalsapp.ui.AddressViewModel
import com.rh.mygoalsapp.ui.UserViewModel
import com.rh.mygoalsapp.domain.models.Address
import com.rh.mygoalsapp.domain.models.User
import com.rh.mygoalsapp.ui.ButtonComponent
import com.rh.mygoalsapp.ui.LabeledEditText
import com.rh.mygoalsapp.ui.navigateToOtherScreen
import com.rh.mygoalsapp.util.Authentication
import com.rh.mygoalsapp.util.ImageFiles
import com.rh.mygoalsapp.util.Routes
import com.rh.mygoalsapp.util.Validation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    colorScheme: ColorScheme,
    typography: Typography,
    navController: NavHostController,
    userViewModel: UserViewModel,
    addressViewModel: AddressViewModel,
    snackbarHostState: SnackbarHostState,
    loggedUser : MutableState<User>
) {

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, start = 30.dp, end = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(text = "Entrar", style = typography.titleLarge)
                Spacer(Modifier.height(3.dp))
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
                Spacer(Modifier.height(35.dp))

                var textEmail = remember { mutableStateOf(TextFieldValue("")) }
                var textSenha = remember { mutableStateOf(TextFieldValue("")) }

                var user = User(
                    "",
                    textEmail.value.text,
                    textSenha.value.text,
                    "",
                    ImageFiles.uriToBitmap(Uri.EMPTY, LocalView.current.context),
                    Address.emptyAddress
                )

                var sendOut = false

                val entrarFunc = {
                    GlobalScope.launch(Dispatchers.Main) {

                        val validate = launch {
                            val validation: Validation = userViewModel.validateCredentials(
                                user,
                                Authentication.Login
                            )

                            val message = when (validation) {
                                is Validation.Sucess -> {
                                    sendOut = true
                                    validation.data?.let {
                                        user = it as User
                                    }

                                    loggedUser.value = validation.data!! as User

                                    validation.message
                                }
                                is Validation.Failed -> {
                                    sendOut = false
                                    validation.message
                                }
                            }

                            snackbarHostState.showSnackbar(
                                message,
                                duration = SnackbarDuration.Short
                            )
                        }

                        validate.join()

                        navigateToOtherScreen(
                            navController = navController,
                            sendToOtherScreen = sendOut,
                            destinationScreen = Routes.HomeScreen,
                            beforeScreen = Routes.RegisterScreen
                        )
                    }
                }

                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = textEmail,
                    textLabel = "Email:",
                    keyboardType = KeyboardType.Email,
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = textSenha,
                    textLabel = "Senha:",
                    keyboardType = KeyboardType.Password,
                    finalInput = true
                ) { entrarFunc() }
                Spacer(Modifier.height(72.dp))
                val focusManager = LocalFocusManager.current
                ButtonComponent(
                    colorScheme = colorScheme,
                    typography = typography,
                    labelText = "Entrar",
                    primaryButton = true
                ) {
                    focusManager.clearFocus()
                    entrarFunc()
                }
            }

            paddingValues
        }
    )
}