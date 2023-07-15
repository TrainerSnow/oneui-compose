package org.oneui.compose.base

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DividableColumn(
    modifier: Modifier = Modifier,
    divider: (@Composable () -> Unit) = { },
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {

    }
}