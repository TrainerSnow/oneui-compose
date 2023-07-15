package org.oneui.compose.picker.color

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme


/**
 * Composable for a oneui-style color swatch. This is a grid, that displays [colors] as rows and columns for the user to select.
 *
 * TODO: Dragging the focus doesn't work yet. Only clicking on the cell works
 *
 * @param modifier The [Modifier] applied to the container
 * @param colors The colors, where each sublist represents a column in the grid
 * @param selectedColor The currently selected color
 * @param onSelectedColorChange The callback invoked when the selected color is changed
 * @param swatchColors The [ColorSwatchesColors] to apply
 */
@Composable
fun ColorSwatches(
    modifier: Modifier = Modifier,
    colors: List<List<Color>> = ColorSwatchesDefaults.colors,
    selectedColor: Color? = null,
    onSelectedColorChange: (Color) -> Unit,
    swatchColors: ColorSwatchesColors = colorSwatchesColors()
) {
    BoxWithConstraints(
        modifier = modifier
            .clip(ColorSwatchesDefaults.shape)
            .border(
                width = with(LocalDensity.current) { ColorSwatchesDefaults.strokeWidth.toDp() },
                color = swatchColors.cellStrokeColor,
                shape = ColorSwatchesDefaults.shape
            )
    ) {
        val width = maxWidth
        val height = maxHeight

        val columnWidth = width / colors.size
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            colors.forEach { colorColumn ->
                val cellHeight = height / colorColumn.size
                Column(
                    modifier = Modifier
                        .width(columnWidth)
                        .height(height)
                ) {
                    colorColumn.forEach { color ->
                        ColorSwatchCell(
                            color = color,
                            modifier = Modifier
                                .width(columnWidth)
                                .height(cellHeight)
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource(),
                                    role = null,
                                    onClick = { onSelectedColorChange(color) }
                                ),
                            selected = false
                        )
                    }
                }
            }
        }
        if (selectedColor != null) {
            colors.forEachIndexed { index, colorColumn ->
                if (selectedColor in colorColumn) {
                    val rowIndex = index
                    val columnIndex = colorColumn.indexOf(selectedColor)

                    val cellHeight = height / colorColumn.size

                    val topSpacing = columnIndex * cellHeight.value
                    val bottomSpacing = height.value - topSpacing - cellHeight.value
                    val startSpacing = rowIndex * columnWidth.value
                    val endSpacing = width.value - startSpacing - columnWidth.value

                    val extraSize = ColorSwatchesDefaults.selectedCellExtraSize
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = (topSpacing.dp - extraSize).coerceAtLeast(0.dp),
                                bottom = (bottomSpacing.dp - extraSize).coerceAtLeast(0.dp),
                                start = (startSpacing.dp - extraSize).coerceAtLeast(0.dp),
                                end = (endSpacing.dp - extraSize).coerceAtLeast(0.dp)
                            )
                    ) {
                        ColorSwatchCell(
                            color = selectedColor,
                            selected = true,
                            modifier = Modifier
                                .width(columnWidth + extraSize * 2)
                                .height(cellHeight + extraSize * 2),
                            border = colorColumn.size - columnIndex <= 2,
                            borderColor = swatchColors.cellStrokeColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun ColorSwatchCell(
    modifier: Modifier = Modifier,
    color: Color,
    selected: Boolean = false,
    border: Boolean = false,
    borderColor: Color? = null
) {
    val elMod = if (selected)
        Modifier
            .shadow(ColorSwatchesDefaults.selectedCellElevation)
    else Modifier

    val borderMod = if (border)
        Modifier
            .border(
                width = with(LocalDensity.current) { ColorSwatchesDefaults.strokeWidth.toDp() },
                color = borderColor!!
            )
    else Modifier

    Box(
        modifier = modifier
            .then(elMod)
            .background(color)
            .then(borderMod)
    )
}


/**
 * Contains default values for a [ColorSwatches], most notably the default colors to show
 */
object ColorSwatchesDefaults {

    //Contains the colors used in the swatches grid.
    val colors = listOf(
        listOf(
            -1,
            -3355444,
            -5000269,
            -6710887,
            -8224126,
            -10066330,
            -11711155,
            -13421773,
            -15066598,
            -16777216
        ),
        listOf(
            -22360,
            -38037,
            -49859,
            -60396,
            -65536,
            -393216,
            -2424832,
            -5767168,
            -10747904,
            -13434880
        ),
        listOf(
            -11096,
            -19093,
            -25544,
            -30705,
            -32768,
            -361216,
            -2396672,
            -5745664,
            -10736128,
            -13428224
        ),
        listOf(-88, -154, -200, -256, -256, -329216, -2368768, -6053120, -10724352, -13421824),
        listOf(
            -5701720,
            -10027162,
            -13041864,
            -16056566,
            -16711936,
            -16713216,
            -16721152,
            -16735488,
            -16753664,
            -16764160
        ),
        listOf(
            -5701685,
            -10027101,
            -13041784,
            -15728785,
            -16711834,
            -16714398,
            -16721064,
            -16735423,
            -16753627,
            -16764140
        ),
        listOf(
            -5701633,
            -10027009,
            -12713985,
            -16056321,
            -16711681,
            -16714251,
            -16720933,
            -16735325,
            -16753572,
            -16764109
        ),
        listOf(
            -5712641,
            -9718273,
            -13067009,
            -15430913,
            -16744193,
            -16744966,
            -16748837,
            -16755544,
            -16764575,
            -16770509
        ),
        listOf(
            -5723905,
            -9737217,
            -13092609,
            -16119041,
            -16776961,
            -16776966,
            -16776997,
            -16777048,
            -16777119,
            -16777165
        ),
        listOf(
            -3430145,
            -5870593,
            -7849729,
            -9498625,
            -10092289,
            -10223366,
            -11009829,
            -12386136,
            -14352292,
            -15466445
        ),
        listOf(
            -22273,
            -39169,
            -50945,
            -61441,
            -65281,
            -392966,
            -2424613,
            -5767000,
            -10420127,
            -13434829
        )
    ).map {
        it.map {
            Color(it)
        }
    }

    val selectedCellExtraSize = 3.dp

    val selectedCellElevation = 15.dp

    const val strokeWidth = 0.25F

    val shape = RoundedCornerShape(
        4.dp
    )
}


/**
 * Contains colors that are used for a [ColorSwatches]
 *
 * @property cellStrokeColor
 */
data class ColorSwatchesColors(

    val cellStrokeColor: Color

)


/**
 * Constructs the default [ColorSwatchesColors]
 *
 * @param cellStrokeColor The color of the stroke that's applied to a SwatchCell when selected
 */
@Composable
fun colorSwatchesColors(
    cellStrokeColor: Color = OneUITheme.colors.colorPickerStroke
): ColorSwatchesColors = ColorSwatchesColors(
    cellStrokeColor = cellStrokeColor
)