package org.oneui.compose.util

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

}