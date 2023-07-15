package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.widgets.VerticalSeekbar
import org.oneui.compose.widgets.VerticalSeekbarExpanding
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun VerticalSeekbarShowcase(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
        .height(300.dp)
) {
    TextSeparator(text = "Vertical Seekbars")
    RoundedCornerBox(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            var progress1 by remember {
                mutableStateOf(0.2F)
            }
            VerticalSeekbar(value = progress1, onValueChange = {
                progress1 = it
            })

            var progress2 by remember {
                mutableStateOf(0.2F)
            }
            VerticalSeekbarExpanding(value = progress2, onValueChange = {
                progress2 = it
            })
        }
    }
}