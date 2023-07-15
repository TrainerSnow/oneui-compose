package org.oneui.compose.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.pow
import kotlin.math.sqrt

fun Offset.distanceTo(other: Offset): Float = sqrt(
    (x - other.x).pow(2) + (y - other.y).pow(2)
)