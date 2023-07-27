package org.oneui.compose.picker

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import org.oneui.compose.picker.scroll.ItemScroll
import org.oneui.compose.picker.scroll.ItemScrollEditText
import org.oneui.compose.picker.scroll.RepeatingItemScroll
import org.oneui.compose.picker.scroll.RepeatingItemScrollState
import org.oneui.compose.picker.scroll.SimpleItemScrollState
import org.oneui.compose.theme.OneUITheme


/**
 * A oneui-style number picker to select an integer number.
 *
 * TODO: There is a lot of repeating in this composable. Should be redone. Same for StringPicker
 *
 * @param modifier The [Modifier] to apply to the container
 * @param values The possible values to show on the [RepeatingItemScroll]
 * @param startValue The value to be selected at begin
 * @param onValueChange The callback invoked when the value is changed
 * @param infiniteScroll Whether the scrolling list should repeat when the last element of [values] is reached, or stop
 * @param fillUpWithZeros Whether single digit values such as 1, 3, or 5 should be changed to 01, 03 etc. depending on the length of the biggest number in [values]
 * @param textStyle The [TextStyle] to apply to each text
 */
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    values: List<Int>,
    startValue: Int = values.first(),
    onValueChange: (Int) -> Unit,
    infiniteScroll: Boolean = true,
    fillUpWithZeros: Boolean = false,
    colors: NumberPickerColors = numberPickerColors(),
    textStyle: TextStyle = OneUITheme.types.numberPicker
) {
    val scope = rememberCoroutineScope()

    val lengthOfMax = values.max().toString().length
    fun String.addZerosUntil(length: Int): String {
        if (!fillUpWithZeros) return this
        var str = this
        while (str.length < length) {
            str = "0$str"
        }

        return str
    }

    fun String.stripZeros(): String {
        if (this.isEmpty()) return this
        var str = this
        while (str[0] == '0') {
            str = str.substring(startIndex = 1)
        }
        return str
    }

    if (infiniteScroll) {
        val state = remember {
            RepeatingItemScrollState(
                itemAmount = values.size,
                initialIndex = values.indexOf(startValue),
                visibleItemsCount = 3
            )
        }

        val style = textStyle.copy(
            color = with(colors) {
                if (state.isScrolling) textScrolling
                else text
            }
        )

        val item = @Composable { index: Int ->
            Text(
                text = values[index].toString().addZerosUntil(lengthOfMax),
                style = style,
                textAlign = TextAlign.Center
            )
        }

        val activeItem = @Composable { index: Int ->
            ItemScrollEditText(
                value = values[index].toString().addZerosUntil(lengthOfMax),
                onValueChange = {
                    val num = it.stripZeros().toInt()
                    onValueChange(num)
                    state.currentIndex = values.indexOf(num)
                    scope.launch {
                        state.scrollToItem(values.indexOf(num))
                    }
                },
                style = style,
                predicate = {
                    val num = it.toIntOrNull() ?: false
                    num in values
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }

        RepeatingItemScroll(
            modifier = modifier,
            state = state,
            onIndexChange = { index ->
                onValueChange(values[index])
                state.currentIndex = index
            },
            item = item,
            activeItem = activeItem
        )
    } else {
        val state = remember {
            SimpleItemScrollState(
                itemAmount = values.size,
                initialIndex = values.indexOf(startValue),
                visibleItemsCount = 3
            )
        }

        val style = textStyle.copy(
            color = with(colors) {
                if (state.isScrolling) textScrolling
                else text
            }
        )

        val item = @Composable { index: Int ->
            Text(
                text = values[index].toString().addZerosUntil(lengthOfMax),
                style = style,
                textAlign = TextAlign.Center
            )
        }

        val activeItem = @Composable { index: Int ->
            ItemScrollEditText(
                value = values[index].toString().addZerosUntil(lengthOfMax),
                onValueChange = {
                    val num = it.stripZeros().toInt()
                    onValueChange(num)
                    state.currentIndex = values.indexOf(num)
                    scope.launch {
                        state.scrollToItem(values.indexOf(num))
                    }
                },
                style = style,
                predicate = {
                    val num = it.toIntOrNull() ?: false
                    num in values
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }

        ItemScroll(
            modifier = modifier,
            state = state,
            onIndexChange = { index ->
                onValueChange(values[index])
                state.currentIndex = index
            },
            item = item,
            activeItem = activeItem
        )
    }
}

/**
 * Contains the colors used for a [NumberPicker]
 */
data class NumberPickerColors(

    val textScrolling: Color,

    val text: Color

)

/**
 * Constructs the default [NumberPickerColors]
 *
 * @param textScrolling The color of the text while scrolling
 * @param text The color of the text while not scrolling
 */
@Composable
fun numberPickerColors(
    textScrolling: Color = OneUITheme.colors.numberPickerScrollTextColor,
    text: Color = OneUITheme.colors.numberPickerTextColor
): NumberPickerColors = NumberPickerColors(
    textScrolling = textScrolling,
    text = text
)