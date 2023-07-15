package org.oneui.compose.picker.color

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.theme.SeslTheme
import org.oneui.compose.util.fromHexString
import org.oneui.compose.util.hexString
import org.oneui.compose.widgets.EditText


/**
 * Composable for the section of the color picker that shows technical info about the currently picked color,
 *     such as HEX and RGB encoding, as well as a comparison between the recently selected value
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param currentColor The color that is selected at the time of opening the dialog
 * @param pickedColor The color that is picked in the current dialog session
 * @param onColorChange The callback invoked when the color is changed
 * @param colors The [SelectedColorSectionColors] to apply
 */
@Composable
fun SelectedColorSection(
    modifier: Modifier = Modifier,
    currentColor: Color,
    pickedColor: Color,
    onColorChange: (Color) -> Unit,
    colors: SelectedColorSectionColors = selectedColorSectionColors()
) {
    Box(
        modifier = modifier
            .padding(
                top = SeslTheme.dimensions.colorPickerSelectedMarginTop,
                start = SelectedColorSectionDefaults.marginStart
            )
            .height(SelectedColorSectionDefaults.height)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ContrastSection(
                modifier = Modifier
                    .width(SelectedColorSectionDefaults.colorSectionWidth)
                    .height(SelectedColorSectionDefaults.colorSectionHeight),
                picked = pickedColor,
                current = currentColor,
                outlineColor = colors.selectedSectionOutline
            )
            Spacer(
                modifier = Modifier
                    .width(SeslTheme.dimensions.colorPickerHexSectionSpacing)
            )
            HexSection(
                modifier = Modifier
                    .width(SelectedColorSectionDefaults.hexSectionWidth),
                color = pickedColor,
                onColorChange = onColorChange
            )
            RgbSection(
                color = pickedColor,
                onColorChange = onColorChange
            )
        }
    }
}

/**
 * Section that contrasts the recently selected color and currently picked color
 */
@Composable
internal fun ContrastSection(
    modifier: Modifier = Modifier,
    picked: Color,
    current: Color,
    outlineColor: Color
) {
    val leftShape = RoundedCornerShape(
        topStart = SelectedColorSectionDefaults.colorSectionCornerRadius,
        bottomStart = SelectedColorSectionDefaults.colorSectionCornerRadius
    )
    val rightShape = RoundedCornerShape(
        topEnd = SelectedColorSectionDefaults.colorSectionCornerRadius,
        bottomEnd = SelectedColorSectionDefaults.colorSectionCornerRadius
    )
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = current,
                    shape = leftShape
                )
                .clip(leftShape)
                .fillMaxHeight()
                .weight(1F)
                .border(
                    width = with(LocalDensity.current) { SelectedColorSectionDefaults.colorSectionStrokeWidth.toDp() },
                    color = outlineColor,
                    shape = leftShape
                )
        )
        Box(
            modifier = Modifier
                .background(
                    color = picked,
                    shape = rightShape
                )
                .clip(rightShape)
                .fillMaxHeight()
                .weight(1F)
                .border(
                    width = with(LocalDensity.current) { SelectedColorSectionDefaults.colorSectionStrokeWidth.toDp() },
                    color = outlineColor,
                    shape = rightShape
                )
        )
    }
}

/**
 * Section that shows the currently picked color in HEX encoding, and allows for editing
 */
@Composable
internal fun HexSection(
    modifier: Modifier = Modifier,
    color: Color,
    onColorChange: (Color) -> Unit
) {
    var inputString by remember(color) {
        mutableStateOf(color.hexString)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier
                .height(SelectedColorSectionDefaults.hexSectionTitleHeight),
            text = stringResource(R.string.sesl_picker_color_hex),
            style = SeslTheme.types.colorPickerHexSectionTitle
        )
        EditText(
            modifier = Modifier
                .widthIn(
                    min = SelectedColorSectionDefaults.hexSelectionEditMinWidth
                )
                .height(SelectedColorSectionDefaults.hexSectionEditHeight),
            value = inputString,
            onValueChange = {
                inputString = it
                try {
                    val c = Color.fromHexString(inputString)
                    onColorChange(c)
                } catch (_: Exception) { //Ignored
                }
            },
            style = SeslTheme.types.colorPickerHexSectionEdit,
            singleLine = true,
            maxCharacters = 6,
            characters = "abcdefABCDEF0123456789".toCharArray()
        )
    }
}


/**
 * Section that shows the currently selected color in RGB encoding and allows for editing
 */
@Composable
internal fun RgbSection(
    modifier: Modifier = Modifier,
    color: Color,
    onColorChange: (Color) -> Unit
) {
    val rgb = color.run { listOf(red, green, blue) }.map { (it * 255).toInt() }
    Row(
        modifier = modifier
            .fillMaxHeight()
    ) {
        Spacer(
            modifier = Modifier
                .width(SeslTheme.dimensions.colorPickerRgbSectionsSpacing)
        )
        RgbSectionUnit(
            modifier = Modifier
                .width(SelectedColorSectionDefaults.rgbSectionsWidth),
            title = stringResource(R.string.sesl_picker_color_red),
            value = rgb[0],
            onValueChange = {
                onColorChange(
                    color.copy(
                        red = it / 255F
                    )
                )
            }
        )

        Spacer(
            modifier = Modifier
                .width(SeslTheme.dimensions.colorPickerRgbSectionsSpacing)
        )
        RgbSectionUnit(
            modifier = Modifier
                .width(SelectedColorSectionDefaults.rgbSectionsWidth),
            title = stringResource(R.string.sesl_picker_color_green),
            value = rgb[1],
            onValueChange = {
                onColorChange(
                    color.copy(
                        green = it / 255F
                    )
                )
            }
        )

        Spacer(
            modifier = Modifier
                .width(SeslTheme.dimensions.colorPickerRgbSectionsSpacing)
        )
        RgbSectionUnit(
            modifier = Modifier
                .width(SelectedColorSectionDefaults.rgbSectionsWidth),
            title = stringResource(R.string.sesl_picker_color_blue),
            value = rgb[2],
            onValueChange = {
                onColorChange(
                    color.copy(
                        blue = it / 255F
                    )
                )
            }
        )
    }
}

/**
 * Singular unit for the RGB section, that is responsible for one component of RGB, e.g. red
 */
@Composable
internal fun RgbSectionUnit(
    modifier: Modifier = Modifier,
    title: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    assert(value in (0..255))
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier
                .height(SelectedColorSectionDefaults.rgbSectionTitleHeight),
            text = title,
            style = SeslTheme.types.colorPickerRgbSectionTitle
        )
        EditText(
            modifier = Modifier
                .widthIn(
                    min = SelectedColorSectionDefaults.rgbSectionsEditMinWidth
                )
                .height(SelectedColorSectionDefaults.rgbSectionEditHeight),
            value = value.toString(),
            onValueChange = {
                val number =
                    if (it.isEmpty()) 0
                    else if (it.toCharArray().all { it == '0' }) 0
                    else if (it.startsWith("0")) it.replace("0", "").toInt()
                    else it.toInt()
                onValueChange(
                    number.coerceIn(
                        minimumValue = 0,
                        maximumValue = 255
                    )
                )
            },
            style = SeslTheme.types.colorPickerHexSectionEdit,
            singleLine = true,
            maxCharacters = 3,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
    }
}


/**
 * Contains default values for the [SelectedColorSection]
 */
object SelectedColorSectionDefaults {

    val height = 42.dp
    val marginStart = 2.dp

    val colorSectionWidth = 56.dp
    val colorSectionHeight = 36.dp
    const val colorSectionStrokeWidth = 1F
    val colorSectionCornerRadius = 10.dp

    val hexSectionWidth = 85.dp
    val hexSelectionEditMinWidth = 24.dp
    val hexSectionTitleHeight = 18.dp
    val hexSectionEditHeight = 24.dp

    val rgbSectionsWidth = 42.dp
    val rgbSectionsEditMinWidth = 30.dp
    val rgbSectionTitleHeight = 18.dp
    val rgbSectionEditHeight = 24.dp
}


/**
 * Contains the colors used for a [SelectedColorSection]
 */
data class SelectedColorSectionColors(

    val selectedSectionOutline: Color

)

/**
 * Constructs the default [SelectedColorSectionColors]
 *
 * @param selectedSectionOutline The outline color for the [ContrastSection]
 */
@Composable
fun selectedColorSectionColors(
    selectedSectionOutline: Color = SeslTheme.colors.colorPickerStroke
): SelectedColorSectionColors = SelectedColorSectionColors(
    selectedSectionOutline = selectedSectionOutline
)