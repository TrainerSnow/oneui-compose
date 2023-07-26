package org.oneui.compose.picker.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Composable
internal fun ItemScrollOverlay(
    modifier: Modifier = Modifier,
    color: Color,
    windowHeight: Dp
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(color)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(windowHeight)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(color)
        )
    }
}