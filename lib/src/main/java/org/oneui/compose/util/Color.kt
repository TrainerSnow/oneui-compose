package org.oneui.compose.util

import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun color(
    @ColorRes color: Int
): Color = Color(ContextCompat.getColor(LocalContext.current, color))