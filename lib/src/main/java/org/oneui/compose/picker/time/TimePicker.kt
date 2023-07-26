package org.oneui.compose.picker.time

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import org.oneui.compose.picker.NumberPicker
import org.oneui.compose.picker.StringPicker
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.TimeFormat
import org.oneui.compose.util.TimeFormatUtil
import org.oneui.compose.util.steppedRangeTo
import java.time.LocalTime
import java.util.Locale

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    config: TimePickerConfig = timePickerConfig()
) {
    assert(60 % config.minuteStep == 0)

    var time by remember {
        mutableStateOf(LocalTime.now())
    }

    val density = LocalDensity.current

    val textSize = with(density) { OneUITheme.dimensions.timePickerText.toSp() }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .weight(TimePickerDefaults.spacingStartWeight)
        )

        Row(
            modifier = Modifier
                .weight(TimePickerDefaults.hourMinuteWeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberPicker(
                modifier = Modifier
                    .weight(TimePickerDefaults.hourWeight),
                textStyle = OneUITheme.types.numberPicker.copy(
                    fontSize = textSize
                ),
                values = config.timeFormat.hourRange,
                onValueChange = { hour ->
                    /*time = TimeUtil.timeFor(
                        hour = hour,
                        minute = time.minute,
                        amPm = if (config.timeFormat == TimeFormat.Military) null else time.amPm()
                    )*/
                }
            )

            Text(
                modifier = Modifier
                    .weight(TimePickerDefaults.dividerWeight),
                text = config.hourMinSeparator,
                style = OneUITheme.types.numberPicker.copy(
                    fontSize = textSize
                )
            )

            NumberPicker(
                modifier = Modifier
                    .weight(TimePickerDefaults.minuteWeight),
                textStyle = OneUITheme.types.numberPicker.copy(
                    fontSize = textSize
                ),
                values = (0).steppedRangeTo(
                    other = 59,
                    step = config.minuteStep
                ),
                onValueChange = { minute ->
                    /*time = TimeUtil.timeFor(
                        hour = time.hour,
                        minute = minute,
                        amPm = if (config.timeFormat == TimeFormat.Military) null else time.amPm()
                    )*/
                }
            )
        }

        if (config.timeFormat == TimeFormat.AmPm) {
            StringPicker(
                modifier = Modifier
                    .weight(TimePickerDefaults.amPmWeight),
                textStyle = OneUITheme.types.numberPicker.copy(
                    fontSize = textSize
                ),
                values = TimeFormatUtil.getAmPmStrings().toList(),
                startValue = TimeFormatUtil.getAmPmStrings().first,
                onValueChange = { ampm ->
                    /*time = TimeUtil.timeFor(
                        hour = time.hour,
                        minute = time.minute,
                        amPm = AmPm.fromLocalizedString(ampm)
                    )*/
                }
            )
        }

        Spacer(
            modifier = Modifier
                .weight(TimePickerDefaults.spacingEndWeight)
        )
    }
}


object TimePickerDefaults {

    const val spacingStartWeight = 270F
    const val spacingEndWeight = 180F

    const val hourMinuteWeight = 2700F
    const val hourWeight = 1260F
    const val dividerWeight = 180F
    const val minuteWeight = 1260F

    const val amPmWeight = 1260F
}


data class TimePickerConfig internal constructor(

    val timeFormat: TimeFormat,

    val minuteStep: Int,

    val hourMinSeparator: String

)

/**
 * Constructs the default [TimePickerConfig]
 *
 * @param timeFormat The [TimeFormat]: 24-hour format (12.00, 13.00, etc...) or AM/PM
 * @param minuteStep The step at which the minute field should be incremented
 * @param hourMinSeparator The separator to separate the hour and minute selection
 */
@Composable
fun timePickerConfig(
    timeFormat: TimeFormat = if (DateFormat.is24HourFormat(LocalContext.current)) TimeFormat.Military else TimeFormat.AmPm,
    minuteStep: Int = 1,
    hourMinSeparator: String = TimeFormatUtil.findHourMinuteSeparator(
        pattern = DateFormat.getBestDateTimePattern(
            Locale.getDefault(),
            if (timeFormat == TimeFormat.Military) "Hm" else "hm"
        )
    )
): TimePickerConfig = TimePickerConfig(
    timeFormat = timeFormat,
    minuteStep = minuteStep,
    hourMinSeparator = hourMinSeparator
)