package org.oneui.compose.dialog

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.SeslTheme


/**
 * Core composable for a oneui-style dialog with title, body and button row.
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param onDismissRequest The callback invoked when the button should be closed
 * @param title The title-composable, preferably a [Text]
 * @param body The body composable, possibly a [Text]
 * @param buttonBar The button bar composable, possibly a [Row] with [DialogButton]s
 */
@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: (@Composable () -> Unit)? = null,
    body: (@Composable () -> Unit)? = null,
    buttonBar: (@Composable () -> Unit)? = null
) {
    BaseDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            ProvideTextStyle(SeslTheme.types.dialogTitle) {
                title?.let { it() }
            }
            if (title != null && body != null) {
                Spacer(
                    modifier = Modifier
                        .height(AlertDialogDefaults.titleBodySpacing)
                )
            }
            ProvideTextStyle(SeslTheme.types.dialogBody) {
                body?.let { it() }
            }
            if (buttonBar != null) {
                Spacer(
                    modifier = Modifier
                        .height(AlertDialogDefaults.bodyButtonSpacing)
                )
            }
            buttonBar?.let { it() }
        }
    }
}

/**
 * Overload for [AlertDialog]
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param onDismissRequest The callback invoked when the button should be closed
 * @param title The title-composable, preferably a [Text]
 * @param body The body composable, possibly a [Text]
 * @param buttonBar The button bar composable, possibly a [Row] with [DialogButton]s
 */
@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: String? = null,
    body: String? = null,
    buttonBar: (@Composable () -> Unit)? = null
) {
    AlertDialog(
        modifier,
        onDismissRequest,
        title = {
            title?.let {
                Text(
                    text = title
                )
            }
        },
        body = {
            body?.let {
                Text(
                    text = body
                )
            }
        },
        buttonBar = buttonBar
    )
}

/**
 * Overload for [AlertDialog]
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param onDismissRequest The callback invoked when the button should be closed
 * @param title The title-composable, preferably a [Text]
 * @param body The body composable, possibly a [Text]
 * @param positiveButton The composable for the positive button, placed at the end
 * @param neutralButton The composable for the neutral button, placed in the center
 * @param negativeButton The composable for the negative button, placed at the start
 */
@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: (@Composable () -> Unit)? = null,
    body: (@Composable () -> Unit)? = null,
    positiveButton: (@Composable RowScope.() -> Unit)? = null,
    neutralButton: (@Composable RowScope.() -> Unit)? = null,
    negativeButton: (@Composable RowScope.() -> Unit)? = null
) {
    assert(
        positiveButton != null ||
                neutralButton != null ||
                negativeButton != null
    ) { "At least one of the three buttons must be defined!" }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = title,
        body = body,
        buttonBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                negativeButton?.let { it() }
                neutralButton?.let { it() }
                positiveButton?.let { it(this) }
            }
        }
    )
}

/**
 * Overload for [AlertDialog]
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param onDismissRequest The callback invoked when the button should be closed
 * @param title The title-composable, preferably a [Text]
 * @param body The body composable, possibly a [Text]
 * @param onPositiveButtonClick The callback invoked when the positive button is clicked
 * @param positiveButtonLabel The label the positive button is given
 * @param onNeutralButtonClick The callback invoked when the neutral button is clicked
 * @param neutralButtonLabel The label the neutral button is given
 * @param onNegativeButtonClick The callback invoked when the negative button is clicked
 * @param negativeButtonLabel The label the negative button is given
 */
@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: (@Composable () -> Unit)? = null,
    body: (@Composable () -> Unit)? = null,
    positiveButtonLabel: String? = null,
    onPositiveButtonClick: (() -> Unit)? = null,
    neutralButtonLabel: String? = null,
    onNeutralButtonClick: (() -> Unit)? = null,
    negativeButtonLabel: String? = null,
    onNegativeButtonClick: (() -> Unit)? = null
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = title,
        body = body,
        positiveButton = positiveButtonLabel?.let {
            {
                DialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = positiveButtonLabel,
                    onClick = onPositiveButtonClick
                )
            }
        },
        negativeButton = negativeButtonLabel?.let {
            {
                DialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = negativeButtonLabel,
                    onClick = onNegativeButtonClick
                )
            }
        },
        neutralButton = neutralButtonLabel?.let {
            {
                DialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = neutralButtonLabel,
                    onClick = onNeutralButtonClick
                )
            }
        }
    )
}

/**
 * Overload for [AlertDialog]
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param onDismissRequest The callback invoked when the button should be closed
 * @param title The string title of the dialog
 * @param body The string body of the dialog
 * @param onPositiveButtonClick The callback invoked when the positive button is clicked
 * @param positiveButtonLabel The label the positive button is given
 * @param onNeutralButtonClick The callback invoked when the neutral button is clicked
 * @param neutralButtonLabel The label the neutral button is given
 * @param onNegativeButtonClick The callback invoked when the negative button is clicked
 * @param negativeButtonLabel The label the negative button is given
 */
@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: String? = null,
    body: String? = null,
    positiveButtonLabel: String? = null,
    onPositiveButtonClick: (() -> Unit)? = null,
    neutralButtonLabel: String? = null,
    onNeutralButtonClick: (() -> Unit)? = null,
    negativeButtonLabel: String? = null,
    onNegativeButtonClick: (() -> Unit)? = null
) {
    val buttonNum = listOfNotNull(positiveButtonLabel, neutralButtonLabel, negativeButtonLabel).size
    val threeButton = buttonNum == 3

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            title?.let {
                Text(
                    text = title
                )
            }
        },
        body = {
            body?.let {
                Text(
                    text = body
                )
            }
        },
        positiveButton = {
            positiveButtonLabel?.let {
                DialogButton(
                    label = {
                        Text(
                            text = positiveButtonLabel
                        )
                    },
                    onClick = { onPositiveButtonClick?.let { it() } },
                    threeButton = threeButton
                )
            }
        },
        neutralButton = {
            neutralButtonLabel?.let {
                DialogButton(
                    label = {
                        Text(
                            text = neutralButtonLabel
                        )
                    },
                    onClick = { onNeutralButtonClick?.let { it() } },
                    threeButton = threeButton
                )
            }
        },
        negativeButton = {
            negativeButtonLabel?.let {
                DialogButton(
                    label = {
                        Text(
                            text = negativeButtonLabel
                        )
                    },
                    onClick = { onNegativeButtonClick?.let { it() } },
                    threeButton = threeButton
                )
            }
        },
    )
}


/**
 * Contains default values for a [AlertDialog]
 */
object AlertDialogDefaults {

    val titleBodySpacing = 8.dp

    val bodyButtonSpacing = 26.dp

}