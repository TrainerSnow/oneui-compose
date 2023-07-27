package org.oneui.compose.util

import java.time.DayOfWeek
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

}

fun getDayPositionInWeek(dayOfWeek: DayOfWeek, locale: Locale): Int {
    val firstDayOfWeek = WeekFields.of(locale).firstDayOfWeek
    val daysToAdd = (dayOfWeek.value - firstDayOfWeek.value + 7) % 7
    return daysToAdd + 1
}