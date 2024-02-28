package io.tristanelizabeth.moonphase.enums

import at.kugel.zodiac.TextHoroscop
import kotlin.math.floor

const val RAD_PER_SIGN = (Math.PI * 2) / 12

enum class Planets {
    MOON, SUN, MERCURY, VENUS, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO;

    val titleStr: String get() {
        if (this == MOON) return "The moon is"
        else if (this == SUN) return "The sun is"
        return this.toString().lowercase().replaceFirstChar { it.uppercase() }
    }

    val titlePadding: Int get() {
        if (this == MOON) return 16
        return 8
    }

    val titleFontSize: Int get() {
        return when(this) {
            SUN -> 44
            MOON -> 42
            MERCURY -> 36
            VENUS -> 34
            MARS -> 32
            JUPITER -> 30
            SATURN -> 26
            URANUS -> 26
            NEPTUNE -> 24
            PLUTO -> 24
        }
    }

    val emoji: String get() {
        return when(this) {
            SUN -> "☀️"
            MOON -> "\uD83C\uDF19"
            MERCURY -> "\u263F"
            VENUS -> "\u2640"
            MARS -> "\u2642"
            JUPITER -> "\u2643"
            SATURN -> "\u2644"
            URANUS -> "\u26E2"
            NEPTUNE -> "\u2646"
            PLUTO -> "\u2647"
        }
    }

    fun signWhen(horoscope: TextHoroscop): AstrologicalSigns {
        val planetValue = horoscope.planet.planetsR[this.ordinal]
        val signIndex = floor(planetValue / RAD_PER_SIGN).toInt()
        return AstrologicalSigns.values()[signIndex]
    }
}