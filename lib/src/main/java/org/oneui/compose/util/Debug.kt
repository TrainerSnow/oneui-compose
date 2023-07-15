package org.oneui.compose.util

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

fun Modifier.debugBorder() = this.then(
    border(
        width = 0.5.dp,
        color = Color(
            red = rand.nextFloat(),
            green = rand.nextFloat(),
            blue = rand.nextFloat()
        )
    )
)

val rand = Random(1)