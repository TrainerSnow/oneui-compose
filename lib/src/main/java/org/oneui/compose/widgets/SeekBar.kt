package org.oneui.compose.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha

/**
 * Composable for a seekbar to select a float value in the range of [0;1]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param value The currently selected value
 * @param onValueChange The callback invoked when the value changes
 * @param colors The [SeekBarColors] to apply
 */
@Composable
fun HorizontalSeekbar(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    colors: SeekBarColors = seekBarColors(),
    enabled: Boolean = true
) {
    var isDragging by remember { mutableStateOf(false) }
    val rippleAlphaFactor by animateFloatAsState(
        targetValue = if (isDragging) 1F else 0F,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Thumb ripple fade in/out"
    )
    val thumbRadiusExtra by animateDpAsState(
        targetValue = if (isDragging) SeekBarDefaults.animThumbRadiusExtra else 0.dp,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Thumb radius increase on drag"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .enabledAlpha(enabled)
            .pointerInput(Unit) {
                if(enabled) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDragEnd = {
                            isDragging = false
                        }
                    ) { change, _ ->
                        val newValue = change.position.x / size.width
                        onValueChange(newValue.coerceIn(0f, 1f))
                        change.consume()
                    }
                }
            }
    ) {
        horizontalTrack(
            color = colors.trackColor,
            activeColor = colors.color,
            height = SeekBarDefaults.trackHeight.toPx(),
            progress = value
        )
        drawThumbHorizontal(
            color = colors.color,
            rippleColor = colors.ripple.copy(
                alpha = colors.ripple.alpha * rippleAlphaFactor
            ),
            radius = (SeekBarDefaults.thumbRadius + thumbRadiusExtra).toPx(),
            rippleRadius = SeekBarDefaults.thumbRippleRadius.toPx(),
            progress = value
        )
    }
}

/**
 * Composable for a vertical seekbar to select a float value in the range of [0;1]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param value The currently selected value
 * @param onValueChange The callback invoked when the value changes
 * @param colors The [SeekBarColors] to apply
 */
@Composable
fun VerticalSeekbar(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    colors: SeekBarColors = seekBarColors(),
    enabled: Boolean = true
) {
    var isDragging by remember { mutableStateOf(false) }
    val rippleAlphaFactor by animateFloatAsState(
        targetValue = if (isDragging) 1F else 0F,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Thumb ripple fade in/out"
    )
    val thumbRadiusExtra by animateDpAsState(
        targetValue = if (isDragging) SeekBarDefaults.animThumbRadiusExtra else 0.dp,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Thumb radius increase on drag"
    )

    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentWidth()
            .padding(8.dp)
            .enabledAlpha(enabled)
            .pointerInput(Unit) {
                if(enabled) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDragEnd = {
                            isDragging = false
                        }
                    ) { change, _ ->
                        val newValue = (size.height - change.position.y) / size.height
                        onValueChange(newValue.coerceIn(0f, 1f))
                        change.consume()
                    }
                }
            }
    ) {
        verticalTrack(
            color = colors.trackColor,
            activeColor = colors.color,
            height = SeekBarDefaults.trackHeight.toPx(),
            progress = value
        )
        drawThumbVertical(
            color = colors.color,
            rippleColor = colors.ripple.copy(
                alpha = colors.ripple.alpha * rippleAlphaFactor
            ),
            radius = (SeekBarDefaults.thumbRadius + thumbRadiusExtra).toPx(),
            rippleRadius = SeekBarDefaults.thumbRippleRadius.toPx(),
            progress = value
        )
    }
}

/**
 * Composable for a vertical expanding seekbar to select a float value in the range of [0;1]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param value The currently selected value
 * @param onValueChange The callback invoked when the value changes
 * @param colors The [SeekBarColors] to apply
 */
@Composable
fun VerticalSeekbarExpanding(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    colors: SeekBarColors = seekBarColors(),
    enabled: Boolean = true
) {
    var isDragging by remember { mutableStateOf(false) }
    val thumbRadius by animateDpAsState(
        targetValue = if (isDragging) 0.dp else SeekBarDefaults.thumbRadius,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Thumb radius decrease/increase on drag"
    )
    val trackHeightExtra by animateDpAsState(
        targetValue = if (isDragging) SeekBarDefaults.trackHeightExpand - SeekBarDefaults.trackHeight else 0.dp,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Track width increase/decrease on drag"
    )

    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentWidth()
            .padding(8.dp)
            .enabledAlpha(enabled)
            .pointerInput(Unit) {
                if(enabled) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDragEnd = {
                            isDragging = false
                        }
                    ) { change, _ ->
                        val newValue = (size.height - change.position.y) / size.height
                        onValueChange(newValue.coerceIn(0f, 1f))
                        change.consume()
                    }
                }
            }
    ) {
        verticalTrack(
            color = colors.trackColor,
            activeColor = colors.color,
            height = (SeekBarDefaults.trackHeight + trackHeightExtra).toPx(),
            progress = value
        )
        drawThumbVertical(
            color = colors.color,
            radius = thumbRadius.toPx(),
            progress = value
        )
    }
}

/**
 * Composable for a expanding seekbar to select a float value in the range of [0;1]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param value The currently selected value
 * @param onValueChange The callback invoked when the value changes
 * @param colors The [SeekBarColors] to apply
 */
@Composable
fun HorizontalSeekbarExpanding(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    colors: SeekBarColors = seekBarColors(),
    enabled: Boolean = true
) {
    var isDragging by remember { mutableStateOf(false) }
    val thumbRadius by animateDpAsState(
        targetValue = if (isDragging) 0.dp else SeekBarDefaults.thumbRadius,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Thumb radius decrease/increase on drag"
    )
    val trackHeightExtra by animateDpAsState(
        targetValue = if (isDragging) SeekBarDefaults.trackHeightExpand - SeekBarDefaults.trackHeight else 0.dp,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Track width increase/decrease on drag"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .enabledAlpha(enabled)
            .pointerInput(Unit) {
                if(enabled) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDragEnd = {
                            isDragging = false
                        }
                    ) { change, _ ->
                        val newValue = change.position.x / size.width
                        onValueChange(newValue.coerceIn(0f, 1f))
                        change.consume()
                    }
                }
            }
    ) {
        horizontalTrack(
            color = colors.trackColor,
            activeColor = colors.color,
            height = (SeekBarDefaults.trackHeight + trackHeightExtra).toPx(),
            progress = value
        )
        drawThumbHorizontal(
            color = colors.color,
            radius = thumbRadius.toPx(),
            progress = value
        )
    }
}

/**
 * Composable for a seekbar with warning effect to select a float value in the range of [0;1]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param value The currently selected value
 * @param warningAt Where the seekbar should change its colo to warning
 * @param onValueChange The callback invoked when the value changes
 * @param colors The [SeekBarColors] to apply
 */
@Composable
fun HorizontalSeekbarWarning(
    modifier: Modifier = Modifier,
    value: Float,
    warningAt: Float = 0.5F,
    onValueChange: (Float) -> Unit,
    colors: SeekBarColors = seekBarColors(),
    enabled: Boolean = true
) {
    var isDragging by remember { mutableStateOf(false) }
    val rippleAlphaFactor by animateFloatAsState(
        targetValue = if (isDragging) 1F else 0F,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Thumb ripple fade in/out"
    )
    val thumbRadiusExtra by animateDpAsState(
        targetValue = if (isDragging) SeekBarDefaults.animThumbRadiusExtra else 0.dp,
        animationSpec = tween(SeekBarDefaults.animDuration),
        label = "Thumb radius increase on drag"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .enabledAlpha(enabled)
            .pointerInput(Unit) {
                if(enabled) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDragEnd = {
                            isDragging = false
                        }
                    ) { change, _ ->
                        val newValue = change.position.x / size.width
                        onValueChange(newValue.coerceIn(0f, 1f))
                        change.consume()
                    }
                }
            }
    ) {
        val inWarningRange = value > warningAt

        horizontalTrack(
            color = colors.trackColor,
            activeColor = if (inWarningRange) colors.warningColor else colors.color,
            warningColor = colors.trackWarningColor,
            height = SeekBarDefaults.trackHeight.toPx(),
            progress = value,
            warningAt = warningAt
        )
        drawThumbHorizontal(
            color = if (inWarningRange) colors.warningColor else colors.color,
            rippleColor = colors.ripple.copy(
                alpha = colors.ripple.alpha * rippleAlphaFactor
            ),
            radius = (SeekBarDefaults.thumbRadius + thumbRadiusExtra).toPx(),
            rippleRadius = SeekBarDefaults.thumbRippleRadius.toPx(),
            progress = value
        )
    }
}

private fun DrawScope.drawThumbHorizontal(
    color: Color,
    rippleColor: Color? = null,
    radius: Float,
    rippleRadius: Float? = null,
    progress: Float
) {
    val yCenter = size.height / 2
    val measuredX = progress * size.width

    drawCircle(
        color = color,
        radius = radius,
        center = Offset(
            x = measuredX,
            y = yCenter
        )
    )
    if (rippleColor != null && rippleRadius != null) {
        drawCircle(
            color = rippleColor,
            radius = rippleRadius,
            center = Offset(
                x = measuredX,
                y = yCenter
            )
        )
    }
}

private fun DrawScope.drawThumbVertical(
    color: Color,
    rippleColor: Color? = null,
    radius: Float,
    rippleRadius: Float? = null,
    progress: Float
) {
    val xCenter = size.width / 2
    val measuredY = (1 - progress) * size.height

    drawCircle(
        color = color,
        radius = radius,
        center = Offset(
            x = xCenter,
            y = measuredY
        )
    )
    if (rippleColor != null && rippleRadius != null) {
        drawCircle(
            color = rippleColor,
            radius = rippleRadius,
            center = Offset(
                x = xCenter,
                y = measuredY
            )
        )
    }
}

private fun DrawScope.horizontalTrack(
    color: Color,
    activeColor: Color,
    warningColor: Color? = null,
    height: Float,
    progress: Float,
    warningAt: Float? = null
) {
    val yCenter = size.height / 2

    val start = Offset(
        x = 0F,
        y = yCenter
    )
    val active = Offset(
        x = size.width * progress,
        y = yCenter
    )
    val end = Offset(
        x = size.width,
        y = yCenter
    )
    val activeMiddle = Offset(
        x = active.x / 2,
        y = active.y
    )

    //Track
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = height,
        cap = StrokeCap.Round
    )
    if (warningColor != null && warningAt != null) {
        warning(
            warningAt, warningColor, height
        )
    }

    drawLine(
        color = activeColor,
        start = start,
        end = activeMiddle,
        strokeWidth = height,
        cap = StrokeCap.Round
    )
    drawLine(
        color = activeColor,
        start = activeMiddle,
        end = active,
        strokeWidth = height,
        cap = StrokeCap.Butt
    )
}

private fun DrawScope.warning(
    warningAt: Float,
    warningColor: Color,
    height: Float
) {
    val yCenter = size.height / 2

    val start = Offset(
        x = size.width * warningAt,
        y = yCenter
    )
    val end = Offset(
        x = size.width,
        y = yCenter
    )
    val middle = Offset(
        x = size.width - (((1 - warningAt) / 2) * size.width),
        y = yCenter
    )

    drawLine(
        color = warningColor,
        start = start,
        end = middle,
        strokeWidth = height,
        cap = StrokeCap.Butt
    )
    drawLine(
        color = warningColor,
        start = middle,
        end = end,
        strokeWidth = height,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.verticalTrack(
    color: Color,
    activeColor: Color,
    height: Float,
    progress: Float
) {
    val xCenter = size.width / 2

    val start = Offset(
        x = xCenter,
        y = size.height
    )
    val active = Offset(
        x = xCenter,
        y = size.height * (1 - progress)
    )
    val end = Offset(
        x = xCenter,
        y = 0F
    )
    val activeMiddle = Offset(
        x = xCenter,
        y = size.height * (1 - (progress / 2))
    )

    //Track
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = height,
        cap = StrokeCap.Round
    )

    drawLine(
        color = activeColor,
        start = start,
        end = activeMiddle,
        strokeWidth = height,
        cap = StrokeCap.Round
    )
    drawLine(
        color = activeColor,
        start = activeMiddle,
        end = active,
        strokeWidth = height,
        cap = StrokeCap.Butt
    )
}

/**
 * Contains colors needed for a [HorizontalSeekbar] and its variants

 */
data class SeekBarColors(

    val color: Color,

    val ripple: Color,

    val trackColor: Color,

    val warningColor: Color,

    val trackWarningColor: Color

)

/**
 * Constructs the default [SeekBarColors]
 *
 * @param color The color of the progress and its handle
 * @param ripple The color of the drag animation
 * @param trackColor The color of the track
 * @param warningColor The color when warning mode is enabled
 * @param trackWarningColor The color of the track when warning mode is enabled
 */
@Composable
fun seekBarColors(
    color: Color = OneUITheme.colors.seslSeekbarControlColorActivated,
    ripple: Color = OneUITheme.colors.seslRippleColor,
    trackColor: Color = OneUITheme.colors.seslSeekbarOverlapColorDefault,
    warningColor: Color = OneUITheme.colors.seslSeekbarOverlapColorActivated,
    trackWarningColor: Color = OneUITheme.colors.seslSeekbarOverlapColorActivated
): SeekBarColors = SeekBarColors(
    color = color,
    ripple = ripple,
    trackColor = trackColor,
    warningColor = warningColor,
    trackWarningColor = trackWarningColor
)

/**
 * Contains default values for the seekbar variants
 */
object SeekBarDefaults {

    val thumbRadius = 10.dp

    val animThumbRadiusExtra = 1.dp

    val thumbRippleRadius = 16.dp

    val trackHeight = 4.dp

    val trackHeightExpand = 17.dp

    val padding = PaddingValues(
        horizontal = 16.dp
    )

    const val animDuration = 200

}
