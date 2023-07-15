package org.oneui.compose.preference

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.base.Icon
import org.oneui.compose.dialog.AlertDialog
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.widgets.buttons.radio.ListRadioButton
import org.oneui.compose.widgets.buttons.radio.VerticalRadioGroup


/**
 * Composable for a oneui-style preference that allows the user to store a single decision between multiple possibilities.
 *
 * @param T The type the radio-components are associated with
 * @param modifier THe [Modifier] applied to the container
 * @param icon The [Icon] to be applied to the preference
 * @param title The title of the preference
 * @param interactionSource The [MutableInteractionSource]
 * @param value The currently selected value
 * @param values All possible values that can be selected
 * @param onValueChange The callback invoked when the [value] is changed
 * @param nameFor A lambda to convert a [T] variable to it's visual representation
 */
@Composable
fun <T> SingleSelectPreference(
    modifier: Modifier = Modifier,
    icon: Icon? = null,
    title: String,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    value: T,
    values: List<T>,
    onValueChange: (T) -> Unit,
    nameFor: ((T) -> String) = { it.toString() }
) {
    assert(value in values) { "The provided value must be present in the provided values" }
    var showDialog by remember {
        mutableStateOf(false)
    }

    BasePreference(
        modifier,
        icon,
        title = {
            Text(
                text = title
            )
        },
        summary = {
            Text(
                text = nameFor(value),
                style = OneUITheme.types.preferenceSummary.copy(
                    color = OneUITheme.colors.seslPrimaryColor
                )
            )
        },
        onClick = { showDialog = true },
        interactionSource = interactionSource
    )

    if (showDialog) {
        SingleSelectPreferenceDialog(
            title = title,
            value = value,
            values = values,
            nameFor = nameFor,
            onDismissRequest = { showDialog = false },
            onValueChange = onValueChange
        )
    }

}

@Composable
internal fun <T> SingleSelectPreferenceDialog(
    modifier: Modifier = Modifier,
    title: String,
    value: T,
    values: List<T>,
    nameFor: (T) -> String,
    onDismissRequest: () -> Unit,
    onValueChange: (T) -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        negativeButtonLabel = stringResource(R.string.sesl_dialog_button_negative),
        onNegativeButtonClick = onDismissRequest,
        body = {
            VerticalRadioGroup(
                spacing = 0.dp
            ) {
                values.forEach {
                    ListRadioButton(
                        modifier = Modifier
                            .fillMaxWidth()
                        /*.background(color = Color.Red)*/,
                        value = it,
                        groupValue = value,
                        onClick = { onValueChange(it); onDismissRequest() },
                        label = { Text(text = nameFor(it)) }
                    )
                }
            }
        }
    )
}