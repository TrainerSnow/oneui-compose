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
import org.oneui.compose.widgets.HorizontalSeekbar
import org.oneui.compose.widgets.HorizontalSeekbarExpanding
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun HorizontalSeekbarShowcase(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
) {
    TextSeparator(text = "Horizontal Seekbars")
    RoundedCornerBox(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(16.dp)
        ) {
            var progress1 by remember {
                mutableStateOf(0.5F)
            }
            HorizontalSeekbar(value = progress1, onValueChange = {
                progress1 = it
            })

            var progress2 by remember {
                mutableStateOf(0.5F)
            }
            HorizontalSeekbarExpanding(value = progress2, onValueChange = {
                progress2 = it
            })
        }
    }
}