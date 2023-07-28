package org.oneui.compose.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

object DateUtil {

    /**
     * Returns the [DayOfWeek] in the order defined by the passed [Locale]
     */
    fun getDayOfWeekInOrder(locale: Locale): List<DayOfWeek> =
        WeekFields
            .of(locale)
            .let {
                val list = emptyList<DayOfWeek>().toMutableList()
                var day = it.firstDayOfWeek

                while (list.size < 7) {
                    list.add(day)
                    day = day.plus(1)
                }

                return list
            }

    /**
     * Calculates all the dates in a month, structured by weeks.
     * This also returns the [LocalDate]s that arent in the specified month, but inside the week of the first/last day of the month,
     *     and additionally the dates to fill up until [weeks] is reached
     *
     * @param year The year
     * @param monthOfYear The month [1;12]
     */
    fun getDatesInMonthDisplay(year: Int, monthOfYear: Int, weeksNum: Int): List<List<LocalDate>> {
        //Values of actual month start (start = 1, end = 28/29/30/31)
        val monthStartDay = LocalDate.of(year, monthOfYear, 1)

        val weeks = listOf<List<LocalDate>>().toMutableList()

        val monthStartDayOfWeek = monthStartDay
            .dayOfWeek
            .value - 1

        val firstWeek = mutableListOf<LocalDate>()
        (0..monthStartDayOfWeek).forEach { index ->
            firstWeek.add(monthStartDay.minusDays(index.toLong()))
        }
        firstWeek.reverse()
        var counter = 1
        while (firstWeek.size < 7) {
            firstWeek.add(firstWeek.size, monthStartDay.plusDays(counter.toLong()))
            counter += 1
        }
        weeks.add(firstWeek)

        counter = 1 //We in week 2 now
        while (weeks.size < weeksNum) {
            val nextWeek = mutableListOf<LocalDate>()
            (0 until 7).forEach { index ->
                nextWeek.add(
                    monthStartDay.plusDays(counter * 7L + index - monthStartDayOfWeek)
                )
            }
            weeks.add(nextWeek)
            counter += 1
        }

        return weeks
    }

}

/**
 * Calculates the difference between two dates
 *
 * @param other The date to calculate the difference to, later in time
 * @return A [DateDif]
 */
fun LocalDate.difTo(other: LocalDate): DateDif {
    val p = this.until(other) //Plus 1 day because param is exclusive

    return DateDif(
        years = p.years,
        months = p.months,
        days = p.days
    )
}

data class DateDif(
    val years: Int,
    val months: Int,
    val days: Int
)