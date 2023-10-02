package org.oneui.compose.navigation.rail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme

/**
 * Composable for a oneui-style navigation rail divider divider, that separates content in a [NavigationRail]
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param colors The [NavigationRailDividerColors] to apply
 */
@Composable
fun NavigationRailDivider(
    modifier: Modifier = Modifier,
    colors: NavigationRailDividerColors = navigationRailDividerColors()
) = Box(
    modifier = Modifier
        .padding(NavigationRailDividerDefaults.margin)
) {
    Canvas(
        modifier = modifier
            .height(NavigationRailDividerDefaults.height)
            .fillMaxWidth()
    ) {
        val y = size.height / 2
        val xEnd = size.width

        val xStep = 12F
        val radius = 2F

        var pos = 0F

        while (pos < xEnd) {
            pos += xStep

            drawCircle(
                color = colors.dotColor,
                radius = radius,
                center = Offset(
                    pos,
                    y
                )
            )
        }
    }
}

/**
 * Contains colors needed for a [NavigationRailDivider]
 */
data class NavigationRailDividerColors(

    val dotColor: Color

)

/**
 * Constructs the default [NavigationRailDividerColors]
 *
 * @param dotColor The color of the dotted line
 */
@Composable
fun navigationRailDividerColors(
    dotColor: Color = OneUITheme.colors.seslPrimaryTextColor.copy(alpha = 0.5F)
): NavigationRailDividerColors = NavigationRailDividerColors(
    dotColor = dotColor
)

/**
 * Contains default values for a [NavigationRailDivider]
 */
object NavigationRailDividerDefaults {

    val margin = PaddingValues(
        vertical = 6.dp,
        horizontal = 24.dp
    )

    val height = 3.dp

}