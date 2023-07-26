package org.oneui.compose.picker

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import org.oneui.compose.picker.scroll.ItemScroll
import org.oneui.compose.picker.scroll.rememberItemScrollState
import org.oneui.compose.theme.OneUITheme


/**
 * A oneui-style number picker to select an integer number.
 *
 * @param modifier The [Modifier] to apply to the container
 * @param values The possible values to show on the [ItemScroll]
 * @param onValueChange The callback invoked when the value is changed
 * @param textStyle The [TextStyle] to apply to each text
 * @param state  The [ItemScrollState] to control the component
 */
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    values: List<Int>,
    startValue: Int = values.first(),
    onValueChange: (Int) -> Unit,
    colors: NumberPickerColors = numberPickerColors(),
    textStyle: TextStyle = OneUITheme.types.numberPicker
) {
    val scope = rememberCoroutineScope()
    val state = rememberItemScrollState(
        itemAmount = values.size,
        initialIndex = values.indexOf(startValue)
    )

    val style = textStyle.copy(
        color = with(colors) {
            if (state.isScrolling) textScrolling
            else text
        }
    )

    ItemScroll(
        modifier = modifier,
        state = state,
        onIndexChange = { index ->
            onValueChange(values[index])
            state.currentIndex = index
        },
        item = { index ->
            Text(
                text = values[index].toString(),
                style = style,
                textAlign = TextAlign.Center
            )
        },
        activeItem = { index ->
            ItemScrollEditText(
                value = values[index].toString(),
                onValueChange = {
                    val num = it.toInt()
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
    )
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