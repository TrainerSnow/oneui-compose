package org.oneui.compose.progress

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.oneui.compose.util.OneUIPreview

/**
 * Composable for a oneui-style horizontal progressbar, to display actual, quantifiable progress.
 *
 * @param modifier The [Modifier] to apply to the container
 * @param progress The progress, in range [0.0; 1.0]
 * @param colors The [ProgressBarColors] to apply
 */
@Composable
fun DeterminateProgressBar(
    modifier: Modifier = Modifier,
    @FloatRange(
        from = 0.0,
        to = 1.0,
        toInclusive = true,
        fromInclusive = true
    )
    progress: Float,
    colors: ProgressBarColors = progressBarColors()
) {
    val actProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(),
        label = "DeterminateProgressBar.Progress"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        drawTrack(
            color = colors.neutral,
            strokeWidth = DeterminateProgressBarDefaults.height.toPx()
        )
        drawProgress(
            color = colors.progress,
            strokeWidth = DeterminateProgressBarDefaults.height.toPx(),
            progress = actProgress
        )
    }
}

private fun DrawScope.drawTrack(
    color: Color,
    strokeWidth: Float
) {
    drawLine(
        start = Offset(
            x = 0F,
            y = size.height / 2F
        ),
        end = Offset(
            x = size.width,
            y = size.height / 2F
        ),
        color = color,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawProgress(
    color: Color,
    strokeWidth: Float,
    progress: Float
) {
    val end = Offset(
        x = size.width * progress,
        y = size.height / 2F
    )

    drawLine(
        start = Offset(
            x = 0F,
            y = size.height / 2F
        ),
        end = end,
        color = color,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

/**
 * Contains default values for a [DeterminateProgressBar]
 */
object DeterminateProgressBarDefaults {

    val height = 4.dp

}


/**
 * Normal preview for a [DeterminateProgressBar]
 */
@Preview
@Composable
fun DeterminateProgressBarPreview() =
    OneUIPreview(title = "DeterminateProgressBar") {
        Row(
            horizontalArrangement = Arrangement
                .spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DeterminateProgressBar(
                progress = 0.4F
            )
        }
    }