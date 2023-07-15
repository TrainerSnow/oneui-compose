package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.oneui.compose.picker.color.ColorPickerDialog
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.buttons.ColoredButton

@Composable
fun ColorPickerShowcase() = RoundedCornerBox {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var color by remember {
            mutableStateOf(Color.Black)
        }
        var showDialog by remember {
            mutableStateOf(false)
        }

        ColoredButton(
            label = "Show dialog",
            onClick = { showDialog = true }
        )
        ColorPickerDialog(
            onDismissRequest = { showDialog = false },
            onColorPicked = { color = it }
        )
    }
}