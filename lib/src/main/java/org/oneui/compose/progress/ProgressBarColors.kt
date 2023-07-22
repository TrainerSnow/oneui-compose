package org.oneui.compose.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.oneui.compose.theme.OneUITheme

data class ProgressBarColors(

    val neutral: Color,

    val progress: Color,

    val secondaryProgress: Color

)

@Composable
fun progressBarColors(
    neutral: Color = OneUITheme.colors.seslProgressControlColorBackground,
    progress: Color = OneUITheme.colors.seslLoadingProgressColor2,
    secondaryProgress: Color = OneUITheme.colors.seslLoadingProgressColor2
): ProgressBarColors = ProgressBarColors(
    neutral = neutral,
    progress = progress,
    secondaryProgress = secondaryProgress
)