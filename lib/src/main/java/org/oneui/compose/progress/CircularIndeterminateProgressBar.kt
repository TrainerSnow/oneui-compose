package org.oneui.compose.progress

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
import org.oneui.compose.util.OneUIPreview

/**
 * Composable for a oneui-style circular progressbar, to display uncertain progress that can take
 *     unknown amount of time
 *
 * @param size The [CircularProgressBarSize]
 * @param colors The [ProgressBarColors] to apply
 */
@Composable
fun CircularIndeterminateProgressBar(
    size: CircularProgressBarSize = CircularProgressBarSize.Medium,
    colors: ProgressBarColors = progressBarColors()
) {
    val rotationEasing = CubicBezierEasing(0F, 0F, 0F, 0F)
    val transition = rememberInfiniteTransition(
        label = "CircularIndeterminateProgressBar"
    )
    val rotation by transition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressBarDefaults.animDurationTotal,
                easing = rotationEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "CircularIndeterminateProgressBar.Rotation"
    )
    val c1y by transition.animateFloat(
        initialValue = CircularIndeterminateProgressBarDefaults.c1.y,
        targetValue = CircularIndeterminateProgressBarDefaults.c1to.y,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressBarDefaults.animDurationTotal / 2
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircularIndeterminateProgressBar.C1Y"
    )
    val c2x by transition.animateFloat(
        initialValue = CircularIndeterminateProgressBarDefaults.c2.x,
        targetValue = CircularIndeterminateProgressBarDefaults.c2to.x,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressBarDefaults.animDurationTotal / 2
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircularIndeterminateProgressBar.C2X"
    )
    val c3y by transition.animateFloat(
        initialValue = CircularIndeterminateProgressBarDefaults.c3.y,
        targetValue = CircularIndeterminateProgressBarDefaults.c3to.y,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressBarDefaults.animDurationTotal / 2
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircularIndeterminateProgressBar.C3Y"
    )
    val c4x by transition.animateFloat(
        initialValue = CircularIndeterminateProgressBarDefaults.c4.x,
        targetValue = CircularIndeterminateProgressBarDefaults.c4to.x,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CircularIndeterminateProgressBarDefaults.animDurationTotal / 2,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircularIndeterminateProgressBar.C4X"
    )


    Canvas(
        modifier = Modifier
            .size(size.size)
    ) {
        rotate(
            degrees = rotation
        ) {
            circle(
                pos = CircularIndeterminateProgressBarDefaults.c1.copy(
                    y = c1y
                ),
                color = colors.progress
            )
            circle(
                pos = CircularIndeterminateProgressBarDefaults.c2.copy(
                    x = c2x
                ),
                color = colors.progress
            )
            circle(
                pos = CircularIndeterminateProgressBarDefaults.c3.copy(
                    y = c3y
                ),
                color = colors.progress
            )
            circle(
                pos = CircularIndeterminateProgressBarDefaults.c4.copy(
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
        radius = CircularIndeterminateProgressBarDefaults.circleRadius * size.width
    )
}

/**
 * Contains default values for a [CircularIndeterminateProgressBar]
 * For easier implementation, the sizes/positions are mostly stated in percent of maximum size.
 */
object CircularIndeterminateProgressBarDefaults {

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
 * Normal preview for a [CircularIndeterminateProgressBar]
 */
@Preview
@Composable
fun CircularIndeterminateProgressBarPreview() =
    OneUIPreview(title = "CircularIndeterminateProgressBar") {
        Row(
            horizontalArrangement = Arrangement
                .spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressBarSize.values().forEach {
                CircularIndeterminateProgressBar(
                    size = it
                )
            }
        }
    }