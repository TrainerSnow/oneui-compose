package org.oneui.compose.progress

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Different sizes of the circular progress bars
 *
 */
enum class CircularProgressBarSize(
    val size: Dp,
    val strokeSize: Dp = ((2F / 11F) * size.value).dp
) {

    Small(24.dp),

    Medium(48.dp),

    Large(60.dp),

    XLarge(75.dp)

}