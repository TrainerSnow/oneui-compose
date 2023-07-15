package org.oneui.compose.layout.drawer

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme

object DrawerDividerDefaults {

    val margin = PaddingValues(
        vertical = 6.dp,
        horizontal = 24.dp
    )

    val height = 3.dp

}

data class DrawerDividerColors(

    val dotColor: Color

)

@Composable
fun drawerDividerColors(
    dotColor: Color = OneUITheme.colors.drawerDividerColor
): DrawerDividerColors = DrawerDividerColors(
    dotColor = dotColor
)

@Composable
fun DrawerDivider(
    modifier: Modifier = Modifier,
    margin: PaddingValues = DrawerDividerDefaults.margin,
    height: Dp = DrawerDividerDefaults.height,
    colors: DrawerDividerColors = drawerDividerColors()
) = Box(
    modifier = Modifier
        .padding(margin)
) {
    Canvas(
        modifier = modifier
            .height(height)
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