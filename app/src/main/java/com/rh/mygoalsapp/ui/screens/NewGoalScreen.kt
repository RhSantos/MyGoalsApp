package com.rh.mygoalsapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rh.mygoalsapp.R
import com.rh.mygoalsapp.ui.ButtonComponent
import com.rh.mygoalsapp.ui.LabeledEditText
import com.rh.mygoalsapp.ui.theme.MyGoalsAppTheme
import com.rh.mygoalsapp.util.Constants.monthNameByMonthInt
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGoalScreen(
    colorScheme: ColorScheme,
    typography: Typography,
    navController: NavHostController,
    /*userViewModel: UserViewModel,
    addressViewModel: AddressViewModel,
    snackbarHostState: SnackbarHostState,
    loggedUser: MutableState<User>*/
) {
    val objectiveNumber by remember { mutableStateOf(1) }
    val nomeObjetivoState = remember { mutableStateOf(TextFieldValue("")) }
    Scaffold(
        //snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(30.dp),
                horizontalAlignment = Start
            ) {

                Text(
                    "Objetivo #${objectiveNumber}",
                    style = typography.titleLarge,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(35.dp))

                LabeledEditText(
                    colorScheme = colorScheme,
                    typography = typography,
                    editTextValue = nomeObjetivoState,
                    textLabel = "Nome do Objetivo:",
                    finalInput = true
                ) {

                }
                Spacer(Modifier.height(30.dp))

                Text(
                    modifier = Modifier.padding(start = 11.dp),
                    text = "Dia do Objetivo:",
                    style = typography.labelMedium.copy(
                        fontSize = 20.sp,
                        color = colorScheme.tertiary
                    )
                )
                Spacer(Modifier.height(6.dp))
                CalendarComponent(colorScheme = colorScheme, typography = typography,withYearText = true)
                Spacer(Modifier.height(20.dp))
                ButtonComponent(
                    colorScheme = colorScheme,
                    typography = typography,
                    labelText = "Adicionar",
                    primaryButton = true
                ) {

                }
            }

            paddingValues
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NewGoalScreenPreview() {
    MyGoalsAppTheme {
        val context = LocalContext.current
        NewGoalScreen(
            colorScheme = MaterialTheme.colorScheme,
            typography = MaterialTheme.typography,
            navController = rememberNavController(),
            /*userViewModel = viewModel(),
            addressViewModel = viewModel(),
            snackbarHostState = remember { SnackbarHostState() },
            loggedUser = remember { mutableStateOf(User.emptyUser(context)) }*/
        )
    }
}

//@Preview
//@Composable
//fun CalendarComponentPreview() {
//    MyGoalsAppTheme {
//        CalendarComponent(
//            colorScheme = MaterialTheme.colorScheme,
//            typography = MaterialTheme.typography
//        )
//    }
//}

@Composable
fun CalendarComponent(
    colorScheme: ColorScheme,
    typography: Typography,
    withYearText: Boolean = false
) {
    val actualDateState = remember { mutableStateOf(LocalDate.now()) }
    val selectedOption = remember { mutableStateOf<LocalDate?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.tertiary,
                shape = RoundedCornerShape(35.dp)
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { selectedOption.value = null }
    ) {
        if (withYearText) {
            Spacer(Modifier.height(24.dp))
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = actualDateState.value.year.toString(),
                style = typography.titleMedium,
                fontSize = 48.sp
            )
        }

        Box(
            Modifier
                .padding(horizontal = 25.dp, vertical = 25.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                modifier = Modifier
                    .width(25.dp)
                    .height(30.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { actualDateState.value = actualDateState.value.minusMonths(1) }
                    .align(Alignment.TopStart),
                painter = painterResource(id = R.drawable.before),

                contentDescription = null
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = monthNameByMonthInt(actualDateState.value.monthValue),
                style = typography.labelLarge,
                fontSize = 24.sp
            )
            Image(
                modifier = Modifier
                    .width(25.dp)
                    .height(30.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { actualDateState.value = actualDateState.value.plusMonths(1) }
                    .align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.next),

                contentDescription = null
            )
        }
        Spacer(Modifier.height(15.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "D", style = typography.labelLarge, fontSize = 16.sp)
            Text(text = "S", style = typography.labelLarge, fontSize = 16.sp)
            Text(text = "T", style = typography.labelLarge, fontSize = 16.sp)
            Text(text = "Q", style = typography.labelLarge, fontSize = 16.sp)
            Text(text = "Q", style = typography.labelLarge, fontSize = 16.sp)
            Text(text = "S", style = typography.labelLarge, fontSize = 16.sp)
            Text(text = "S", style = typography.labelLarge, fontSize = 16.sp)
        }
        Spacer(Modifier.height(8.dp))
        Canvas(
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .fillMaxWidth()
                .height(4.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawRoundRect(
                color = colorScheme.onPrimary,
                size = Size(canvasWidth, canvasHeight),
                cornerRadius = CornerRadius(10f, 10f)
            )
        }
        Spacer(Modifier.height(15.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            val firstWeekOfMonth =
                actualDateState.value.withDayOfMonth(1).get(ChronoField.ALIGNED_WEEK_OF_YEAR)
            val finalWeekOfMonth =
                actualDateState.value.withDayOfMonth(actualDateState.value.lengthOfMonth())
                    .get(ChronoField.ALIGNED_WEEK_OF_YEAR)

            var tempDate = actualDateState.value.withDayOfMonth(1)
            var firstWeekDaysCount = 0
            var finalWeekDaysCount = 0
            val firstWeekDaysOfThisMonth = mutableListOf<LocalDate>()

            do {
                firstWeekDaysOfThisMonth.add(tempDate)
                tempDate = tempDate.plusDays(1)
                firstWeekDaysCount += 1
            } while (tempDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR) == firstWeekOfMonth)

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp), Arrangement.SpaceBetween
            ) {
                for (i in (7 - firstWeekDaysCount) downTo 1) {
                    val day = actualDateState.value.withDayOfMonth(1).minus(Period.ofDays(i))

                    DayOfCalendar(
                        date = day,
                        actualDateState = actualDateState,
                        outOfMonth = true,
                        selectedOption = selectedOption,
                        colorScheme = colorScheme,
                        typography = typography
                    )
                }

                for (day in firstWeekDaysOfThisMonth) {
                    DayOfCalendar(
                        date = day,
                        actualDateState = actualDateState,
                        selectedOption = selectedOption,
                        colorScheme = colorScheme,
                        typography = typography
                    )
                }
             }

            Spacer(Modifier.height(10.dp))

            var currentWeek = firstWeekOfMonth + 1
            do {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp), Arrangement.SpaceBetween
                ) {
                    while (tempDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR) == currentWeek) {
                        if (tempDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR) == finalWeekOfMonth) finalWeekDaysCount += 1
                        val day = tempDate.dayOfMonth

                        DayOfCalendar(
                            date = tempDate,
                            actualDateState = actualDateState,
                            selectedOption = selectedOption,
                            colorScheme = colorScheme,
                            typography = typography
                        )
                        tempDate = tempDate.plusDays(1)
                    }
                    currentWeek += 1
                }
                Spacer(Modifier.height(10.dp))
            } while (tempDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR) != finalWeekOfMonth)

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp), Arrangement.SpaceBetween
            ) {
                var dateFinalWeek = tempDate.minusDays(finalWeekDaysCount.toLong() + 1)
                    .plusDays(1)
                val dateMonth = dateFinalWeek.monthValue
                for (i in 1..7) {
                    val day = dateFinalWeek.dayOfMonth

                    if (dateFinalWeek.monthValue == dateMonth) {
                        DayOfCalendar(
                            date = dateFinalWeek,
                            actualDateState = actualDateState,
                            selectedOption = selectedOption,
                            colorScheme = colorScheme,
                            typography = typography
                        )
                    } else {
                        DayOfCalendar(
                            date = dateFinalWeek,
                            actualDateState = actualDateState,
                            outOfMonth = true,
                            selectedOption = selectedOption,
                            colorScheme = colorScheme,
                            typography = typography
                        )
                    }

                    dateFinalWeek = dateFinalWeek.plusDays(1)
                }
            }

            Spacer(Modifier.height(25.dp))
        }
    }
}

@Composable
fun DayOfCalendar(
    date: LocalDate,
    actualDateState: MutableState<LocalDate>,
    outOfMonth: Boolean = false,
    selectedOption: MutableState<LocalDate?>,
    colorScheme: ColorScheme,
    typography: Typography
) {

    val day = date.dayOfMonth
    Box(
        Modifier
            .size(40.dp)
            .border(
                2.dp,
                if (date == LocalDate.now()) colorScheme.primary else Color.Transparent,
                CircleShape
            )
            .selectable(
                selected = (date == selectedOption.value),
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                if (date == selectedOption.value) selectedOption.value = null
                else {
                    if (outOfMonth) actualDateState.value =
                        actualDateState.value
                            .withMonth(date.monthValue)
                            .withYear(date.year)
                    selectedOption.value = date
                }
            }
            .background(
                if (date == selectedOption.value) colorScheme.primary else Color.Transparent,
                CircleShape
            ),
        contentAlignment = Center
    ) {
        if (outOfMonth) Text(
            day.toString(),
            style = typography.labelLarge,
            color = colorScheme.secondary,
            fontSize = 16.sp
        ) else Text(
            day.toString(),
            style = typography.labelLarge,
            fontSize = 16.sp
        )
    }
}

//@Preview
//@Composable
//fun DayOfCalendarPreview() {
//    MyGoalsAppTheme {
//        val actualDateState = remember { mutableStateOf(LocalDate.now()) }
//        val selectedOption = remember { mutableStateOf<LocalDate?>(null) }
//        Row(
//            Modifier
//                .background(MaterialTheme.colorScheme.secondary)
//                .clickable { selectedOption.value = null }) {
//            DayOfCalendar(
//                date = LocalDate.now(),
//                actualDateState = actualDateState,
//                selectedOption = selectedOption,
//                colorScheme = MaterialTheme.colorScheme,
//                typography = MaterialTheme.typography
//            )
//            Spacer(Modifier.width(30.dp))
//            DayOfCalendar(
//                date = LocalDate.now().plusDays(1),
//                actualDateState = actualDateState,
//                selectedOption = selectedOption,
//                colorScheme = MaterialTheme.colorScheme,
//                typography = MaterialTheme.typography
//            )
//        }
//    }
//}