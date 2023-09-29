package org.oneui.compose.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme


/**
 * Composable for a oneui-style divider, to be used e.g. in lists of any kind
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param colors The [DividerColors] to apply
 */
@Composable
fun Divider(
    modifier: Modifier = Modifier,
    padding: PaddingValues = DividerDefaults.padding,
    strokeWidth: Dp = DividerDefaults.strokeWidth,
    colors: DividerColors = dividerColors()
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        val yCenter = size.height / 2

        drawLine(
            color = colors.stroke,
            strokeWidth = strokeWidth.toPx(),
            start = Offset(
                x = 0F,
                y = yCenter
            ),
            end = Offset(
                x = size.width,
                y = yCenter
            )
        )
    }
}


/**
 * Contains default values for a [Divider]
 */
object DividerDefaults {

    val strokeWidth = 1.dp

    val padding = PaddingValues(
        horizontal = 20.dp
    )

}


/**
 * Contains the colors used for a [Divider]
 */
data class DividerColors(

    val stroke: Color

)


/**
 * Constructs the default [DividerColors]
 *
 * @param stroke The color used for the stroke itself
 */
@Composable
fun dividerColors(
    stroke: Color = OneUITheme.colors.seslListDividerColor
): DividerColors = DividerColors(
    stroke = stroke
)