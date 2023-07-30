package org.oneui.compose.widgets.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.mapRange
import org.oneui.compose.widgets.buttons.ActualSwitchColors.Companion.forConfig

/**
 * Composable for a oneui-style switch to toggle an option or on or off
 *
 * @param modifier The [Modifier] to apply to the container
 * @param colors The [SwitchColors] to apply
 * @param onSwitchedChange The callback invoked when the state changes
 * @param enabled Whether the switch is enabled
 * @param switched The state of the switch
 * @param interactionSource The [MutableInteractionSource]
 */
@Composable
fun Switch(
    modifier: Modifier = Modifier,
    colors: SwitchColors = switchColors(),
    onSwitchedChange: (Boolean) -> Unit = { },
    enabled: Boolean = true,
    switched: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    var isAnimating by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    val actualColors = colors.forConfig(enabled, switched)

    val spec = tween<Color>(SwitchDefaults.animDuration)
    val thumbColor by animateColorAsState(
        targetValue = actualColors.thumb,
        label = "Switch thumb color",
        animationSpec = spec
    )
    val track by animateColorAsState(
        targetValue = actualColors.track,
        label = "Switch track color",
        animationSpec = spec
    )
    val stroke by animateColorAsState(
        targetValue = actualColors.stroke,
        label = "Switch stroke color",
        animationSpec = spec
    )
    val progress by animateFloatAsState(
        targetValue = if (switched) 1F else 0F,
        label = "Switch progress",
        animationSpec = tween(SwitchDefaults.animDuration)
    )
    val rippleAlphaFactor by animateFloatAsState(
        targetValue = if (isAnimating) 1F else 0F,
        label = "Switch ripple alpha",
        animationSpec = tween(SwitchDefaults.animDuration)
    )
    val rippleRadius by animateDpAsState(
        targetValue = if (isAnimating) SwitchDefaults.rippleRadius else 0.dp,
        label = "Switch ripple radius",
        animationSpec = tween(SwitchDefaults.animDuration)
    )

    Canvas(
        modifier = modifier
            .width(
                SwitchDefaults.trackSize.width + (SwitchDefaults.thumbOvershoot * 2)
            )
            .height(
                SwitchDefaults.thumbSize.height
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                role = Role.Switch,
                onClick = {
                    onSwitchedChange(!switched)
                    isAnimating = true
                    scope.launch {
                        delay(SwitchDefaults.animDuration.toLong())
                        isAnimating = false
                    }
                }
            )
    ) {
        drawTrack(
            color = track,
            dpSize = SwitchDefaults.trackSize,
            cornerRadius = SwitchDefaults.trackCornerRadius,
            spacingStart = SwitchDefaults.thumbOvershoot
        )

        val thumbPos = thumbPosition(
            overshoot = SwitchDefaults.thumbOvershoot,
            progress = progress,
            radius = SwitchDefaults.thumbSize.width / 2,
            size = size,
            density = this //DrawScope is a Density
        )
        drawThumb(
            color = thumbColor,
            radius = SwitchDefaults.thumbSize.width / 2,
            position = thumbPos
        )

        drawOutline(
            color = stroke,
            radius = (SwitchDefaults.thumbSize.width + SwitchDefaults.strokeWidth) / 2,
            position = thumbPos,
            strokeWidth = SwitchDefaults.strokeWidth
        )
        drawRipple(
            color = colors.ripple.copy(
                alpha = colors.ripple.alpha * rippleAlphaFactor
            ),
            radius = rippleRadius,
            position = thumbPos
        )
    }
}

private fun DrawScope.drawTrack(
    color: Color,
    dpSize: DpSize,
    cornerRadius: Dp,
    spacingStart: Dp
) {
    val dif = size.height - dpSize.height.toPx()
    drawRoundRect(
        topLeft = Offset(
            x = spacingStart.toPx(),
            y = dif / 2
        ),
        color = color,
        cornerRadius = CornerRadius(
            x = cornerRadius.toPx(),
            y = cornerRadius.toPx()
        ),
        size = dpSize.toSize()
    )
}

private fun thumbPosition(
    overshoot: Dp,
    progress: Float,
    radius: Dp,
    size: Size,
    density: Density
): Offset {
    val yCenter = size.height / 2
    val width = size.width
    val start =
        with(density) { SwitchDefaults.thumbOvershoot.toPx() + (radius.toPx() - overshoot.toPx()) }
    val end = width - start
    val pos = mapRange(
        value = progress,
        origStart = 0F,
        origEnd = 1F,
        targetStart = start,
        targetEnd = end
    )
    return Offset(
        x = pos,
        y = yCenter
    )
}

private fun DrawScope.drawThumb(
    color: Color,
    radius: Dp,
    position: Offset
) {
    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = position
    )
}

private fun DrawScope.drawOutline(
    color: Color,
    radius: Dp,
    position: Offset,
    strokeWidth: Dp
) {
    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = position,
        style = Stroke(
            width = strokeWidth.toPx()
        )
    )
}

private fun DrawScope.drawRipple(
    color: Color,
    radius: Dp,
    position: Offset
) {
    drawCircle(
        color = color,
        radius = radius.toPx(),
        center = position
    )
}


/**
 * Contains colors for a [Switch]
 */
data class SwitchColors(

    val thumb: Color,

    val thumbOff: Color,

    val thumbDisabled: Color,

    val thumbOffDisabled: Color,

    val track: Color,

    val trackOff: Color,

    val trackDisabled: Color,

    val trackOffDisabled: Color,

    val stroke: Color,

    val strokeOff: Color,

    val strokeDisabled: Color,

    val strokeOffDisabled: Color,

    val ripple: Color

)


/**
 * Contains only the colors for a [Switch] that are needed, decided upon its configuration
 */
data class ActualSwitchColors(

    val thumb: Color,

    val track: Color,

    val stroke: Color

) {
    companion object {
        @Suppress("KotlinConstantConditions")
        fun SwitchColors.forConfig(
            enabled: Boolean,
            switched: Boolean
        ): ActualSwitchColors = if (
            enabled && switched
        ) ActualSwitchColors(
            thumb = thumb,
            track = track,
            stroke = stroke
        ) else if (
            !enabled && switched
        ) ActualSwitchColors(
            thumb = thumbDisabled,
            track = trackDisabled,
            stroke = strokeDisabled
        ) else if (
            enabled && !switched
        ) ActualSwitchColors(
            thumb = thumbOff,
            track = trackOff,
            stroke = strokeOff
        ) else ActualSwitchColors(
            thumb = thumbOffDisabled,
            track = trackOffDisabled,
            stroke = strokeOffDisabled
        )
    }
}


/**
 * Constructs the default [SwitchColors]
 */
@Composable
fun switchColors(
    thumb: Color = OneUITheme.colors.seslSwitchThumbOnColor,
    thumbOff: Color = OneUITheme.colors.seslSwitchThumbOffColor,
    thumbDisabled: Color = OneUITheme.colors.seslSwitchThumbOnDisabledColor,
    thumbOffDisabled: Color = OneUITheme.colors.seslSwitchThumbOffDisabledColor,
    track: Color = OneUITheme.colors.seslPrimaryColor,
    trackOff: Color = OneUITheme.colors.seslSwitchTrackOffColor,
    trackDisabled: Color = OneUITheme.colors.seslPrimaryColor.copy(alpha = 0.4F),
    trackOffDisabled: Color = OneUITheme.colors.seslSwitchTrackOffDisabledColor,
    stroke: Color = OneUITheme.colors.seslPrimaryColor,
    strokeOff: Color = OneUITheme.colors.seslSwitchThumbOffStrokeColor,
    strokeDisabled: Color = OneUITheme.colors.seslPrimaryColor.copy(alpha = 0.4F),
    strokeOffDisabled: Color = OneUITheme.colors.seslSwitchThumbOffDisabledStrokeColor,
    ripple: Color = OneUITheme.colors.seslRippleColor
): SwitchColors = SwitchColors(
    thumb,
    thumbOff,
    thumbDisabled,
    thumbOffDisabled,
    track,
    trackOff,
    trackDisabled,
    trackOffDisabled,
    stroke,
    strokeOff,
    strokeDisabled,
    strokeOffDisabled,
    ripple
)


/**
 * Contains default values for a [Switch]
 */
object SwitchDefaults {

    const val animDuration = 150

    val strokeWidth = 1.dp

    val thumbSize = DpSize(
        width = 22.dp,
        height = 22.dp
    )

    val thumbOvershoot = 2.dp

    val trackSize = DpSize(
        width = 35.dp,
        height = 18.5.dp
    )
    val trackCornerRadius = 9.25.dp

    val rippleRadius = 20.dp
}