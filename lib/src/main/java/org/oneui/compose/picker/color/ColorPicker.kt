package org.oneui.compose.picker.color

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.dialog.AlertDialogDefaults
import org.oneui.compose.dialog.BaseDialog
import org.oneui.compose.dialog.DialogButton
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.hsl

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    state: ColorPickerState = remember {
        ColorPickerState(Color.Black)
    }
) {
    var tabSelection by remember {
        mutableStateOf(ColorPickerTabSelection.Swatches)
    }

    val width = OneUITheme.dimensions.colorPickerContentWidth

    val swatchHeight = OneUITheme.dimensions.colorPickerSwatchHeight
    val swatchWidth = OneUITheme.dimensions.colorPickerSwatchWidth
    val swatchMargin = PaddingValues(
        top = OneUITheme.dimensions.colorPickerSwatchMarginTop,
        bottom = OneUITheme.dimensions.colorPickerSwatchMarginTop
    )

    val specHeight = OneUITheme.dimensions.colorPickerSpectrumHeight
    val specWidth = OneUITheme.dimensions.colorPickerSpectrumWidth
    val specMargin = PaddingValues(
        top = OneUITheme.dimensions.colorPickerSwatchMarginTop
    )

    Column(
        modifier = modifier
            .width(width)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    onSelectedColorChange = {
                        state.selected = it
                    },
                    selectedColor = state.selected
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
                    selectedColor = state.selected,
                    onSelectedColorChange = { hue, sat ->
                        val hsl = state.selected.hsl
                        state.selected = Color.hsl(
                            hue, sat, hsl.third
                        )
                    }
                )
            }
        }
        if (tabSelection == ColorPickerTabSelection.Spectrum) {
            ColorPickerSeekbar(
                value = state.selected.hsl.second,
                onValueChange = { sat ->
                    val hsl = state.selected.hsl
                    state.selected = Color.hsl(
                        hsl.first, sat, hsl.third
                    )
                },
                colorStart = Color.Black,
                colorEnd = state.selected,
                title = stringResource(R.string.sesl_picker_seekbar_saturation)
            )
        }
        ColorPickerSeekbar(
            value = state.selected.alpha,
            onValueChange = { alpha ->
                state.selected = state.selected.copy(
                    alpha = alpha
                )
            },
            colorStart = Color.Transparent,
            colorEnd = state.selected.copy(
                alpha = 1F
            ),
            title = stringResource(R.string.sesl_picker_seekbar_opacity)
        )
        SelectedColorSection(
            currentColor = state.selected,
            pickedColor = state.initial,
            onColorChange = { color ->
                state.selected = color
            }
        )
    }
}

data class ColorPickerState(
    val initial: Color
) {

    var selected by mutableStateOf(initial)

}

@Composable
fun ColorPickerDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    selectedColor: Color = Color.Black,
    onColorSelected: (Color) -> Unit
) {
    val state  = remember {
        ColorPickerState(selectedColor)
    }

    val width = OneUITheme.dimensions.colorPickerContentWidth

    BaseDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        width = width
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            ColorPicker(
                state = state
            )

            Spacer(
                modifier = Modifier
                    .height(AlertDialogDefaults.bodyButtonSpacing)
            )
            Row(
                modifier = Modifier
                    .width(width)
                    .padding(
                        horizontal = 24.dp
                    )
            ) {
                DialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = stringResource(id = R.string.sesl_dialog_picker_date_negative),
                    onClick = onDismissRequest
                )
                DialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = stringResource(id = R.string.sesl_dialog_picker_date_positive),
                    onClick = {
                        onColorSelected(state.selected)
                        onDismissRequest()
                    }
                )
            }
        }
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