package org.oneui.compose.progress.internal

import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.oneui.compose.progress.ProgressIndicatorColors
import org.oneui.compose.progress.internal.IndeterminateProgressIndicatorDefaults.toConf
import org.oneui.compose.progress.progressIndicatorColors
import org.oneui.compose.util.OneUIPreview

/**
 * Composable for a oneui-style progress indicator, horizontal and inteterminate.
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param colors The [ProgressIndicatorColors] to apply
 */
@Composable
internal fun IndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
    colors: ProgressIndicatorColors = progressIndicatorColors()
) {
    val transition = rememberInfiniteTransition(
        label = "IndeterminateProgressIndicator"
    )

    val l1s by transition.animateFloat(
        targetValue = 1F,
        label = "IndeterminateProgressIndicator.L1S",
        animationSpec = infiniteRepeatable(
            animation = KeyframesSpec(
                KeyframesSpec.KeyframesSpecConfig<Float>().apply {
                    durationMillis = IndeterminateProgressIndicatorDefaults.animDuration
                    toConf(IndeterminateProgressIndicatorDefaults.l1s)
                }
            )
        ),
        initialValue = 0F
    )
    val l1e by transition.animateFloat(
        targetValue = 1F,
        label = "IndeterminateProgressIndicator.L1E",
        animationSpec = infiniteRepeatable(
            animation = KeyframesSpec(
                KeyframesSpec.KeyframesSpecConfig<Float>().apply {
                    durationMillis = IndeterminateProgressIndicatorDefaults.animDuration
                    toConf(IndeterminateProgressIndicatorDefaults.l1e)
                }
            )
        ),
        initialValue = 0F
    )

    val l2s by transition.animateFloat(
        targetValue = 1F,
        label = "IndeterminateProgressIndicator.L2S",
        animationSpec = infiniteRepeatable(
            KeyframesSpec(
                KeyframesSpec.KeyframesSpecConfig<Float>().apply {
                    durationMillis = IndeterminateProgressIndicatorDefaults.animDuration
                    toConf(IndeterminateProgressIndicatorDefaults.l2s)
                }
            )
        ),
        initialValue = 0F
    )
    val l2e by transition.animateFloat(
        targetValue = 1F,
        label = "IndeterminateProgressIndicator.L2E",
        animationSpec = infiniteRepeatable(
            animation = KeyframesSpec(
                KeyframesSpec.KeyframesSpecConfig<Float>().apply {
                    durationMillis = IndeterminateProgressIndicatorDefaults.animDuration
                    toConf(IndeterminateProgressIndicatorDefaults.l2e)
                }
            )
        ),
        initialValue = 0F
    )

    val l3s by transition.animateFloat(
        targetValue = 1F,
        label = "IndeterminateProgressIndicator.L3S",
        animationSpec = infiniteRepeatable(
            animation = KeyframesSpec(
                KeyframesSpec.KeyframesSpecConfig<Float>().apply {
                    durationMillis = IndeterminateProgressIndicatorDefaults.animDuration
                    toConf(IndeterminateProgressIndicatorDefaults.l3s)
                }
            )
        ),
        initialValue = 0F
    )
    val l3e by transition.animateFloat(
        targetValue = 1F,
        label = "IndeterminateProgressIndicator.L3E",
        animationSpec = infiniteRepeatable(
            animation = KeyframesSpec(
                KeyframesSpec.KeyframesSpecConfig<Float>().apply {
                    durationMillis = IndeterminateProgressIndicatorDefaults.animDuration
                    toConf(IndeterminateProgressIndicatorDefaults.l3e)
                }
            )
        ),
        initialValue = 0F
    )
    Canvas(
        modifier = modifier
            .fillMaxWidth()
    ) {
        drawTrack(
            color = colors.neutral,
            strokeWidth = IndeterminateProgressIndicatorDefaults.height.toPx()
        )

        drawProgressBit(
            color = colors.progress,
            strokeWidth = IndeterminateProgressIndicatorDefaults.height.toPx(),
            start = l1s * size.width,
            end = l1e * size.width
        )
        drawProgressBit(
            color = colors.progress,
            strokeWidth = IndeterminateProgressIndicatorDefaults.height.toPx(),
            start = l2s * size.width,
            end = l2e * size.width
        )
        drawProgressBit(
            color = colors.progress,
            strokeWidth = IndeterminateProgressIndicatorDefaults.height.toPx(),
            start = l3s * size.width,
            end = l3e * size.width
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


private fun DrawScope.drawProgressBit(
    color: Color,
    strokeWidth: Float,
    start: Float,
    end: Float
) {
    drawLine(
        start = Offset(
            x = start,
            y = size.height / 2F
        ),
        end = Offset(
            x = end,
            y = size.height / 2F
        ),
        color = color,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}


/**
 * Contains default values for a [IndeterminateProgressIndicator]
 *
 * Map values are labeled by convention: "l<number of line><e for end, start for start>". They contain keyframes,
 * manually scraped from the original oneui animation
 */
internal object IndeterminateProgressIndicatorDefaults {

    val height = 2.dp

    const val animDuration = 1933
    private fun frac(sec: Int): Int = (animDuration * (sec / 6.25F)).toInt()

    val l1s = mapOf(
        0F to frac(1),
        1F / 5F to frac(2),
        4F / 5F to frac(3),
        19F / 20F to frac(4),
        1F to frac(5)
    )
    val l1e = mapOf(
        1F / 3F to frac(1),
        4F / 5F to frac(2),
        9F / 10F to frac(3),
        1F to frac(4)
    )

    val l2s = mapOf(
        0F to frac(1),
        0F to frac(2),
        1F / 2F to frac(3),
        7F / 8F to frac(4),
        19F / 20F to frac(5),
        1F to frac(6)
    )
    val l2e = mapOf(
        0F to frac(1),
        1F / 7F to frac(2),
        3F / 5F to frac(3),
        9F / 10F to frac(4),
        1F to frac(5)
    )

    val l3s = mapOf(
        0F to frac(1),
        0F to frac(2),
        1F / 6F to frac(3),
        2F / 3F to frac(4),
        8F / 9F to frac(5),
    )
    val l3e = mapOf(
        0F to frac(1),
        0F to frac(2),
        2F / 5F to frac(3),
        4F / 5F to frac(4),
        9F / 10F to frac(5)
    )

    fun KeyframesSpec.KeyframesSpecConfig<Float>.toConf(map: Map<Float, Int>) = map
        .forEach { (value, millis) ->
            value at millis
        }

}

/**
 * Normal preview for a [IndeterminateProgressIndicator]
 */
@Preview
@Composable
private fun IndeterminateProgressIndicatorPreview() =
    OneUIPreview(title = "IndeterminateProgressIndicatpr") {
        IndeterminateProgressIndicator()
    }