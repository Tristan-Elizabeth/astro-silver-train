package io.tristanelizabeth.moonphase.enums

import androidx.compose.ui.graphics.Brush

enum class AstrologicalSigns {
    ARIES, TAURUS, GEMINI, CANCER,
    LEO, VIRGO, LIBRA, SCORPIO,
    SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES;

    val emoji: String get() {
        return when(this) {
            ARIES -> "\u2648"
            TAURUS -> "\u2649"
            GEMINI -> "\u264A"
            CANCER -> "\u264B"
            LEO -> "\u264C"
            VIRGO -> "\u264D"
            LIBRA -> "\u264E"
            SCORPIO -> "\u264F"
            SAGITTARIUS -> "\u2650"
            CAPRICORN -> "\u2651"
            AQUARIUS -> "\u2652"
            PISCES -> "\u2653"
        }
    }

    val titlecase: String get() {
        return this.toString().lowercase().replaceFirstChar { it.uppercase() }
    }

    fun brush(): Brush {
        return when(this) {
            ARIES -> Elements.FIRE.brush()
            TAURUS -> Elements.EARTH.brush()
            GEMINI -> Elements.AIR.brush()
            CANCER -> Elements.WATER.brush()
            LEO -> Elements.FIRE.brush()
            VIRGO -> Elements.EARTH.brush()
            LIBRA -> Elements.AIR.brush()
            SCORPIO -> Elements.WATER.brush()
            SAGITTARIUS -> Elements.FIRE.brush()
            CAPRICORN -> Elements.EARTH.brush()
            AQUARIUS -> Elements.AIR.brush()
            PISCES -> Elements.WATER.brush()
        }
    }
}