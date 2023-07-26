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
 * A oneui-style number picker to select a string from a given selection of strings. Only recommended for a fixed and static amount of strings.
 *
 * @param modifier The [Modifier] to apply to the container
 * @param values The possible values to show on the [RepeatingItemScroll]
 * @param startValue The value to be selected at begin
 * @param onValueChange The callback invoked when the value is changed
 * @param infiniteScroll Whether the scrolling list should repeat when the last element of [values] is reached, or stop
 * @param textStyle The [TextStyle] to apply to each text
 */

@Composable
fun StringPicker(
    modifier: Modifier = Modifier,
    values: List<String>,
    startValue: String = values.first(),
    onValueChange: (String) -> Unit,
    infiniteScroll: Boolean = true,
    colors: StringPickerColors = stringPickerColors(),
    textStyle: TextStyle = OneUITheme.types.numberPicker
) {
    val scope = rememberCoroutineScope()

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
                text = values[index],
                style = style,
                textAlign = TextAlign.Center
            )
        }

        val activeItem = @Composable { index: Int ->
            ItemScrollEditText(
                value = values[index],
                onValueChange = {
                    onValueChange(it)
                    state.currentIndex = values.indexOf(it)
                    scope.launch {
                        state.scrollToItem(values.indexOf(it))
                    }
                },
                style = style,
                predicate = {
                    it in values
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
                text = values[index],
                style = style,
                textAlign = TextAlign.Center
            )
        }

        val activeItem = @Composable { index: Int ->
            ItemScrollEditText(
                value = values[index],
                onValueChange = {
                    onValueChange(it)
                    state.currentIndex = values.indexOf(it)
                    scope.launch {
                        state.scrollToItem(values.indexOf(it))
                    }
                },
                style = style,
                predicate = {
                    it in values
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

data class StringPickerColors(

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
fun stringPickerColors(
    textScrolling: Color = OneUITheme.colors.numberPickerScrollTextColor,
    text: Color = OneUITheme.colors.numberPickerTextColor
): StringPickerColors = StringPickerColors(
    textScrolling = textScrolling,
    text = text
)
