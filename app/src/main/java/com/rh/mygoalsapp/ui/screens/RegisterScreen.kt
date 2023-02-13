package com.rh.mygoalsapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rh.mygoalsapp.ui.AddressViewModel
import com.rh.mygoalsapp.ui.UserViewModel
import com.rh.mygoalsapp.domain.models.Address
import com.rh.mygoalsapp.domain.models.User
import com.rh.mygoalsapp.ui.*
import com.rh.mygoalsapp.util.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    colorScheme: ColorScheme,
    typography: Typography,
    navController: NavHostController,
    userViewModel: UserViewModel,
    addressViewModel: AddressViewModel,
    snackbarHostState: SnackbarHostState,
    loggedUser: MutableState<User>
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            val context = LocalView.current.context
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, start = 30.dp, end = 30.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Registro", style = typography.titleLarge)
                Spacer(Modifier.height(44.dp))
                Text(text = "Informações Gerais", style = typography.headlineMedium)
                Spacer(Modifier.height(36.dp))

                val nomeTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val emailTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val senhaTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val dataDeNascimentoTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val cepTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val logradouroTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val numeroTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val complementoTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val bairroTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }
                val cidadeTextFieldValue = remember { mutableStateOf(TextFieldValue("")) }

                val checkBoxState = remember { mutableStateOf(false) }

                val checkBoxLabel = "Li e aceito as políticas de privacidade e termos de uso."

                val uriState = remember { mutableStateOf(Uri.EMPTY) }

                var sendOut = false


                addressViewModel.adressFieldsCompletetionApiCall(
                    cepTextFieldValue,
                    logradouroTextFieldValue,
                    complementoTextFieldValue,
                    bairroTextFieldValue,
                    cidadeTextFieldValue
                )


                val registrarFunc = {

                    val address = Address(
                        post_code = cepTextFieldValue.value.text,
                        street = logradouroTextFieldValue.value.text,
                        complement = complementoTextFieldValue.value.text,
                        district = bairroTextFieldValue.value.text,
                        city = cidadeTextFieldValue.value.text
                    )

                    val user = User(
                        nomeTextFieldValue.value.text,
                        emailTextFieldValue.value.text,
                        senhaTextFieldValue.value.text,
                        dataDeNascimentoTextFieldValue.value.text,
                        ImageFiles.uriToBitmap(uriState.value, context),
                        address
                    )

                    GlobalScope.launch(Dispatchers.Main) {
                        val validate = launch {

                            var message: String

                            var validationAddress: Validation =
                                addressViewModel.validateAddress(address,numeroTextFieldValue.value.text)

                            message = when (validationAddress) {
                                is Validation.Sucess -> {

                                    address.number = numeroTextFieldValue.value.text.toInt()

                                    var validationUser: Validation =
                                        userViewModel.validateCredentials(
                                            user,
                                            Authentication.Register
                                        )

                                    if (!checkBoxState.value) validationUser =
                                        Validation.Failed("Você deve aceitar os termos e condições!")

                                    when (validationUser) {
                                        is Validation.Sucess -> {
                                            sendOut = true

                                            loggedUser.value = validationUser.data!! as User

                                            validationUser.message
                                        }
                                        is Validation.Failed -> {
                                            sendOut = false
                                            validationUser.message
                                        }
                                    }
                                }
                                is Validation.Failed -> {
                                    sendOut = false
                                    validationAddress.message
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
                    editTextValue = nomeTextFieldValue,
                    textLabel = "Nome:",
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = emailTextFieldValue,
                    textLabel = "Email:",
                    keyboardType = KeyboardType.Email,
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = senhaTextFieldValue,
                    textLabel = "Senha:",
                    keyboardType = KeyboardType.Password,
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = dataDeNascimentoTextFieldValue,
                    textLabel = "Data de Nascimento:",
                    keyboardType = KeyboardType.Number,
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))

                PhotoPickerContainer(
                    colorScheme = colorScheme,
                    typography = typography,
                    textLabel = "Foto:",
                    uriState = uriState,
                )

                Spacer(Modifier.height(84.dp))
                Text(text = "Endereço", style = typography.headlineMedium)
                Spacer(Modifier.height(36.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = cepTextFieldValue,
                    textLabel = "CEP:",
                    keyboardType = KeyboardType.Number,
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = logradouroTextFieldValue,
                    textLabel = "Logradouro:",
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = numeroTextFieldValue,
                    textLabel = "Número:",
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = complementoTextFieldValue,
                    textLabel = "Complemento:",
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = bairroTextFieldValue,
                    textLabel = "Bairro:",
                    finalInput = false
                ) {}
                Spacer(Modifier.height(18.dp))
                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = cidadeTextFieldValue,
                    textLabel = "Cidade:",
                    finalInput = true
                ) { registrarFunc.invoke() }
                Spacer(Modifier.height(55.dp))

                CheckBoxComponent(
                    colorScheme = colorScheme,
                    typography = typography,
                    labelCheckbox = checkBoxLabel,
                    checked = checkBoxState
                )
                Spacer(Modifier.height(34.dp))
                val focusManager = LocalFocusManager.current
                ButtonComponent(
                    colorScheme = colorScheme,
                    typography = typography,
                    labelText = "Criar conta",
                    primaryButton = true,
                ) {
                    focusManager.clearFocus()
                    registrarFunc.invoke()
                }
                Spacer(Modifier.height(50.dp))

                paddingValues
            }
        })
}