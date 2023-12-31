package org.oneui.compose.picker.time

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.oneui.compose.R
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.DateUtil
import org.oneui.compose.util.OneUIPreview
import org.oneui.compose.util.TimeFormatUtil
import org.oneui.compose.util.animateScrollToNext
import org.oneui.compose.util.animateScrollToPrevious
import org.oneui.compose.util.difTo
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.util.Locale


/**
 * Composable for a oneui-date picker that allows the user to select a [LocalDate]
 *
 * @param modifier The [Modifier] to appply to the container
 * @param colors The [DatePickerColors] to apply
 * @param startDate The first possible month to show (day property is ignored)
 * @param endDate The last possible month to show (day property is ignored)
 * @param state The [DatePickerState] to receive updates about the users selection
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    colors: DatePickerColors = datePickerColors(),
    startDate: LocalDate = DatePickerDefaults.startDate,
    endDate: LocalDate = DatePickerDefaults.endDate,
    state: DatePickerState
) {
    val scope = rememberCoroutineScope()

    //Difs from start to end
    val totalDif = startDate.difTo(endDate)

    //Difs from start to current
    val currentDif = startDate.difTo(state.date)


    val pagerState = rememberPagerState(
        initialPage = currentDif.years * 12 + currentDif.months,
        pageCount = { totalDif.years * 12 + totalDif.months }
    )

    //Date that is displayed at top of DatePicker, and shows only month and year
    val currentDisplayedDate = startDate
        .plusMonths(pagerState.settledPage.toLong())

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DatePickerHeader(
            modifier = Modifier
                .height(OneUITheme.dimensions.datePickerHeaderHeight)
                .fillMaxWidth(),
            onPrevClick = {
                scope.launch {
                    pagerState.animateScrollToPrevious()
                }
            },
            onNextClick = {
                scope.launch {
                    pagerState.animateScrollToNext()
                }
            }
        ) { Text(text = TimeFormatUtil.formatMonthYear(currentDisplayedDate)) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    //I don't know why we need to half the padding, but it only works like this
                    horizontal = DatePickerDefaults.calendarPadding / 2
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(OneUITheme.dimensions.datePickerHeaderWeekSpacing)
                    .fillMaxWidth()
            )

            DatePickerWeek(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OneUITheme.dimensions.datePickerWeekHeight)
            )

            Spacer(
                modifier = Modifier
                    .height(OneUITheme.dimensions.datePickerWeekCalendarSpacing)
                    .fillMaxWidth()
            )

            DatePickerCalendar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OneUITheme.dimensions.datePickerCalendarHeight),
                startDate = startDate,
                endDate = endDate,
                onDayClick = {
                    val isInCurrentMonth = it.month == currentDisplayedDate.month
                    if (isInCurrentMonth) {
                        state.date = it
                    } else {
                        val isNextMonth = it.month == currentDisplayedDate.month.plus(1L)
                        scope.launch {
                            if (isNextMonth) pagerState.animateScrollToNext()
                            else pagerState.animateScrollToPrevious()
                        }
                    }
                },
                pagerState = pagerState,
                currentDate = state.date,
                colors = colors
            )
        }
    }
}

@Composable
internal fun DatePickerHeader(
    modifier: Modifier = Modifier,
    onPrevClick: () -> Unit,
    prevEnabled: Boolean = true,
    nextEnabled: Boolean = true,
    onNextClick: () -> Unit,
    colors: DatePickerColors = datePickerColors(),
    label: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = DatePickerDefaults.calendarPadding
                )
        ) {
            DatePickerIconButton(
                painter = painterResource(R.drawable.sesl_date_picker_prev),
                onClick = onPrevClick,
                colors = colors,
                enabled = prevEnabled
            )
        }

        ProvideTextStyle(OneUITheme.types.datePickerHeader) {
            Box(
                modifier = Modifier
                    .padding(DatePickerDefaults.headerTextPadding)
            ) {
                label()
            }
        }

        Box(
            modifier = Modifier
                .padding(
                    end = DatePickerDefaults.calendarPadding
                )
        ) {
            DatePickerIconButton(
                painter = painterResource(R.drawable.sesl_date_picker_next),
                onClick = onNextClick,
                colors = colors,
                enabled = nextEnabled
            )
        }
    }
}

@Composable
internal fun DatePickerWeek(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val days = DateUtil.getDayOfWeekInOrder(Locale.getDefault())

        days.forEach { day ->
            Text(
                modifier = Modifier
                    .weight(1F),
                text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                style = with(OneUITheme.types) {
                    when (day) {
                        DayOfWeek.SATURDAY -> datePickerSaturday
                        DayOfWeek.SUNDAY -> datePickerSunday
                        else -> datePickerNormalDay
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DatePickerCalendar(
    modifier: Modifier = Modifier,
    startDate: LocalDate,
    endDate: LocalDate,
    currentDate: LocalDate = LocalDate.now(),
    onDayClick: (LocalDate) -> Unit,
    pagerState: PagerState,
    colors: DatePickerColors = datePickerColors()
) {

    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { index ->
        val date = startDate.plusMonths(index.toLong())

        val year = date[ChronoField.YEAR]
        val month = date[ChronoField.MONTH_OF_YEAR]
        val selectedDay =
            if (
                currentDate[ChronoField.YEAR] == year &&
                currentDate[ChronoField.MONTH_OF_YEAR] == month
            )
                currentDate[ChronoField.DAY_OF_MONTH] else null

        SimpleMonthCalendar(
            year = year,
            monthOfYear = month,
            selectedDayOfMonth = selectedDay ?: -1,
            onDayClick = onDayClick,
            colors = colors,
            isDayInRange = {
                (it.isEqual(startDate) || it.isAfter(startDate) && (it.isEqual(endDate) || it.isBefore(
                    endDate
                )))
            }
        )


    }
}

// Always display a total of 6 weeks per month.
private const val DISPLAY_WEEKS_IN_MONTH = 6

@Composable
internal fun SimpleMonthCalendar(
    modifier: Modifier = Modifier,
    year: Int,
    monthOfYear: Int,
    selectedDayOfMonth: Int,
    onDayClick: (LocalDate) -> Unit,
    isDayInRange: (LocalDate) -> Boolean,
    colors: DatePickerColors
) {
    val weeks = remember {
        DateUtil.getDatesInMonthDisplay(year, monthOfYear, DISPLAY_WEEKS_IN_MONTH)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        weeks.forEach { dates ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OneUITheme.dimensions.datePickerWeekHeight)
            ) {
                for (date in dates) {
                    val isSelected =
                        date[ChronoField.MONTH_OF_YEAR] == monthOfYear && date[ChronoField.DAY_OF_MONTH] == selectedDayOfMonth
                    val radius = OneUITheme.dimensions.datePickerCalendarDaySelectedCircleRadius
                    val selMod = if (isSelected) Modifier
                        .drawBehind {
                            val center = Offset(
                                x = size.width / 2,
                                y = size.height / 2
                            )
                            drawCircle(
                                center = center,
                                radius = radius.toPx(),
                                color = colors.selectedDayCircleColor
                            )
                        } else Modifier
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxHeight()
                            .then(selMod)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onDayClick(date) }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        val dow = date.dayOfWeek
                        val style = with(OneUITheme.types) {
                            when (dow) {
                                DayOfWeek.SATURDAY -> datePickerNumberSaturday
                                DayOfWeek.SUNDAY -> datePickerNumberSunday
                                else -> datePickerNumberNormalDay
                            }
                        }

                        val alpha = if (date[ChronoField.MONTH_OF_YEAR] == monthOfYear) 1F
                        else if (isSystemInDarkTheme()) DatePickerDefaults.monthNumberDisabledAlphaDark
                        else DatePickerDefaults.monthNumberDisabledAlphaLight

                        val color = if (isSelected) colors.selectedDayNumberText
                        else style.color.copy(alpha = alpha)

                        if (isDayInRange(date)) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = date[ChronoField.DAY_OF_MONTH].toString(),
                                //Apply alpha when date is not part of month, or special color when selected
                                style = style.copy(
                                    color = color
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DatePickerIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit,
    colors: DatePickerColors,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .size(OneUITheme.dimensions.datePickerHeaderHeight)
            .clip(CircleShape)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = colors.ripple,
                    radius = 24.dp
                ),
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            alpha = if (enabled) 1F else DatePickerDefaults.prevNextButtonDisabledAlpha,
            painter = painter,
            contentDescription = null
        )
    }
}

/**
 * State to receive updates about the user selection of a [DatePicker]
 *
 * @property initial
 */
data class DatePickerState(
    val initial: LocalDate
) {

    var date by mutableStateOf(initial)

}

/**
 * Remembers a [DatePickerState]
 *
 * @param initial The initially selected value to show on the [DatePicker]
 */
@Composable
fun rememberDatePickerState(
    initial: LocalDate = LocalDate.now()
) = remember {
    DatePickerState(
        initial = initial
    )
}

/**
 * Contains default values for a [DatePicker]
 */
object DatePickerDefaults {

    val calendarPadding = 15.dp

    val headerTextPadding = PaddingValues(
        horizontal = 8.dp
    )

    const val monthNumberDisabledAlphaLight = 120F / 255F
    const val monthNumberDisabledAlphaDark = 120F / 255F

    const val prevNextButtonDisabledAlpha = 0.4F

    val startDate = LocalDate.of(1900, Month.JANUARY, 1)
    val endDate = LocalDate.of(2100, Month.DECEMBER, 31)

}

/**
 * Contains the colors needed for a [DatePicker]
 */
data class DatePickerColors(

    val ripple: Color,

    val selectedDayCircleColor: Color,

    val selectedDayNumberText: Color

)

/**
 * Constructs the default [DatePickerColors]
 *
 * @param ripple The ripple color used for the onclick animation of several components
 * @param selectedDayCircleColor The color of the circle which highlights the selected date
 * @param selectedDayNumberText The text color of the selected day
 */
@Composable
fun datePickerColors(
    ripple: Color = OneUITheme.colors.seslRippleColor,
    selectedDayCircleColor: Color = OneUITheme.colors.seslPrimaryDarkColor,
    selectedDayNumberText: Color = OneUITheme.colors.seslDatePickerSelectedDayNumberTextColor
): DatePickerColors = DatePickerColors(
    ripple = ripple,
    selectedDayCircleColor = selectedDayCircleColor,
    selectedDayNumberText = selectedDayNumberText
)

@Preview
@Composable
fun DatePickerPreview() = OneUIPreview(
    title = "DatePicker",
    padding = PaddingValues(
        vertical = 12.dp
    )
) {
    DatePicker(
        state = remember {
            DatePickerState(
                initial = LocalDate.now()
            )
        }
    )
}