package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.buttons.Button
import org.oneui.compose.widgets.buttons.ColoredButton
import org.oneui.compose.widgets.buttons.TransparentButton
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun ButtonsShowcase(
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
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(8.dp)
        ) {
            ColoredButton(label = "Button 1")
            Button(label = "Button 2")
            TransparentButton(label = "Button 3")
        }
    }
}