package org.oneui.compose.preference

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import org.oneui.compose.R
import org.oneui.compose.base.Icon
import org.oneui.compose.dialog.AlertDialog
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha
import org.oneui.compose.widgets.UnderlinedEditText


/**
 * Composable for a oneui-style preference that stores a single string
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param icon The [Icon] to be displayed at the start of the preference
 * @param title The title of the preference
 * @param interactionSource The [MutableInteractionSource] to be used
 * @param value The string value the preference currently holds
 * @param onValueChange The callback invoked when the user applied the changed value
 */
@Composable
fun EditTextPreference(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: Icon? = null,
    title: String,
    value: String = "",
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember { FocusRequester() }

    BasePreference(
        modifier = modifier,
        enabled = enabled,
        onClick = { showDialog = true },
        icon = icon,
        title = {
            Text(
                text = title
            )
        },
        summary = {
            Text(
                text = value,
                style = OneUITheme.types.preferenceSummary.copy(
                    color = OneUITheme.colors.seslPrimaryColor
                ).enabledAlpha(enabled)
            )
        },
        interactionSource = interactionSource
    )

    if (showDialog) {

        var editTextValue by remember {
            mutableStateOf(value)
        }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = title) },
            body = {
                UnderlinedEditText(
                    modifier = modifier
                        .focusRequester(focusRequester),
                    value = editTextValue,
                    onValueChange = { editTextValue = it },
                )
            },
            positiveButtonLabel = stringResource(R.string.sesl_picker_done),
            onPositiveButtonClick = { onValueChange(editTextValue); showDialog = false },
            negativeButtonLabel = stringResource(R.string.sesl_picker_cancel),
            onNegativeButtonClick = { showDialog = false }
        )

        //TextField is focused when dialog appears
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}