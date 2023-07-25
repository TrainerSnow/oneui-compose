package org.oneui.compose.progress.internal

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.oneui.compose.progress.CircularProgressIndicatorSize
import org.oneui.compose.progress.ProgressIndicatorColors
import org.oneui.compose.progress.progressIndicatorColors
import org.oneui.compose.util.OneUIPreview
import kotlin.math.cos
import kotlin.math.sin

/**
 * Composable for a oneui-style circular progress indicator, to display actual, quantifiable progress.
 *
 * @param size The [CircularProgressIndicatorSize]
 * @param progress The progress, in range [0.0; 1.0]
 * @param colors The [ProgressIndicatorColors] to apply
 */
@Composable
internal fun CircularDeterminateProgressIndicator(
    size: CircularProgressIndicatorSize = CircularProgressIndicatorSize.Medium,
    @FloatRange(
        from = 0.0,
        to = 1.0,
        toInclusive = true,
        fromInclusive = true
    )
    progress: Float,
    colors: ProgressIndicatorColors = progressIndicatorColors()
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(),
        label = "CircularDeterminateProgressIndicator"
    )

    Canvas(
        modifier = Modifier
            .size(size.size)
    ) {
        drawTrack(
            color = colors.neutral,
            strokeWidth = size.strokeSize.toPx()
        )

        drawProgress(
            color = colors.progress,
            strokeWidth = size.strokeSize.toPx(),
            progress = animatedProgress
        )
        drawProgressTips(
            color = colors.progress,
            strokeWidth = size.strokeSize.toPx(),
            progress = animatedProgress
        )
    }
}

private fun DrawScope.drawTrack(
    color: Color,
    strokeWidth: Float
) {
    val actSize = Size(
        width = size.width - strokeWidth,
        height = size.height - strokeWidth
    )

    drawCircle(
        color = color,
        radius = actSize.width / 2F,
        center = Offset(
            x = size.width / 2,
            y = size.height / 2
        ),
        style = Stroke(
            width = strokeWidth
        )
    )
}

private fun DrawScope.drawProgress(
    color: Color,
    strokeWidth: Float,
    progress: Float
) {
    val actSize = Size(
        width = size.width - strokeWidth,
        height = size.height - strokeWidth
    )
    val sweepAngle = 360F * progress

    drawArc(
        color = color,
        startAngle = -90F,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset(
            x = strokeWidth / 2F,
            y = strokeWidth / 2F
        ),
        size = actSize,
        style = Stroke(
            width = strokeWidth
        ),
    )
}

private fun DrawScope.drawProgressTips(
    color: Color,
    strokeWidth: Float,
    progress: Float
) {
    val actSize = Size(
        width = size.width - strokeWidth,
        height = size.height - strokeWidth
    )
    val center = size / 2F
    val radius = actSize.width / 2F
    val sweepAngle = (360F * progress) - 90F

    val center1 = Offset(
        x = size.width / 2F,
        y = strokeWidth / 2F
    )
    drawCircle(
        color = color,
        radius = strokeWidth / 2F,
        center = center1
    )

    val center2 = Offset(
        x = center.width + radius * cos(Math.toRadians(sweepAngle.toDouble()).toFloat()),
        y = center.height + radius * sin(Math.toRadians(sweepAngle.toDouble()).toFloat())
    )
    drawCircle(
        color = color,
        radius = strokeWidth / 2F,
        center = center2
    )
}

/**
 * Normal preview for a [CircularDeterminateProgressIndicator]
 */
@Preview
@Composable
private fun CircularDeterminateProgressIndicatorPreview() =
    OneUIPreview(title = "CircularDeterminateProgressIndicator") {
        Row(
            horizontalArrangement = Arrangement
                .spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicatorSize.values().forEach {
                CircularDeterminateProgressIndicator(
                    size = it,
                    progress = 0.8F
                )
            }
        }
    }