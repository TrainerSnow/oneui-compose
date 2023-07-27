package org.oneui.compose.picker.time

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.oneui.compose.picker.NumberPicker
import org.oneui.compose.picker.StringPicker
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.AmPm
import org.oneui.compose.util.Time
import org.oneui.compose.util.TimeFormatUtil
import org.oneui.compose.util.steppedRangeTo
import java.time.LocalTime
import java.util.Locale

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    config: TimePickerConfig = timePickerConfig(),
    state: TimePickerState
) {
    assert(60 % config.minuteStep == 0)

    val textStyle = OneUITheme.types.timePicker

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
                textStyle = textStyle,
                values = config.hourRange.toList(),
                startValue = Time.of(state.time)
                    .run { if (config.militaryFormat) asMilitary.hour else asHalfDay.hour },
                fillUpWithZeros = config.militaryFormat,
                onValueChange = { hour ->
                    val recent = Time.of(state.time)
                    state.time = if (config.militaryFormat) Time.Military(
                        hour = hour,
                        minute = recent.asMilitary.minute
                    ).asLocalTime else Time.HalfDay(
                        hour = hour,
                        minute = recent.asHalfDay.minute,
                        amPm = recent.asHalfDay.amPm
                    ).asLocalTime
                }
            )

            Text(
                modifier = Modifier
                    .weight(TimePickerDefaults.dividerWeight),
                text = config.hourMinSeparator,
                style = textStyle
            )

            NumberPicker(
                modifier = Modifier
                    .weight(TimePickerDefaults.minuteWeight),
                textStyle = textStyle,
                values = (0).steppedRangeTo(
                    other = 59,
                    step = config.minuteStep
                ),
                startValue = state.time.minute,
                fillUpWithZeros = config.militaryFormat,
                onValueChange = { minute ->
                    val recent = Time.of(state.time)
                    state.time = if (config.militaryFormat) Time.Military(
                        hour = recent.asMilitary.hour,
                        minute = minute
                    ).asLocalTime else Time.HalfDay(
                        hour = recent.asHalfDay.hour,
                        minute = minute,
                        amPm = recent.asHalfDay.amPm
                    ).asLocalTime
                }
            )
        }

        if (!config.militaryFormat) {
            StringPicker(
                modifier = Modifier
                    .weight(TimePickerDefaults.amPmWeight),
                textStyle = textStyle,
                values = TimeFormatUtil.getAmPmStrings().toList(),
                startValue = TimeFormatUtil.getAmPmStrings().first,
                onValueChange = { ampm ->
                    val recent = Time.of(state.time)

                    state.time = Time.HalfDay(
                        hour = recent.asHalfDay.hour,
                        minute = recent.asHalfDay.minute,
                        amPm = AmPm.fromLocalizedString(ampm)
                    ).asLocalTime
                },
                infiniteScroll = false
            )
        }

        Spacer(
            modifier = Modifier
                .weight(TimePickerDefaults.spacingEndWeight)
        )
    }
}

data class TimePickerState(
    val initial: LocalTime
) {

    var time by mutableStateOf(initial)

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

    val militaryFormat: Boolean,

    internal val hourRange: IntRange,

    val minuteStep: Int,

    val hourMinSeparator: String

)

/**
 * Constructs the default [TimePickerConfig]
 *
 * @param militaryTime Whether 'Military Time' (00:00) or 'Half-Day' (12:00 AM) should be used)
 * @param minuteStep The step at which the minute field should be incremented
 * @param hourMinSeparator The separator to separate the hour and minute selection
 */
@Composable
fun timePickerConfig(
    militaryTime: Boolean = DateFormat.is24HourFormat(LocalContext.current),
    minuteStep: Int = 1,
    hourMinSeparator: String = TimeFormatUtil.findHourMinuteSeparator(
        pattern = DateFormat.getBestDateTimePattern(
            Locale.getDefault(),
            if (militaryTime) "Hm" else "hm"
        )
    )
): TimePickerConfig = TimePickerConfig(
    militaryFormat = militaryTime,
    hourRange = if (militaryTime) (0..23) else (1..12),
    minuteStep = minuteStep,
    hourMinSeparator = hourMinSeparator
)