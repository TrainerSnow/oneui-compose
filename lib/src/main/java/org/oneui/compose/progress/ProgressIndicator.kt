package org.oneui.compose.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.oneui.compose.progress.internal.CircularDeterminateProgressIndicator
import org.oneui.compose.progress.internal.CircularIndeterminateProgressIndicator
import org.oneui.compose.progress.internal.DeterminateProgressIndicator
import org.oneui.compose.progress.internal.IndeterminateProgressIndicator

/**
 * Composable for a oneui-style progress indicator, defined by [type]
 *
 * @param modifier The [Modifier] to, potentially, apply to the container
 * @param type The [ProgressIndicatorType], defining the visuals of the indicator
 * @param colors The [ProgressIndicatorColors] to apply
 */
@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    type: ProgressIndicatorType,
    colors: ProgressIndicatorColors = progressIndicatorColors()
) {
    when (type) {
        is ProgressIndicatorType.CircularDeterminate -> {
            CircularDeterminateProgressIndicator(
                size = type.size,
                progress = type.progress,
                colors = colors
            )
        }

        is ProgressIndicatorType.CircularIndeterminate -> {
            CircularIndeterminateProgressIndicator(
                size = type.size,
                colors = colors
            )
        }

        is ProgressIndicatorType.HorizontalDeterminate -> {
            DeterminateProgressIndicator(
                modifier = modifier,
                progress = type.progress,
                colors = colors
            )
        }

        ProgressIndicatorType.HorizontalIndeterminate -> {
            IndeterminateProgressIndicator(
                modifier = modifier,
                colors = colors
            )
        }
    }
}