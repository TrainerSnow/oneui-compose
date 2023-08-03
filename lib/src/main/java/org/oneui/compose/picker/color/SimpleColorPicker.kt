package org.oneui.compose.picker.color

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.theme.locals.LocalBackgroundColor
import org.oneui.compose.widgets.menu.PopupMenu

/**
 * Composable for a oneui-style popup menu that allows the user to select a color from a predefined selection of colors.
 * This is a simpler and faster method than a normal color picker, but allows for less customization on the user end.
 *
 * @param modifier The [Modifier] to apply to the container
 * @param selectionColors The colors that the user can choose from
 * @param selectedColor The color that is currently selected. Null if none
 * @param colors The [SimpleColorPickerColors] to apply
 * @param onColorSelected The callback invoked when the user chooses a color
 * @param onDismissRequest The callback invoked when the user exits the popup
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SimpleColorPickerPopup(
    modifier: Modifier = Modifier,
    selectionColors: List<Color> = SimpleColorPickerDefaults.colors,
    selectedColor: Color? = selectionColors.first(),
    colors: SimpleColorPickerColors = simpleColorPickerColors(),
    onColorSelected: (Color) -> Unit,
    onDismissRequest: () -> Unit
) {
    PopupMenu(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier
                .padding(SimpleColorPickerDefaults.padding)
        ) {
            FlowRow(
                modifier = Modifier
                    .width(SimpleColorPickerDefaults.width),
                verticalArrangement = Arrangement
                    .spacedBy(SimpleColorPickerDefaults.verticalSpacing),
                horizontalArrangement = Arrangement
                    .spacedBy(SimpleColorPickerDefaults.horizontalSpacing)
            ) {
                val bg = LocalBackgroundColor.current
                selectionColors.forEach {
                    val selected = it == selectedColor

                    Canvas(
                        modifier = Modifier
                            .size(SimpleColorPickerDefaults.cellRadius * 2)
                            .clickable(
                                onClick = { onColorSelected(it) },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                    ) {
                        if(selected) {
                            drawCircle(
                                color = colors.selectedStroke,
                                center = Offset(
                                    x = size.width / 2,
                                    y = size.height / 2
                                ),
                                radius = SimpleColorPickerDefaults.selectedIndicatorRadius.toPx()
                            )
                        }
                        drawCircle(
                            color = bg,
                            center = Offset(
                                x = size.width / 2,
                                y = size.height / 2
                            ),
                            radius = (SimpleColorPickerDefaults.selectedIndicatorRadius  - SimpleColorPickerDefaults.selectedIndicatorWidth).toPx()
                        )
                        drawCircle(
                            color = it,
                            center = Offset(
                                x = size.width / 2,
                                y = size.height / 2
                            ),
                            radius = SimpleColorPickerDefaults.cellRadius.toPx()
                        )
                    }
                }
            }
        }
    }
}

/**
 * The colors needed for a [SimpleColorPickerPopup]
 */
data class SimpleColorPickerColors(

    val selectedStroke: Color

)

/**
 * Constructs the default [SimpleColorPickerPopup]
 *
 * @param selectedStroke The color that the currently selected color has
 */
@Composable
fun simpleColorPickerColors(
    selectedStroke: Color = OneUITheme.colors.simpleColorPickerSelectedStroke
): SimpleColorPickerColors = SimpleColorPickerColors(
    selectedStroke = selectedStroke
)

/**
 * Contains default values for a [SimpleColorPickerPopup]
 */
object SimpleColorPickerDefaults {

    val colors = listOf(
        0xff039be6,
        0xffd44245,
        0xfff27199,
        0xffeb9e5a,
        0xfffcca05,
        0xff5fc59d,
        0xff69b054,
        0xff63d1d2,
        0xff81aae8,
        0xff4d7adf,
        0xffb093e6,
        0xffa9a9a9
    ).map { Color(it) }

    val padding = PaddingValues(
        vertical = 20.dp,
        horizontal = 20.dp
    )

    val width = 250.dp

    val horizontalSpacing = 20.dp

    val verticalSpacing = 14.dp

    val cellRadius = 12.dp

    val selectedIndicatorRadius = 15.dp

    val selectedIndicatorWidth = 1.5.dp

}