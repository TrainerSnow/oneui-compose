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
 *
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

    override val colorPickerSpectrumHeight = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_spectrum_height, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSpectrumWidth = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_spectrum_width, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSpectrumCursorRadius = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_spectrum_cursor_radius, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSelectedMarginTop = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_selected_margin_top, tv, true)
            tv.float
        }
        .dp

    override val colorPickerHexSectionSpacing = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_selected_hex_section_margin, tv, true)
            tv.float
        }
        .dp

    override val colorPickerRgbSectionsSpacing = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_selected_rgb_section_margin, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSeekbarMarginBottom = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_seekbar_margin_bottom, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSeekbarMarginHorizontal = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_seekbar_margin_horizontal, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSeekbarLabelSpacing = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_seekbar_label_spacing, tv, true)
            tv.float
        }
        .dp

    override val colorPickerTabLayoutMarginHorizontal = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_tab_layout_margin_horizontal, tv, true)
            tv.float
        }
        .dp

    override val colorPickerContentWidth = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_content_width, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSwatchMarginTop = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_swatches_margin_top, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSwatchMarginBottom = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_swatches_margin_bottom, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSwatchHeight = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_swatches_height, tv, true)
            tv.float
        }
        .dp

    override val colorPickerSwatchWidth = context
        .resources
        .let {
            val tv = TypedValue()
            it.getValue(R.dimen.sesl_picker_color_swatches_width, tv, true)
            tv.float
        }
        .dp
}