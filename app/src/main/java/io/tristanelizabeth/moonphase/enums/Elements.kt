package io.tristanelizabeth.moonphase.enums

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import io.tristanelizabeth.moonphase.ui.theme.Air01
import io.tristanelizabeth.moonphase.ui.theme.Earth01
import io.tristanelizabeth.moonphase.ui.theme.Earth02
import io.tristanelizabeth.moonphase.ui.theme.Fire01
import io.tristanelizabeth.moonphase.ui.theme.Fire02
import io.tristanelizabeth.moonphase.ui.theme.Water01
import io.tristanelizabeth.moonphase.ui.theme.Water02

enum class Elements {
    FIRE, EARTH, AIR, WATER;

    fun brush(): Brush {
        return when(this) {
            FIRE ->
                Brush.linearGradient(
                    colors = listOf(Fire01, Fire02))
            EARTH ->
                Brush.linearGradient(
                    colors = listOf(Earth01, Earth02))
            AIR ->
                Brush.linearGradient(
                    colors = listOf(Air01, Color.White))
            WATER ->
                Brush.linearGradient(
                    colors = listOf(Water01, Water02))
        }
    }
}

