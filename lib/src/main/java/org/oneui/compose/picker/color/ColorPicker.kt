package org.oneui.compose.picker.color

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.dialog.BaseDialog
import org.oneui.compose.theme.SeslTheme
import org.oneui.compose.util.debugBorder
import org.oneui.compose.util.hsl

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    selectedColor: Color,
    pickedColor: Color,
    onAlphaChange: (Float) -> Unit,
    onRgbChange: (Float, Float, Float) -> Unit,
    onSaturationChange: (Float) -> Unit,
    onHslChange: (Float, Float, Float) -> Unit,
    onColorChange: (Color) -> Unit
) {
    var tabSelection by remember {
        mutableStateOf(ColorPickerTabSelection.Swatches)
    }

    val width = SeslTheme.dimensions.colorPickerContentWidth

    val swatchHeight = SeslTheme.dimensions.colorPickerSwatchHeight
    val swatchWidth = SeslTheme.dimensions.colorPickerSwatchWidth
    val swatchMargin = PaddingValues(
        top = SeslTheme.dimensions.colorPickerSwatchMarginTop,
        bottom = SeslTheme.dimensions.colorPickerSwatchMarginTop
    )

    val specHeight = SeslTheme.dimensions.colorPickerSpectrumHeight
    val specWidth = SeslTheme.dimensions.colorPickerSpectrumWidth
    val specMargin = PaddingValues(
        top = SeslTheme.dimensions.colorPickerSwatchMarginTop
    )

    Column(
        modifier = modifier
            .width(width)
            .wrapContentHeight()
            .padding(ColorPickerDefaults.padding)
    ) {
        ColorPickerTabLayout(
            modifier = Modifier
                .fillMaxWidth(),
            selected = tabSelection,
            onSelectedChange = { tabSelection = it }
        )
        if (tabSelection == ColorPickerTabSelection.Swatches) {
            Box(
                modifier = Modifier
                    .height(swatchHeight)
                    .width(swatchWidth)
                    .padding(swatchMargin)
            ) {
                ColorSwatches(
                    modifier = Modifier
                        .fillMaxSize(),
                    onSelectedColorChange = onColorChange,
                    selectedColor = pickedColor
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .height(specHeight)
                    .width(specWidth)
                    .padding(specMargin)
            ) {
                ColorSpectrum(
                    modifier = Modifier
                        .fillMaxSize(),
                    selectedColor = pickedColor,
                    onSelectedColorChange = { hue, sat ->
                        onHslChange(hue, sat, pickedColor.hsl.third)
                    }
                )
            }
        }
        if (tabSelection == ColorPickerTabSelection.Spectrum) {
            ColorPickerSeekbar(
                value = pickedColor.hsl.second,
                onValueChange = onSaturationChange,
                colorStart = Color.Black,
                colorEnd = pickedColor,
                title = stringResource(R.string.sesl_picker_seekbar_saturation)
            )
        }
        ColorPickerSeekbar(
            modifier = Modifier
                .debugBorder(),
            value = pickedColor.alpha,
            onValueChange = onAlphaChange,
            colorStart = Color.Transparent,
            colorEnd = pickedColor.copy(
                alpha = 1F
            ),
            title = stringResource(R.string.sesl_picker_seekbar_opacity)
        )
        SelectedColorSection(
            currentColor = selectedColor,
            pickedColor = pickedColor,
            onColorChange = onColorChange
        )
    }
}

@Composable
fun ColorPickerDialog(
    modifier: Modifier = Modifier,
    selectedColor: Color = Color.Black,
    recentlyUsedColors: List<Color> = emptyList(),
    onDismissRequest: () -> Unit,
    onColorPicked: (Color) -> Unit
) {
    var pickedColor by remember {
        mutableStateOf(Color.Black)
    }
    BaseDialog(
        onDismissRequest = onDismissRequest,
        padding = PaddingValues(0.dp),
        width = SeslTheme.dimensions.colorPickerContentWidth
    ) {
        ColorPicker(
            selectedColor = selectedColor,
            pickedColor = pickedColor,
            onAlphaChange = {
                pickedColor = pickedColor.copy(
                    alpha = it
                )
            },
            onSaturationChange = {
                pickedColor = Color.hsl(
                    hue = pickedColor.hsl.first,
                    saturation = it,
                    lightness = pickedColor.hsl.third
                )
            },
            onHslChange = { h, s, l ->
                pickedColor = Color.hsl(
                    hue = h,
                    saturation = s,
                    lightness = l
                )
            },
            onRgbChange = { r, g, b ->
                pickedColor = pickedColor.copy(
                    red = r,
                    green = g,
                    blue = b
                )
            },
            onColorChange = {
                pickedColor = it
            }
        )
    }
}

object ColorPickerDefaults {

    val padding = PaddingValues(
        start = 20.dp,
        end = 20.dp,
        top = 12.dp,
        bottom = 20.dp
    )

}