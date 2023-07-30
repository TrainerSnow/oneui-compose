package org.oneui.compose.preference

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.base.Icon
import org.oneui.compose.dialog.AlertDialog
import org.oneui.compose.widgets.buttons.ListCheckbox
import org.oneui.compose.widgets.buttons.radio.VerticalRadioGroup


/**
 * Composable for a oneui-style preference that allows the user to store multiple
 *     selections out of multiple choices
 *
 * @param T The type the checkboxes-components are associated with
 * @param modifier THe [Modifier] applied to the container
 * @param icon The [Icon] to be applied to the preference
 * @param title The title of the preference
 * @param interactionSource The [MutableInteractionSource]
 * @param values All possible values that can be selected
 * @param selectedValues The list pf currently selected values
 * @param nameFor A lambda to convert a [T] variable to it's visual representation
 * @param onValuesChange The callback invoked with the new selection of items, when the selection is applied by the user
 * @param summary The summary to display on the preference
 */
@Composable
fun <T> MultiSelectPreference(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: Icon? = null,
    title: String,
    selectedValues: List<T>,
    values: List<T>,
    onValuesChange: (List<T>) -> Unit,
    nameFor: ((T) -> String) = { it.toString() },
    summary: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    assert(values.containsAll(selectedValues)) { "All of the selected values must be present in the provided values" }
    var showDialog by remember {
        mutableStateOf(false)
    }

    BasePreference(
        modifier = modifier,
        onClick = { showDialog = true },
        enabled = enabled,
        icon = icon,
        title = {
            Text(
                text = title
            )
        },
        summary = summary?.let {
            {
                Text(
                    text = summary
                )
            }
        },
        interactionSource = interactionSource
    )

    //Because we cannot convert a dynamically-typed list to a typed array, we need to 'transport'
    //the T-values as 'Any' types to create a MutableStateList
    val selectedVals = Array(selectedValues.size) { selectedValues[it] as Any }
    if (showDialog) {
        MultiSelectPreferenceDialog(
            title = title,
            selectedValues = selectedVals,
            values = values,
            nameFor = nameFor,
            onDismissRequest = { showDialog = false },
            onValuesChange = {
                showDialog = false
                onValuesChange(it)
            }
        )
    }

}

@Suppress("UNCHECKED_CAST")
@Composable
internal fun <T> MultiSelectPreferenceDialog(
    modifier: Modifier = Modifier,
    title: String,
    selectedValues: Array<Any>,
    values: List<T>,
    onValuesChange: (List<T>) -> Unit,
    nameFor: ((T) -> String) = { it.toString() },
    onDismissRequest: () -> Unit
) {
    //Because we cannot convert a dynamically-typed list to a typed array, we need to 'transport'
    //the T-values as 'Any' types to create a MutableStateList
    val selectedItemsChanged = remember {
        mutableStateListOf(*selectedValues)
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        positiveButtonLabel = stringResource(R.string.sesl_dialog_button_positive),
        onPositiveButtonClick = {
            onValuesChange(selectedItemsChanged.map { it as T })
        },
        negativeButtonLabel = stringResource(R.string.sesl_dialog_button_negative),
        onNegativeButtonClick = onDismissRequest,
        body = {
            VerticalRadioGroup(
                spacing = 0.dp
            ) {
                values.forEach {
                    ListCheckbox(
                        modifier = Modifier
                            .fillMaxWidth(),
                        checked = (it as Any) in selectedItemsChanged,
                        onCheckedChange = { checked ->
                            if (checked)
                                selectedItemsChanged.add(it)
                            else
                                selectedItemsChanged.remove(it)
                        },
                        label = { Text(text = nameFor(it)) }
                    )
                }
            }
        }
    )
}