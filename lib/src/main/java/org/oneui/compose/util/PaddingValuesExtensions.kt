package org.oneui.compose.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PaddingValues.plus(
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp
): PaddingValues = PaddingValues(
    top = calculateTopPadding() + top,
    bottom = calculateBottomPadding() + bottom,
    end = calculateEndPadding(LocalLayoutDirection.current) + end,
    start = calculateStartPadding(LocalLayoutDirection.current) + start
)