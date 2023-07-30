package org.oneui.compose.widgets.buttons.radio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Overload for [VerticalRadioGroup] that takes the values and labels and constructs the buttons itself
 *
 * @param T The value associated with the group and its' buttons
 * @param modifier The [Modifier] to be applied to the container
 * @param onClick The callback invoked when a [RadioButton] is clicked with the clicked value and its' index
 * @param groupValue The [T] value that is currently selected
 * @param values The [T] values to construct [RadioButton]s for
 * @param labels The [String] labels to label each of the [values] button with
 * @param spacing The spacing to be applied between each [RadioButton]
 */
@Composable
fun <T> VerticalRadioGroup(
    modifier: Modifier = Modifier,
    onClick: ((value: T, index: Int) -> Unit)? = null,
    groupValue: T,
    values: List<T>,
    labels: List<String>,
    spacing: Dp = VerticalRadioGroupDefaults.spacing,
) {
    assert(values.size == labels.size) {
        "The amount of labels is unequal to the amount of values."
    }

    VerticalRadioGroup(
        modifier = modifier,
        spacing = spacing
    ) {
        values.forEachIndexed { index, value ->
            val label = labels[index]

            RadioButton(
                value = value,
                groupValue = groupValue,
                onClick = {
                    onClick?.let {
                        it(value, index)
                    }
                }
            ) {
                Text(
                    text = label
                )
            }
        }
    }
}

/**
 * Arranges a group of [RadioButton]s vertically
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param spacing The spacing to be applied between each [RadioButton]
 * @param content The [RadioButton]s
 */
@Composable
fun VerticalRadioGroup(
    modifier: Modifier = Modifier,
    spacing: Dp = VerticalRadioGroupDefaults.spacing,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement
            .spacedBy(spacing),
        modifier = modifier
    ) {
        content(this)
    }
}

/**
 * Contains default values for a [VerticalRadioGroup]
 *
 * @constructor Create empty Vertical radio group defaults
 */
object VerticalRadioGroupDefaults {

    val spacing = 8.dp

}