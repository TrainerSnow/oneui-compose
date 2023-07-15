package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.widgets.EditText
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun EditTextShowcase(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
) {
    TextSeparator(text = "Edittext")
    RoundedCornerBox(
        modifier = modifier
            .fillMaxWidth(),
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(8.dp)
        ) {
            var value by remember { mutableStateOf("") }
            EditText(
                onValueChange = { value = it },
                value = value,
                hint = "Hint"
            )
        }
    }
}