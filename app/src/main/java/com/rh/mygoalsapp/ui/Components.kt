package com.rh.mygoalsapp.ui

import android.content.res.Resources
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.rh.mygoalsapp.R
import com.rh.mygoalsapp.ui.theme.MyGoalsAppTheme
import com.rh.mygoalsapp.util.BottomBarScreen
import com.rh.mygoalsapp.util.Constants

@Composable
fun ButtonComponent(
    colorScheme: ColorScheme,
    typography: Typography,
    labelText: String,
    primaryButton: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick.invoke() },
        contentPadding = PaddingValues(20.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(if (primaryButton) colorScheme.primary else colorScheme.secondary)
    ) {
        Text(text = labelText, style = typography.labelLarge)
    }
}

@Preview
@Composable
fun LabeledEditTextPreview() {
    MyGoalsAppTheme {
        val value = remember { mutableStateOf(TextFieldValue("")) }
        LabeledEditText(
            colorScheme = MaterialTheme.colorScheme,
            typography = MaterialTheme.typography,
            editTextValue = value,
            textLabel = "Teste",
            KeyboardType.Text,
            false
        ) { }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledEditText(
    colorScheme: ColorScheme,
    typography: Typography,
    editTextValue: MutableState<TextFieldValue>,
    textLabel: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    finalInput: Boolean,
    onClick: () -> Any
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 11.dp),
            text = textLabel,
            style = typography.labelMedium.copy(fontSize = 20.sp, color = colorScheme.tertiary)
        )
        Spacer(Modifier.height(6.dp))
        val interactionSource = remember { MutableInteractionSource() }
        val focusManager = LocalFocusManager.current
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorScheme.secondary,
                    RoundedCornerShape(10.dp)
                ),
            value = editTextValue.value,
            onValueChange = { newText ->
                if (newText.text.length > Constants.editTextMaxChars(textLabel)) return@BasicTextField
                if ((textLabel == "CEP:" || textLabel == "Numero:") && (newText.text.contains(".") || newText.text.contains(","))) return@BasicTextField
                if (textLabel == "CEP:" && newText.text.count { it == '-' } > 1) return@BasicTextField
                if (!newText.text.contains("-") && !editTextValue.value.text.contains("-") && textLabel == "CEP:") {
                    if (newText.text.length == 5) {
                        editTextValue.value = TextFieldValue(newText.text.plus("-"), TextRange(newText.text.length + 1))
                    } else {
                        editTextValue.value = TextFieldValue(newText.text,TextRange(newText.text.length))
                    }
                } else {
                    if (textLabel == "CEP:" && editTextValue.value.text.contains("-") && !newText.text.contains("-")) {
                        if(newText.text.length < editTextValue.value.text.length) editTextValue.value = editTextValue.value.copy(selection = TextRange(newText.text.length))
                        else editTextValue.value = editTextValue.value.copy(selection = TextRange(newText.text.length+1))
                    } else editTextValue.value = newText
                }
            },
            textStyle = typography.displaySmall,
            maxLines = 1,
            visualTransformation = if (keyboardType != KeyboardType.Password) VisualTransformation.None else PasswordVisualTransformation(),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                if (finalInput) {
                    focusManager.clearFocus()
                    onClick.invoke()
                } else focusManager.moveFocus(
                    FocusDirection.Down
                )
            }),
            singleLine = true,
            decorationBox = { innerTextField ->
                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = editTextValue.value.text,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = if (keyboardType != KeyboardType.Password) VisualTransformation.None else PasswordVisualTransformation(),
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(20.dp),
                    colors = outlinedTextFieldColors(textColor = colorScheme.primary),
                    border = {
                        TextFieldDefaults.BorderBox(
                            enabled = true,
                            isError = false,
                            unfocusedBorderThickness = 3.dp,
                            shape = RoundedCornerShape(10.dp),
                            focusedBorderThickness = 3.dp,
                            interactionSource = interactionSource,
                            colors = outlinedTextFieldColors(
                                unfocusedBorderColor = colorScheme.tertiary,
                                focusedBorderColor = colorScheme.tertiary
                            )
                        )
                    }
                )
            }
        )
    }
}

@Composable
fun PhotoPickerContainer(
    colorScheme: ColorScheme,
    typography: Typography,
    textLabel: String,
    uriState: MutableState<Uri>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = textLabel,
            style = typography.labelMedium.copy(fontSize = 20.sp, color = colorScheme.tertiary)
        )
        Spacer(Modifier.height(6.dp))
        val interactionSource = remember { MutableInteractionSource() }
        val galleryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let { uriState.value = it }
            }
        Box(
            modifier = Modifier
                .size(150.dp)
                .border(4.dp, colorScheme.tertiary, RoundedCornerShape(25.dp))
                .background(colorScheme.secondary, RoundedCornerShape(25.dp))
                .clickable(interactionSource, null) {
                    galleryLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            val painter =
                if (uriState.value == Uri.EMPTY)
                    painterResource(id = R.drawable.user_plus)
                else rememberAsyncImagePainter(model = uriState!!.value)

            Image(
                modifier = Modifier
                    .clip(
                        if (uriState.value == Uri.EMPTY) RectangleShape else RoundedCornerShape(
                            25.dp
                        )
                    )
                    .fillMaxSize(if (uriState.value == Uri.EMPTY) 0.65f else 1f),
                painter = painter,
                contentDescription = null
            )
        }
    }
}

@Composable
fun CheckBoxComponent(
    colorScheme: ColorScheme,
    typography: Typography,
    labelCheckbox: String,
    checked: MutableState<Boolean>
) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        Image(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                )
                { checked.value = !checked.value },
            painter = painterResource(id = if (checked.value) R.drawable.check_square else R.drawable.check_square_empty),
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Text(
            labelCheckbox,
            style = typography.labelSmall
        )
    }
}

@Composable
fun CustomBottomAppBar(
    colorScheme: ColorScheme,
    navController: NavController,
) {
    MyGoalsAppTheme {
        val screens = listOf(
            BottomBarScreen.Home,
            BottomBarScreen.Calendar,
            BottomBarScreen.List,
            BottomBarScreen.User,
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentDestination = navBackStackEntry?.destination

        val (width, height) = Resources.getSystem().displayMetrics.run { (widthPixels to heightPixels) }

        val path = Path().apply {
            addOval(Rect(Offset(width.toFloat() / 2, 0f), 115f))
        }


        Box(
            Modifier
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            BottomAppBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(60.dp)
                    .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
                    .drawBehind {
                        clipPath(path, clipOp = ClipOp.Difference) {
                            drawPath(path, colorScheme.tertiary, 0f)
                            drawRect(colorScheme.tertiary)
                        }

                    },
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                tonalElevation = 0.dp

            ) {
                var count = 1
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        navController = navController,
                        currentDestination = currentDestination,
                        colorScheme = colorScheme,
                        count = count
                    )
                    count++
                }
            }
        }

    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    navController: NavController,
    currentDestination: NavDestination?,
    colorScheme: ColorScheme,
    count: Int
) {

    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val width = Resources.getSystem().displayMetrics.run { widthPixels / density }
    val interactionSource = remember { MutableInteractionSource() }

    val spaceDeafultBetweenButtons = (((width / 2) - 50 - (115 / 2)) / 3).dp

    when (count) {
        1 -> Spacer(modifier = Modifier.width(spaceDeafultBetweenButtons))
        2 -> Spacer(modifier = Modifier.width(spaceDeafultBetweenButtons))
        3 -> Spacer(modifier = Modifier.width((width / 3).dp - 5.dp))
        4 -> Spacer(modifier = Modifier.width(spaceDeafultBetweenButtons))
    }

    Icon(
        imageVector = ImageVector.vectorResource(id = screen.icon),
        contentDescription = null,
        modifier = Modifier
            .size(35.dp)
            .clickable(interactionSource, null) { navController.navigate(screen.route) },
        tint = if (selected) colorScheme.primary else colorScheme.onPrimary
    )
}

@Composable
fun AddGoalFab(
    modifier: Modifier = Modifier,
    colorScheme: ColorScheme,
    onClick: () -> Unit
) {
    Box(
        modifier
            .size(60.dp)
            .background(colorScheme.primary, CircleShape)
            .clickable(remember { MutableInteractionSource() }, null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(0.8f),
            painter = painterResource(id = R.drawable.plus),
            contentDescription = null
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddGoalFabPreview() {
    MyGoalsAppTheme {
        AddGoalFab(Modifier, MaterialTheme.colorScheme) {}
        /*CustomBottomAppBar(
            colorScheme = MaterialTheme.colorScheme,
            navController = rememberNavController()
        )*/
    }
}