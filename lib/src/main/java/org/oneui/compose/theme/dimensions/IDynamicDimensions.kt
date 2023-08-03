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