package org.oneui.compose.util

import java.time.LocalTime


/**
 * Contains utility functions for dealing with and processing time and date
 */
object TimeUtil {

    /**
     * Creates a [LocalTime] object from [hour] and [minute] fields.
     *
     * @param amPm AM/PM state, or null, if military time is used
     */
    fun timeFor(hour: Int, minute: Int, amPm: AmPm?): LocalTime = if (amPm == null) {
        LocalTime.of(
            hour,
            minute
        )
    } else {
        LocalTime.of(
            if (amPm == AmPm.AM) hour else hour + 12,
            minute
        )
    }

}

/**
 * @return The AM/PM state of a given time
 */
fun LocalTime.amPm(): AmPm = if (hour in TimeFormat.AmPm.hourRange) AmPm.AM else AmPm.PM

enum class AmPm {

    AM,

    PM

    ;

    companion object {

        fun fromLocalizedString(str: String): AmPm = when (str) {
            TimeFormatUtil.getAmPmStrings().first -> AM
            TimeFormatUtil.getAmPmStrings().second -> PM
            else -> {
                throw IllegalArgumentException("The localized string '$str' can't be mapped to an AM/PM state")
            }
        }

    }
}

enum class TimeFormat(
    val hourRange: List<Int>
) {
    Military(
        (0..23).toList()
    ),

    AmPm(
        (1..12).toList()
    )
}