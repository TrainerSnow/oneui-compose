package org.oneui.compose.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.oneui.compose.theme.OneUITheme

data class ProgressIndicatorColors(

    val neutral: Color,

    val progress: Color,

    val secondaryProgress: Color

)

@Composable
fun progressIndicatorColors(
    neutral: Color = OneUITheme.colors.seslProgressControlColorBackground,
    progress: Color = OneUITheme.colors.seslLoadingProgressColor2,
    secondaryProgress: Color = OneUITheme.colors.seslLoadingProgressColor1
): ProgressIndicatorColors = ProgressIndicatorColors(
    neutral = neutral,
    progress = progress,
    secondaryProgress = secondaryProgress
)