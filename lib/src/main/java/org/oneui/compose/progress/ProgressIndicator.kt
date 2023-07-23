package org.oneui.compose.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    type: ProgressIndicatorType
) {
    when (type) {
        is ProgressIndicatorType.CircularDeterminate -> {
            CircularDeterminateProgressIndicator(
                size = type.size,
                progress = type.progress
            )
        }

        is ProgressIndicatorType.CircularIndeterminate -> {
            CircularIndeterminateProgressIndicator(
                size = type.size
            )
        }

        is ProgressIndicatorType.HorizontalDeterminate -> {
            DeterminateProgressIndicator(
                modifier = modifier,
                progress = type.progress
            )
        }

        ProgressIndicatorType.HorizontalIndeterminate -> {
            IndeterminateProgressIndicator(
                modifier = modifier
            )
        }
    }
}