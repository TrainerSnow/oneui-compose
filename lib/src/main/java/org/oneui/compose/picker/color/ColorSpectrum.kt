package org.oneui.compose.picker.color

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme

/**
 * Composable for a oneui-style color spectrum. This allows the user to select one color
 *     from a large-scale color selection.
 *
 * TODO: Dragging the cursor doesn't yet work. Only clicking works
 *
 * @param modifier The modifier to apply to the container
 * @param colors The [ColorSpectrumColors] to apply
 * @param selectedColor The currently selected color
 * @param onSelectedColorChange The callback invoked when a new color is selected. Param 1: hue, Param 2: saturation
 * @param colors The list of colors that is used as a base for the color spectrum.
 */
@Composable
fun ColorSpectrum(
    modifier: Modifier = Modifier,
    colors: ColorSpectrumColors = colorSpectrumColors(),
    selectedColor: Color,
    onSelectedColorChange: (hue: Float, saturation: Float) -> Unit,
) {
    val cursorRadius = OneUITheme.dimensions.colorPickerSpectrumCursorRadius

    var cursorPos by remember {
        mutableStateOf(Offset.Zero)
    }

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        cursorPos = offset

                        val hue = (offset.x / size.width) * 300F
                        val sat = (offset.y / size.height)
                        onSelectedColorChange(hue, sat)
                    }
                )
            }
    ) {
        val hueBrush = Brush.linearGradient(
            colors = colors.hueColors,
            start = Offset(x = size.width, y = size.height / 2),
            end = Offset(x = 0F, y = size.height / 2)
        )
        //Saturation brush is linear gradiant of white to transparent, overlaid
        val saturationBrush = Brush.linearGradient(
            colors = listOf(Color.White, Color.Transparent),
            start = Offset(x = size.width / 2, y = 0F),
            end = Offset(x = size.width / 2, y = size.height)
        )

        drawRoundRect(
            brush = hueBrush,
            size = size,
            cornerRadius = CornerRadius(
                x = ColorSpectrumDefaults.cornerRadius.toPx(),
                y = ColorSpectrumDefaults.cornerRadius.toPx()
            )
        )

        drawRoundRect(
            brush = saturationBrush,
            size = size,
            cornerRadius = CornerRadius(
                x = ColorSpectrumDefaults.cornerRadius.toPx(),
                y = ColorSpectrumDefaults.cornerRadius.toPx()
            )
        )

        cursorFill(
            color = selectedColor,
            radius = cursorRadius,
            position = cursorPos
        )
        cursor(
            color = colors.cursorOutline,
            radius = cursorRadius,
            position = cursorPos
        )
    }
}

internal fun DrawScope.cursor(
    color: Color,
    radius: Dp,
    position: Offset
) {
    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = position,
        style = Stroke(
            width = ColorSpectrumDefaults.cursorOutlineWidth.toPx()
        )
    )
}

internal fun DrawScope.cursorFill(
    color: Color,
    radius: Dp,
    position: Offset
) {
    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = position,
        style = Fill
    )
}


/**
 * Contains default values for a [ColorSpectrum]
 */
object ColorSpectrumDefaults {

    const val strokeWidth = 2F

    val cornerRadius = 4.dp

    val hueColors = listOf(
        -65281, -16776961, -16711681, -16711936, -256, -65536
    ).map { Color(it) }

    val cursorOutlineWidth = 2.dp

}


/**
 * Contains the colors used for a [ColorSpectrum]
 */
data class ColorSpectrumColors(

    val cursorOutline: Color,

    val hueColors: List<Color>

)


/**
 * Constructs the default [ColorSpectrumColors]
 *
 * @param cursorOutline The color of the cursor outline
 */
@Composable
fun colorSpectrumColors(
    cursorOutline: Color = OneUITheme.colors.white,
    hueColors: List<Color> = ColorSpectrumDefaults.hueColors
): ColorSpectrumColors = ColorSpectrumColors(
    cursorOutline = cursorOutline,
    hueColors = hueColors
)