package org.oneui.compose.util

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt

/**
 * Converts the [Color] to a rbg-hex string
 */
val Color.hexString: String
    get() = String.format(
        "%02X%02X%02X", (red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt()
    )

/**
 * Parses a [Color] from [hexString]
 */
fun Color.Companion.fromHexString(hexString: String): Color =
    Color(android.graphics.Color.parseColor("#$hexString"))

/**
 * Converts a color to a hsl-triple
 */
val Color.hsl: Triple<Float, Float, Float>
    get() {
        val arr = FloatArray(3)
        ColorUtils.RGBToHSL(
            (red * 255).roundToInt(),
            (green * 255).roundToInt(),
            (blue * 255).roundToInt(),
            arr
        )
        return Triple(arr[0], arr[1], arr[2])
    }
