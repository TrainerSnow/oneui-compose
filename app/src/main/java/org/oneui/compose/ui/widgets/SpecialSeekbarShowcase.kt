package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.widgets.HorizontalSeekbarWarning
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun SpecialSeekbarShowcase(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
) {
    TextSeparator(text = "Special Seekbars")
    RoundedCornerBox {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var progress1 by remember {
                mutableStateOf(0.2F)
            }
            HorizontalSeekbarWarning(
                value = progress1,
                onValueChange = { progress1 = it },
                warningAt = 0.75F
            )
        }
    }
}