package io.tristanelizabeth.moonphase.activities

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.kugel.zodiac.TextHoroscop
import at.kugel.zodiac.house.HousePlacidus
import at.kugel.zodiac.planet.PlanetAA0


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

import io.tristanelizabeth.moonphase.enums.Planets
import io.tristanelizabeth.moonphase.ui.theme.MoonPhaseTheme
import io.tristanelizabeth.moonphase.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val LOCATION_NAME = "Edmonton, Alberta"
const val LOCATION_LAT = 53.5461
const val LOCATION_LONG = -113.4937


class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MoonPhaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScaffoldWithTopBar()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar() {
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val timeFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault())}
    val dateFormat =  remember { SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())}

    val sdf =  remember { SimpleDateFormat("MMMM dd, yyyy - HH:mm:ss", Locale.getDefault())}
    var c: Date = Calendar.getInstance().time
    var horoscope = createHoroscope(c)

    Log.i("HOROSCOPE", horoscope.toString())

    var moonSign = Planets.MOON.signWhen(horoscope)
    var brush = moonSign.brush()

    LaunchedEffect(key1 = Unit){
        while(isActive){
            time = timeFormat.format(Date())
            date = dateFormat.format(Date())

            c = Calendar.getInstance().time
            horoscope = createHoroscope(c)

            moonSign = Planets.MOON.signWhen(horoscope)
            brush = moonSign.brush()

            delay(1000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row() {
                        Text(
                            text = date,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 50.sp,
                            color = Color.Black,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )

        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff8d6e63)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CurrentHoroscope(it, brush, time, horoscope)
            }
        },
    )
}

@Composable
fun CurrentHoroscope(paddingValues: PaddingValues, brush: Brush, time: String, horoscope: TextHoroscop, modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${Planets.MOON.emoji} ${Planets.MOON.signWhen(horoscope).emoji}",
            modifier = modifier.padding(16.dp),
            fontSize = 100.sp,
        )
        Text(
            text = time,
            modifier = modifier.padding(16.dp),
            fontSize = 40.sp,
        )
        Text(
            text = LOCATION_NAME,
            modifier = modifier.padding(16.dp),
            fontSize = 30.sp
        )
        enumValues<Planets>().forEach {
            PlanetStatement(horoscope = horoscope, planet = it)
        }
    }
}

@Composable
fun PlanetStatement(horoscope: TextHoroscop, planet: Planets, modifier: Modifier = Modifier) {
    val sign = getSign(horoscope, planet)
    val paddingValue = planet.titlePadding
    val titleFontSize = planet.titleFontSize
    Column () {
        Text (
            text= "${planet.titleStr} in $sign until",
            modifier = modifier.padding(paddingValue.dp),
            fontSize = titleFontSize.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoonPhaseTheme() {
        ScaffoldWithTopBar()
    }
}

// TODO: Move these functions to a more appropriate place.
fun getSign(horoscope: TextHoroscop, planet: Planets): String {
    return planet.signWhen(horoscope).titlecase
}

fun createHoroscope(date: Date): TextHoroscop {
    val horoscope = TextHoroscop()
    horoscope.planet = PlanetAA0()
    horoscope.house = HousePlacidus()

    val day = SimpleDateFormat("d", Locale.getDefault()).format(date).toInt()
    val month = SimpleDateFormat("M", Locale.getDefault()).format(date).toInt()
    val year = SimpleDateFormat("Y", Locale.getDefault()).format(date).toInt()
    val hour = SimpleDateFormat("H", Locale.getDefault()).format(date).toInt()
    val minute = SimpleDateFormat("m", Locale.getDefault()).format(date).toInt()
    val second = SimpleDateFormat("s", Locale.getDefault()).format(date).toInt()
    val timeZone = 0.0

    horoscope.setTime(day, month, year, hour, minute, second, timeZone)

    horoscope.setLocation(LOCATION_LAT, LOCATION_LONG)

    horoscope.calcValues()

    return horoscope
}