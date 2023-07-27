package org.oneui.compose.util

import android.annotation.SuppressLint
import androidx.annotation.IntRange
import java.time.LocalTime

internal enum class AmPm {

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


/**
 * Makes dealing with different time formats easier. Used around the the library
 *
 */
internal sealed interface Time {

    /**
     * Time in 24-hour format, 00:00 to 23:59. Basically a wrapper for [LocalTime]
     */
    data class Military(
        @IntRange(from = 0, to = 23) val hour: Int = 0,
        @IntRange(from = 0, to = 59) val minute: Int = 0,
        @IntRange(from = 0, to = 59) val second: Int = 0,
        @IntRange(from = 0, to = 999_999_999) val nano: Int = 0
    ) : Time {

        override val asLocalTime: LocalTime
            get() = LocalTime.of(
                hour,
                minute,
                second,
                nano
            )

    }

    /**
     * Time in half-day format, from 12:00 AM to 11:59 PM
     */
    data class HalfDay(
        @IntRange(from = 1, to = 12) val hour: Int = 1,
        @IntRange(from = 0, to = 59) val minute: Int = 0,
        @IntRange(from = 0, to = 59) val second: Int = 0,
        @IntRange(from = 0, to = 999_999_999) val nano: Int = 0,
        val amPm: AmPm = AmPm.AM
    ) : Time

    /**
     * Converts the [Time] to [Military]
     */
    val asMilitary: Military
        get() = if (this is Military) this
        else with(this as HalfDay) {
            Military(
                minute = minute,
                second = second,
                nano = nano,
                hour = if (hour == 12 && amPm == AmPm.AM) 0
                else if (
                    hour in (1..11) && amPm == AmPm.AM ||
                    hour == 12 && amPm == AmPm.PM
                ) hour
                else if (
                    hour in (1..11) && amPm == AmPm.PM
                ) hour + 12
                else throw Error("This should not occur!")
            )
        }

    /**
     * Converts the [Time] to [HalfDay]
     */
    val asHalfDay: HalfDay
        @SuppressLint("Range")
        get() = if (this is HalfDay) this
        else with(this as Military) {
            HalfDay(
                minute = minute,
                second = second,
                nano = nano,
                hour = when (hour) {
                    0 -> 12
                    in (1..11) -> hour
                    12 -> hour
                    in (13..23) -> hour - 12
                    else -> throw Error("This should not occur!")
                },
                amPm = when (hour) {
                    0 -> AmPm.AM
                    in (1..11) -> AmPm.AM
                    12 -> AmPm.PM
                    in (13..23) -> AmPm.PM
                    else -> throw Error("This should not occur!")
                }
            )
        }

    /**
     * Converts the [Time] to a [LocalTime]
     */
    val asLocalTime: LocalTime
        get() = asMilitary.asLocalTime

    val hourRange: kotlin.ranges.IntRange
        get() = if (this is Military) (0..23)
        else (1..12)

    companion object {

        /**
         * Creates a [Time] object based on the provided parameters.
         *
         * @param amPm Decides whether to return an [Military] when null or a [HalfDay]
         */
        fun of(
            hour: Int,
            minute: Int = 0,
            second: Int = 0,
            nano: Int = 0,
            amPm: AmPm? = null
        ): Time {
            val isMilitary = amPm == null
            assert(
                minute in (0..59) &&
                        second in (0..59) &&
                        nano in (0..999_999_999) &&
                        hour in if (isMilitary) (0..23) else (1..12)
            ) { "The values are out of bounds." }

            return if (isMilitary) Military(
                hour = hour,
                minute = minute,
                second = second,
                nano = nano
            ) else HalfDay(
                hour = hour,
                minute = minute,
                second = second,
                nano = nano,
                amPm = amPm!!
            )
        }

        /**
         * Creates a [Time] [Military] from a [LocalTime]
         */
        fun of(time: LocalTime): Time = Military(
            hour = time.hour,
            minute = time.minute,
            second = time.second,
            nano = time.nano
        )

    }

}