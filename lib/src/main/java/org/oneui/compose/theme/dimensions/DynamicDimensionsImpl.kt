package org.oneui.compose.theme.dimensions

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.oneui.compose.R

/**
 * Implementation of [IDynamicDimensions] using the raw height and width values of the window.
 * Gets the values from the res folders.
 */
class DynamicDimensionsImpl(
    val context: Context
) : IDynamicDimensions {

    override val dialogMinWidthMajor = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_dialog_min_width_major, tv, true)
            tv.float
        }

    override val dialogMinWidthMinor = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_dialog_min_width_minor, tv, true)
            tv.float
        }


    override val dialogMinWidth =
        if (
            context
                .resources
                .configuration
                .orientation == Configuration.ORIENTATION_LANDSCAPE
        ) dialogMinWidthMajor
        else dialogMinWidthMinor

    override val switchBarMarginHorizontal = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_switchbar_margin_horizontal, tv, true)
            tv.float
        }
        .dp

    override val switchBarLabelTextSize = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_switchbar_label_text_size, tv, true)
            tv.float
        }
        .sp

    override val preferenceSwitchScreenDividerWidth = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_preference_switch_screen_divider_width, tv, true)
            tv.float
        }

    override val timePickerText = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_time_text, tv, true)
            tv.float
        }
        .dp

    override val datePickerHeaderHeight = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_date_header_height, tv, true)
            tv.float
        }
        .dp

    override val datePickerHeaderWeekSpacing = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_date_header_week_spacing, tv, true)
            tv.float
        }
        .dp

    override val datePickerWeekHeight = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_date_week_height, tv, true)
            tv.float
        }
        .dp

    override val datePickerWeekCalendarSpacing = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_date_week_calendar_spacing, tv, true)
            tv.float
        }
        .dp

    override val datePickerCalendarHeight = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_date_calendar_height, tv, true)
            tv.float
        }
        .dp

    override val datePickerCalendarDaySelectedCircleRadius = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_date_calendar_day_selected_circle_radius, tv, true)
            tv.float
        }
        .dp
}