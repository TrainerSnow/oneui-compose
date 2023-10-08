package org.oneui.compose.picker.time

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.dialog.AlertDialogDefaults
import org.oneui.compose.dialog.BaseDialog
import org.oneui.compose.dialog.DialogButton
import java.time.LocalDate

/**
 * Dialog wrapper for [TimePicker]
 *
 * @param modifier The [Modifier] to be applied to the dialog content
 * @param onDismissRequest The callback invoked when the dialog is dismissed
 * @param onDateSelected The callback invoked when a date has been selected
 * @param initialSelectedDate The initial date to show on the dialog
 * @param startDate The first possible month to show (day property is ignored)
 * @param endDate The last possible month to show (day property is ignored)
 * @param colors The [DatePickerColors] to apply
 */
@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    initialSelectedDate: LocalDate = LocalDate.now(),
    startDate: LocalDate = DatePickerDefaults.startDate,
    endDate: LocalDate = DatePickerDefaults.endDate,
    colors: DatePickerColors = datePickerColors()
) {
    val state = rememberDatePickerState(
        initial = initialSelectedDate
    )

    BaseDialog(
        modifier = modifier,
        padding = PaddingValues(
            vertical = 24.dp
        ),
        onDismissRequest = onDismissRequest
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            DatePicker(
                state = state,
                startDate = startDate,
                endDate = endDate,
                colors = colors
            )

            Spacer(
                modifier = Modifier
                    .height(AlertDialogDefaults.bodyButtonSpacing)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp
                    )
            ) {
                DialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = stringResource(id = R.string.sesl_picker_cancel),
                    onClick = onDismissRequest
                )
                DialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = stringResource(id = R.string.sesl_picker_done),
                    onClick = {
                        onDateSelected(state.date)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}