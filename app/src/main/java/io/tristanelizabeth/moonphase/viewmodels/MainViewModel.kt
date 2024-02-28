package io.tristanelizabeth.moonphase.viewmodels

import at.kugel.zodiac.TextHoroscop
import at.kugel.zodiac.house.HousePlacidus
import at.kugel.zodiac.planet.PlanetAA0
import io.tristanelizabeth.moonphase.activities.LOCATION_LAT
import io.tristanelizabeth.moonphase.activities.LOCATION_LONG
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel {
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
}