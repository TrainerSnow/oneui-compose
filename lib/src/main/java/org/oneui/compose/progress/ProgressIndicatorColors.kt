package org.oneui.compose.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.oneui.compose.theme.OneUITheme

/**
 * Contains the colors used for a [ProgressIndicator]
 */
data class ProgressIndicatorColors(

    val neutral: Color,

    val progress: Color,

    val secondaryProgress: Color

)

/**
 * Constructs the default progress indicator colors
 *
 * @param neutral The neutral colors, used for track and background
 * @param progress The progress color, used to show the progress level
 * @param secondaryProgress The secondary progress, used as an accent color
 */
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