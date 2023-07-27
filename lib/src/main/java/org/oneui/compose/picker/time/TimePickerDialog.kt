package org.oneui.compose.picker.time

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.oneui.compose.R
import org.oneui.compose.dialog.AlertDialog
import java.time.LocalTime

/**
 * Dialog wrapper for [TimePicker]
 *
 * @param modifier The [Modifier] to be applied to the dialog content
 * @param onDismissRequest The callback invoked when the dialog is dismissed
 * @param onTimeSelected The callback invoked when a time has been selected
 * @param initialTime The initial time to show on the dialog
 * @param config The [TimePickerConfig] to configure important settings
 * @param title The title to show on the dialog
 */
@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    initialTime: LocalTime = LocalTime.now(),
    config: TimePickerConfig = timePickerConfig(),
    title: String = stringResource(R.string.sesl_dialog_picker_time_title)
) {
    val state = remember {
        TimePickerState(
            initial = initialTime
        )
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        positiveButtonLabel = stringResource(R.string.sesl_dialog_picker_time_positive),
        onPositiveButtonClick = {
            onTimeSelected(state.time)
        },
        negativeButtonLabel = stringResource(R.string.sesl_dialog_picker_time_negative),
        onNegativeButtonClick = onDismissRequest,
        body = {
            TimePicker(
                state = state,
                config = config
            )
        }
    )
}