package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.oneui.compose.dialog.AlertDialog
import org.oneui.compose.dialog.DialogButton
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.buttons.ColoredButton
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun DialogShowcase(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
) {
    TextSeparator(text = "Buttons")
    RoundedCornerBox(
        modifier = modifier
            .fillMaxWidth(),
        onClick = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement
                .SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val info = "Place some informative text to inform the user about his choicees."

            @Composable
            fun button(label: String, three: Boolean = false) = @Composable {
                DialogButton(
                    modifier = Modifier
                        .weight(1F),
                    label = label,
                    onClick = { },
                    threeButton = three
                )
            }

            var showDialog1 by remember { mutableStateOf(false) }
            if (showDialog1) {
                AlertDialog(
                    onDismissRequest = { showDialog1 = false },
                    title = "Three options dialog",
                    body = info,
                    positiveButtonLabel = "OK",
                    negativeButtonLabel = "Abbrechen",
                    neutralButtonLabel = "Vielleicht"
                )
            }
            ColoredButton(label = "Dialog 1", onClick = { showDialog1 = true })

            var showDialog2 by remember { mutableStateOf(false) }
            if (showDialog2) {
                AlertDialog(
                    onDismissRequest = { showDialog2 = false },
                    title = "Two options dialog",
                    body = info,
                    positiveButtonLabel = "OK",
                    negativeButtonLabel = "Abbrechen",
                )
            }
            ColoredButton(label = "Dialog 2", onClick = { showDialog2 = true })

            var showDialog3 by remember { mutableStateOf(false) }
            if (showDialog3) {
                AlertDialog(
                    onDismissRequest = { showDialog3 = false },
                    title = "One option dialog",
                    body = info,
                    positiveButtonLabel = "OK",
                )
            }
            ColoredButton(label = "Dialog 3", onClick = { showDialog3 = true })
        }
    }
}