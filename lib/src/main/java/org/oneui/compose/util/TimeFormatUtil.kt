package org.oneui.compose.util

import android.text.format.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Contains utility fields/functions to aid in formatting time or date
 */
object TimeFormatUtil {

    private val hourPatternPlaceholders = listOf('k', 'K', 'h', 'H')
    private const val defaultHourMinSeparator = ":"

    /**
     * Find the separator for hour/minute in a time format pattern.
     * Example:
     *
     * @param pattern The formatting pattern
     * @return The separator
     */
    fun findHourMinuteSeparator(pattern: String): String {
        var foundHour = false

        pattern.forEach {
            if (it in hourPatternPlaceholders) {
                foundHour = true
            } else if (foundHour) {
                return it.toString()
            }
        }

        return defaultHourMinSeparator
    }

    /**
     * @return A [Pair] with the am/pm text descriptor
     */
    fun getAmPmStrings(): Pair<String, String> =
        with(java.text.DateFormatSymbols().amPmStrings) {
            Pair(
                first = component1(),
                second = component2()
            )
        }

    /**
     * Formats the [date] to show full month and then full year
     *
     * @return The formatted date as a string
     */
    fun formatMonthYear(date: LocalDate): String = DateTimeFormatter.ofPattern(
        DateFormat.getBestDateTimePattern(
            Locale.getDefault(),
            "MMMM yyyy"
        )
    ).format(date)

}