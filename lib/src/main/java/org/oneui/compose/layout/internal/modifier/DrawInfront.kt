package org.oneui.compose.layout.internal.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Utility modifier for mimicking the behaviour of [drawBehind], just infront of the composable
 *
 * @param onDraw The drawing action infront of the composable
 * @return The chained [Modifier]
 */
fun Modifier.overlay(
    onDraw: DrawScope.() -> Unit
): Modifier = this.then(
    drawWithContent {
        drawContent()
        onDraw(this)
    }
)