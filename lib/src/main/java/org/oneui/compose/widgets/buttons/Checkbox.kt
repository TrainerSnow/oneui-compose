package org.oneui.compose.widgets.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.widgets.buttons.radio.RadioButtonDefaults
import kotlinx.coroutines.launch
import kotlin.math.abs


/**
 * Composable for a oneui-style checkbox
 *
 * @param modifier The [Modifier] to apply to the container
 * @param enabled Whether the checkbox is enabled
 * @param checked Whether the checkbox is checked
 * @param onCheckedChange The callback for when the checkbox is checked/unchecked
 * @param colors The [CheckboxColors] to apply
 * @param interactionSource The [MutableInteractionSource]
 * @param label The composable for the label, preferably a [Text]
 */
@Composable
fun Checkbox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    colors: CheckboxColors = checkboxColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    labelSpacing: Dp = CheckboxDefaults.spacing,
    label: (@Composable () -> Unit)? = null
) {
    val scope = rememberCoroutineScope()

    val animProgress = animateFloatAsState(
        targetValue = if (checked) 1F else 0F,
        animationSpec = tween(durationMillis = CheckboxDefaults.animDurationMillis),
        label = "Animationprogress"
    )
    val outlineSizeAnimated = outlineSize(
        animProgress.value,
        CheckboxDefaults.outlineSize,
        CheckboxDefaults.outlineSizeAnimDif
    )

    val checkColor = colors.checkColor(enabled, checked, CheckboxDefaults.animDurationMillis)

    val checkInteractionSource = remember { MutableInteractionSource() }
    val selectableModifier =
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = checked,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = CheckboxDefaults.touchSize / 2,
                    color = colors.ripple
                ),
                enabled = enabled,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
        } else {
            Modifier
        }

    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Checkbox,
                onClick = {
                    scope.launch {
                        val p = PressInteraction.Press(Offset.Zero)
                        checkInteractionSource.emit(p)
                        checkInteractionSource.emit(PressInteraction.Release(p))
                        onCheckedChange?.let { it(!checked) }
                    }
                }
            )
            .padding(CheckboxDefaults.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Canvas(
            modifier = Modifier
                .then(selectableModifier)
                .requiredSize(CheckboxDefaults.outlineSize)
        ) {
            drawCircle(
                checkColor.value,
                radius = (outlineSizeAnimated / 2).toPx() - (CheckboxDefaults.strokeWidthPx / 2),
                style = Stroke(CheckboxDefaults.strokeWidthPx)
            )
            drawBackground(
                animProgress.value,
                checkColor.value,
                (outlineSizeAnimated / 2).toPx()
            )
            drawCheck(
                animProgress.value,
                Color.White,
                5F
            )
        }
        Spacer(
            modifier = Modifier
                .width(labelSpacing)
        )
        label?.let {
            ProvideTextStyle(value = OneUITheme.types.checkboxLabel) {
                it()
            }
        }
    }
}


/**
 * Also a [Checkbox], wrapped in a box in order to have a much bigger touch target, as well as padding.
 *  *     Used in dialogs where the Checkboxes are the primary actions.
 *
 * @param modifier The [Modifier] to apply to the container
 * @param enabled Whether the checkbox is enabled
 * @param checked Whether the checkbox is checked
 * @param onCheckedChange The callback for when the checkbox is checked/unchecked
 * @param colors The [CheckboxColors] to apply
 * @param interactionSource The [MutableInteractionSource]
 * @param label The composable for the label, preferably a [Text]
 */
@Composable
fun ListCheckbox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    colors: CheckboxColors = checkboxColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    label: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                enabled = enabled,
                role = Role.Button,
                onClick = { onCheckedChange?.let { it(!checked) } }
            )
            .padding(RadioButtonDefaults.listPadding)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = colors,
            label = label,
            labelSpacing = RadioButtonDefaults.listLabelSpacing
        )
    }
}

private fun DrawScope.drawCheck(
    progress: Float,
    color: Color,
    strokeWidth: Float
) {
    if (progress == 0F) return
    val cornerX = size.width * 0.45F
    val cornerY = size.height * 0.65F
    val leftX = size.width * 0.28F
    val leftY = size.height * 0.48F
    val rightX = size.width * 0.75F
    val rightY = size.height * 0.35F

    val corner = Offset(cornerX, cornerY)

    drawLine(
        color = color,
        strokeWidth = strokeWidth,
        start = corner,
        end = lineToProgress(
            corner,
            Offset(leftX, leftY),
            progress
        ),
        cap = StrokeCap.Round
    )

    drawLine(
        color = color,
        strokeWidth = strokeWidth,
        start = corner,
        end = lineToProgress(
            corner,
            Offset(rightX, rightY),
            progress
        ),
        cap = StrokeCap.Round
    )
}

private fun lineToProgress(
    p1: Offset,
    p2: Offset,
    progress: Float
): Offset {
    return Offset(
        x = p1.x + progress * (p2.x - p1.x),
        y = p1.y + progress * (p2.y - p1.y)
    )
}

private fun DrawScope.drawBackground(
    progress: Float,
    color: Color,
    radius: Float
) {
    val actualRadius = radius * progress

    drawCircle(
        color = color,
        radius = actualRadius
    )
}

private fun outlineSize(
    animProgress: Float,
    outlineSize: Dp,
    outlineSizeDif: Dp
): Dp = outlineSize - if (
    animProgress < 0.5F
) {
    val frac = animProgress / 0.5F
    outlineSizeDif * frac
} else if (animProgress == 0.5F) {
    outlineSizeDif
} else {
    val frac = abs(1 - animProgress) / 0.5F
    outlineSizeDif * frac
}


/**
 * Contains the colors that define a [Checkbox], depending on its' state
 */
@Immutable
data class CheckboxColors(
    val color: Color,
    val unselectedColor: Color,
    val disabledColor: Color,
    val disabledUnselectedColor: Color,
    val ripple: Color
) {
    /**
     * Calculates the color of a [Checkbox] depending on its' state, enabled and selected
     *
     * @param enabled Whether the [Checkbox] is enabled
     * @param checked Whether the [Checkbox] is selected
     * @param animDuration Duration of the animation
     * @return The appropriate color, dependant on the animation and its' state
     */
    @Composable
    internal fun checkColor(enabled: Boolean, checked: Boolean, animDuration: Int): State<Color> {
        val target = when {
            enabled && checked -> color
            enabled && !checked -> unselectedColor
            !enabled && checked -> disabledColor
            else -> disabledUnselectedColor
        }

        return if (enabled) {
            animateColorAsState(
                target,
                tween(durationMillis = animDuration),
                label = "Coloranimation"
            )
        } else {
            rememberUpdatedState(target)
        }
    }
}

/**
 * Creates the default [CheckboxColors]
 *
 * @param color the color to use for the [Checkbox] by default.
 * @param unselectedColor the color to use for the [Checkbox] when unselected.
 * @param disabledColor the color to use for the [Checkbox] when disabled.
 * @param disabledUnselectedColor the color to use for the [Checkbox] when disabled and not
 * selected.
 * @return the resulting [CheckboxColors] used for the [Checkbox]
 * TODO: Create variables for colors referenced in the theme
 */
@Composable
fun checkboxColors(
    color: Color = OneUITheme.colors.seslPrimaryColor,
    unselectedColor: Color = OneUITheme.colors.seslControlNormalColor,
    disabledColor: Color = color.copy(
        alpha = CheckboxDefaults.disabledAlphaValue
    ),
    disabledUnselectedColor: Color = unselectedColor.copy(
        alpha = CheckboxDefaults.disabledAlphaValue
    ),
    ripple: Color = OneUITheme.colors.seslRippleColor
): CheckboxColors = CheckboxColors(
    color,
    unselectedColor,
    disabledColor,
    disabledUnselectedColor,
    ripple
)

object CheckboxDefaults {

    const val animDurationMillis = 300
    val padding = 2.dp
    const val strokeWidthPx = 3F
    val outlineSize = 20.dp
    val outlineSizeAnimDif = 3.dp
    val touchSize = 40.0.dp
    val spacing = 8.dp

    val listLabelSpacing = 22.dp
    val listPadding = PaddingValues(
        all = 16.dp
    )

    const val disabledAlphaValue = 0.38f

}