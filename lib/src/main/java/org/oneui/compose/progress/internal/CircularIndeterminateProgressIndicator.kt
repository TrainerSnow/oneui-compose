package org.oneui.compose.progress.internal

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.oneui.compose.progress.CircularProgressIndicatorSize
import org.oneui.compose.progress.ProgressIndicatorColors
import org.oneui.compose.progress.progressIndicatorColors
import org.oneui.compose.util.OneUIPreview

/**
 * Composable for a oneui-style circular progress indicator, to display uncertain progress that can take
 *     unknown amount of time
 *
 * @param size The [CircularProgressIndicatorSize]
 * @param colors The [ProgressIndicatorColors] to apply
 */
@Composable
internal fun CircularIndeterminateProgressIndicator(
    size: CircularProgressIndicatorSize = CircularProgressIndicatorSize.Medium,
    colors: ProgressIndicatorColors = progressIndicatorColors()
) {
    val rotationEasing = CubicBezierEasing(0F, 0F, 0F, 0F)
    val transition = rememberInfiniteTransition(
        label = "CircularIndeterminateProgressIndicator"
    )
    val rotation by transition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressIndicatorDefaults.animDurationTotal,
                easing = rotationEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "CircularIndeterminateProgressIndicator.Rotation"
    )
    val c1y by transition.animateFloat(
        initialValue = CircularIndeterminateProgressIndicatorDefaults.c1.y,
        targetValue = CircularIndeterminateProgressIndicatorDefaults.c1to.y,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressIndicatorDefaults.animDurationTotal / 2
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircularIndeterminateProgressIndicator.C1Y"
    )
    val c2x by transition.animateFloat(
        initialValue = CircularIndeterminateProgressIndicatorDefaults.c2.x,
        targetValue = CircularIndeterminateProgressIndicatorDefaults.c2to.x,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressIndicatorDefaults.animDurationTotal / 2
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircularIndeterminateProgressIndicator.C2X"
    )
    val c3y by transition.animateFloat(
        initialValue = CircularIndeterminateProgressIndicatorDefaults.c3.y,
        targetValue = CircularIndeterminateProgressIndicatorDefaults.c3to.y,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressIndicatorDefaults.animDurationTotal / 2
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircularIndeterminateProgressIndicator.C3Y"
    )
    val c4x by transition.animateFloat(
        initialValue = CircularIndeterminateProgressIndicatorDefaults.c4.x,
        targetValue = CircularIndeterminateProgressIndicatorDefaults.c4to.x,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressIndicatorDefaults.animDurationTotal / 2,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircularIndeterminateProgressIndicator.C4X"
    )


    Canvas(
        modifier = Modifier
            .size(size.size)
    ) {
        rotate(
            degrees = rotation
        ) {
            circle(
                pos = CircularIndeterminateProgressIndicatorDefaults.c1.copy(
                    y = c1y
                ),
                color = colors.progress
            )
            circle(
                pos = CircularIndeterminateProgressIndicatorDefaults.c2.copy(
                    x = c2x
                ),
                color = colors.progress
            )
            circle(
                pos = CircularIndeterminateProgressIndicatorDefaults.c3.copy(
                    y = c3y
                ),
                color = colors.progress
            )
            circle(
                pos = CircularIndeterminateProgressIndicatorDefaults.c4.copy(
                    x = c4x
                ),
                color = colors.secondaryProgress
            )
        }
    }
}

private fun DrawScope.circle(
    pos: Offset,
    color: Color
) {
    drawCircle(
        color = color,
        center = Offset(
            x = pos.x * size.width,
            y = pos.y * size.width
        ),
        radius = CircularIndeterminateProgressIndicatorDefaults.circleRadius * size.width
    )
}

/**
 * Contains default values for a [CircularIndeterminateProgressIndicator]
 * For easier implementation, the sizes/positions are mostly stated in percent of maximum size.
 */
internal object CircularIndeterminateProgressIndicatorDefaults {

    val c1 = Offset(
        x = (48F / 96F),
        y = (48F / 96F) + (-35F / 96F)
    )
    val c1to = Offset(
        x = (48F / 96F),
        y = (48F / 96F) + (-15F / 96F)
    )

    val c2 = Offset(
        x = (48F / 96F) + (-35F / 96F),
        y = (48F / 96F)
    )
    val c2to = Offset(
        x = (48F / 96F) + (-15F / 96F),
        y = (48F / 96F)
    )

    val c3 = Offset(
        x = (48F / 96F),
        y = (48F / 96F) + (35F / 96F)
    )
    val c3to = Offset(
        x = (48F / 96F),
        y = (48F / 96F) + (15F / 96F)
    )

    val c4 = Offset(
        x = (48F / 96F) + (35F / 96F),
        y = (48F / 96F)
    )
    val c4to = Offset(
        x = (48F / 96F) + (15F / 96F),
        y = (48F / 96F)
    )

    const val circleRadius = (110F / 620F) / 2

    const val animDurationTotal = 983
}

/**
 * Normal preview for a [CircularIndeterminateProgressIndicator]
 */
@Preview
@Composable
private fun CircularIndeterminateProgressIndicatorPreview() =
    OneUIPreview(title = "CircularIndeterminateProgressIndicator") {
        Row(
            horizontalArrangement = Arrangement
                .spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicatorSize.values().forEach {
                CircularIndeterminateProgressIndicator(
                    size = it
                )
            }
        }
    }