package org.oneui.compose.theme.dimensions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit


/**
 * Contains dimensions (pixel, dp, sp etc values) that change dynamically with device specifications
 *     (Such as rotation or width/height) like dialog height/widths.
 */
interface IDynamicDimensions {

    /**
     * Minimum width a dialog should take up, in % of max width, if displayed on the major axis.
     * Dependant on screen height, width
     */
    val dialogMinWidthMajor: Float

    /**
     * Minimum width a dialog should take up, in % of max width, if displayed on the minor axis.
     * Dependant on screen height, width
     */
    val dialogMinWidthMinor: Float

    /**
     * Minimum width a dialog should take up, in % of max width
     * Dependant on screen height, width and rotation state
     */
    val dialogMinWidth: Float

    /**
     * Horizontal padding for the SwitchBar. IDK why this is called margin
     */
    val switchBarMarginHorizontal: Dp

    /**
     * Text size of the TextStyle for the SwitchBar label text
     */
    val switchBarLabelTextSize: TextUnit

    /**
     * Width of the divider used in the switch screen preference, in px
     */
    val preferenceSwitchScreenDividerWidth: Float

    /**
     * Color picker spectrum height
     */
    val colorPickerSpectrumHeight: Dp

    /**
     * Color picker spectrum width
     */
    val colorPickerSpectrumWidth: Dp

    /**
     * Radius of the cursor for the color picker spectrum
     */
    val colorPickerSpectrumCursorRadius: Dp

    /**
     * Top margin for the selected color region of the color picker
     */
    val colorPickerSelectedMarginTop: Dp

    /**
     * Spacing for the hex section of the color picker
     */
    val colorPickerHexSectionSpacing: Dp

    /**
     * Spacing between each rgb section of the color picker
     */
    val colorPickerRgbSectionsSpacing: Dp

    /**
     * Horizontal margin for any color picker seekbar
     */
    val colorPickerSeekbarMarginHorizontal: Dp

    /**
     * Bottom margin for any color picker seekbar
     */
    val colorPickerSeekbarMarginBottom: Dp

    /**
     * Spacing between a seekbar and its' label
     */
    val colorPickerSeekbarLabelSpacing: Dp

    /**
     * Horizontal margin for the color picker tab layout
     */
    val colorPickerTabLayoutMarginHorizontal: Dp

    /**
     * Content width of the color picker
     */
    val colorPickerContentWidth: Dp

    val colorPickerSwatchMarginTop: Dp

    val colorPickerSwatchMarginBottom: Dp

    val colorPickerSwatchHeight: Dp

    val colorPickerSwatchWidth: Dp

    val timePickerText: Dp

    val datePickerHeaderHeight: Dp

    val datePickerHeaderWeekSpacing: Dp

    val datePickerWeekHeight: Dp

    val datePickerWeekCalendarSpacing: Dp

    val datePickerCalendarHeight: Dp

    val datePickerCalendarDaySelectedCircleRadius: Dp

    companion object {

        val Default = DefaultDynamicDimensions()

    }

}