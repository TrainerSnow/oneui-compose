package org.oneui.compose.theme.dimensions

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Implementation of [IDynamicDimensions] that contains the default values.
 */
class DefaultDynamicDimensions : IDynamicDimensions {

    override val dialogMinWidthMajor = 1F

    override val dialogMinWidthMinor = 1F

    override val dialogMinWidth = 1F

    override val switchBarMarginHorizontal = 12.dp

    override val switchBarLabelTextSize = 21.sp

    override val preferenceSwitchScreenDividerWidth = 1F

    override val timePickerText = 21.dp

    override val datePickerHeaderHeight = 36.dp

    override val datePickerHeaderWeekSpacing = 10.dp

    override val datePickerWeekHeight = 32.dp

    override val datePickerWeekCalendarSpacing = 7.dp

    override val datePickerCalendarHeight = 192.dp

    override val datePickerCalendarDaySelectedCircleRadius = 14.dp
}