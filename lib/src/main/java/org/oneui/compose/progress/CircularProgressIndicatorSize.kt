package org.oneui.compose.progress

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Different sizes of the circular progress bars
 */
open class CircularProgressIndicatorSize(
    val size: Dp,
    val strokeSize: Dp = ((2F / 11F) * size.value).dp
) {


    companion object {

        object Small : CircularProgressIndicatorSize(24.dp)

        object Medium : CircularProgressIndicatorSize(48.dp)

        object Large : CircularProgressIndicatorSize(60.dp)

        object XLarge : CircularProgressIndicatorSize(75.dp)

    }

}