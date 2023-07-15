package org.oneui.compose.preference.misc

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme


/**
 * Composable for a oneui-style divider for a list of preferences
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param colors The [PreferenceListDividerColors] to apply
 */
@Composable
fun PreferenceListDivider(
    modifier: Modifier = Modifier,
    colors: PreferenceListDividerColors = preferenceListDividerColors()
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(PreferenceListDividerDefaults.padding)
    ) {
        val yCenter = size.height / 2

        drawLine(
            color = colors.stroke,
            strokeWidth = PreferenceListDividerDefaults.strokeWidth.toPx(),
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
 * Contains default values for a [PreferenceListDivider]
 */
object PreferenceListDividerDefaults {

    val strokeWidth = 1.dp

    val padding = PaddingValues(
        horizontal = 20.dp
    )

}


/**
 * Contains the colors used for a [PreferenceListDivider]
 */
data class PreferenceListDividerColors(

    val stroke: Color

)


/**
 * Constructs the default [PreferenceListDividerColors]
 *
 * @param stroke The color used for the stroke itself
 */
@Composable
fun preferenceListDividerColors(
    stroke: Color = OneUITheme.colors.seslListDividerColor
): PreferenceListDividerColors = PreferenceListDividerColors(
    stroke = stroke
)