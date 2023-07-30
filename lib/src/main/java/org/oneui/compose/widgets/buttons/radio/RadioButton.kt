/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.oneui.compose.widgets.buttons.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.oneui.compose.theme.OneUITheme
import kotlin.math.abs

/*
    Code taken from the material3 library for jetpack compose
    Changed:
        Merged top-level values and Tokens into RadioButtonDefaults
        Changed names of above named variables
        Renamed color variable names
        Removed equals and hashCode functions from colors object
        Moved default colors fun and changed default args to SeslTheme.* calls
        Add values from RadioButtonDefaults to @Composable call
        Replaced "selected" attribute by value parameters
        Added .toPx() calls on Dp variables
        Made RadioButtonColors a data class
        Added explicit ripple color
        Added outline shrink-and-expand animation
        Changed javadoc
        Added text label
        Added functionality for clicking on the text label
        Added new composable for RadioButtons in a list
 */

/**
 * Composable for a oneui-style radio button
 *
 * @param T The type the radio button is associated with
 * @param colors The [RadioButtonColors]
 * @param onClick The callback invoked when the [RadioButton] is clicked, together with its' value
 * @param enabled Whether this [RadioButton] is enabled
 * @param modifier The [Modifier] to be applied to the container
 * @param value The [T] value of this radio button
 * @param groupValue The [T] value that is associated with a group of [RadioButton]s
 * @param animDurationMillis The duration of the click-animation
 * @param interactionSource The [MutableInteractionSource]
 * @param label The label, preferably a [Text]
 */
@Composable
fun <T> RadioButton(
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = radioButtonColors(),
    onClick: ((T) -> Unit)? = null,
    enabled: Boolean = true,
    value: T,
    groupValue: T = value,
    animDurationMillis: Int = RadioButtonDefaults.animDurationMillis,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    labelSpacing: Dp = RadioButtonDefaults.labelSpacing,
    label: (@Composable () -> Unit)? = null
) {
    val selected = value == groupValue
    val scope = rememberCoroutineScope()

    val dotRadius = animateDpAsState(
        targetValue = if (selected) RadioButtonDefaults.dotSize / 2 else 0.dp,
        animationSpec = tween(durationMillis = animDurationMillis),
        label = "Radio button dot radius size in/out"
    )
    val animProgress = animateFloatAsState(
        targetValue = if (selected) 1F else 0F,
        animationSpec = tween(durationMillis = animDurationMillis),
        label = "Radio button outline radius increase/decrease"
    )
    val outlineSizeAnimated = outlineSize(
        animProgress.value,
        RadioButtonDefaults.outlineSize,
        RadioButtonDefaults.outlineSizeAnimDif
    )


    val radioColor = colors.radioColor(enabled, selected, animDurationMillis)

    val radioInteractionSource = remember { MutableInteractionSource() }
    val selectableModifier =
        if (onClick != null) {
            Modifier.selectable(
                selected = selected,
                onClick = { onClick(value) },
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = radioInteractionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = RadioButtonDefaults.touchSize / 2,
                    color = colors.ripple
                )
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
                role = Role.RadioButton,
                onClick = {
                    scope.launch {
                        val p = PressInteraction.Press(Offset.Zero)
                        radioInteractionSource.emit(p)
                        radioInteractionSource.emit(PressInteraction.Release(p))
                        onClick?.let { it(value) }
                    }
                }
            )
        /*.padding(RadioButtonDefaults.padding)*/,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Canvas(
            modifier = Modifier
                .then(selectableModifier)
                .requiredSize(RadioButtonDefaults.outlineSize)
        ) {
            drawCircle(
                radioColor.value,
                radius = (outlineSizeAnimated / 2).toPx() - (RadioButtonDefaults.strokeWidthPx / 2),
                style = Stroke(RadioButtonDefaults.strokeWidthPx)
            )
            if (dotRadius.value > 0.dp) {
                drawCircle(
                    radioColor.value,
                    dotRadius.value.toPx() - (RadioButtonDefaults.strokeWidthPx / 2),
                    style = Fill
                )
            }
        }
        Spacer(
            modifier = Modifier
                .width(labelSpacing)
        )
        label?.let {
            ProvideTextStyle(value = OneUITheme.types.radioLabel) {
                it()
            }
        }
    }
}


/**
 * Also a [RadioButton], wrapped in a box in order to have a much bigger touch target, as well as padding.
 *     Used in dialogs where the RadioButtons are the primary actions.
 *
 * @param T The type the radio button is associated with
 * @param colors The [RadioButtonColors]
 * @param onClick The callback invoked when the [RadioButton] is clicked, together with its' value
 * @param enabled Whether this [RadioButton] is enabled
 * @param modifier The [Modifier] to be applied to the container
 * @param value The [T] value of this radio button
 * @param groupValue The [T] value that is associated with a group of [RadioButton]s
 * @param animDurationMillis The duration of the click-animation
 * @param interactionSource The [MutableInteractionSource]
 * @param label The label, preferably a [Text]
 */
@Composable
fun <T> ListRadioButton(
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = radioButtonColors(),
    onClick: ((T) -> Unit)? = null,
    enabled: Boolean = true,
    value: T,
    groupValue: T = value,
    animDurationMillis: Int = RadioButtonDefaults.animDurationMillis,
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
                onClick = { onClick?.let { it(value) } }
            )
            .padding(RadioButtonDefaults.listPadding)
    ) {
        RadioButton(
            value = value,
            groupValue = groupValue,
            onClick = onClick,
            enabled = enabled,
            colors = colors,
            animDurationMillis = animDurationMillis,
            label = label,
            labelSpacing = RadioButtonDefaults.listLabelSpacing
        )
    }
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
 * Defaults used in [RadioButton].
 */
internal object RadioButtonDefaults {

    const val animDurationMillis = 300

    val padding = 2.dp
    val dotSize = 12.dp
    const val strokeWidthPx = 3F
    val outlineSize = 20.dp
    val outlineSizeAnimDif = 3.dp
    val touchSize = 40.0.dp
    val labelSpacing = 8.dp

    val listLabelSpacing = 22.dp
    val listPadding = PaddingValues(
        all = 16.dp
    )

    const val disabledAlphaValue = 0.38f

}

/**
 * Contains the colors that define a [RadioButton], depending on its' state
 */
@Immutable
data class RadioButtonColors(
    val color: Color,
    val unselectedColor: Color,
    val disabledColor: Color,
    val disabledUnselectedColor: Color,
    val ripple: Color
) {
    /**
     * Calculates the color of a [RadioButton] depending on its' state, enabled and selected
     *
     * @param enabled Whether the [RadioButton] is enabled
     * @param selected Whether the [RadioButton] is selected
     * @param animDuration Duration of the animation
     * @return The appropriate color, dependant on the animation and its' state
     */
    @Composable
    internal fun radioColor(enabled: Boolean, selected: Boolean, animDuration: Int): State<Color> {
        val target = when {
            enabled && selected -> color
            enabled && !selected -> unselectedColor
            !enabled && selected -> disabledColor
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
 * Creates the default [RadioButtonColors]
 *
 * @param color the color to use for the [RadioButton] by default.
 * @param unselectedColor the color to use for the [RadioButton] when unselected.
 * @param disabledColor the color to use for the [RadioButton] when disabled.
 * @param disabledUnselectedColor the color to use for the RadioButton when disabled and not
 * selected.
 */
@Composable
fun radioButtonColors(
    color: Color = OneUITheme.colors.seslPrimaryColor,
    unselectedColor: Color = OneUITheme.colors.seslControlNormalColor,
    disabledColor: Color = color.copy(
        alpha = RadioButtonDefaults.disabledAlphaValue
    ),
    disabledUnselectedColor: Color = unselectedColor.copy(
        alpha = RadioButtonDefaults.disabledAlphaValue
    ),
    ripple: Color = OneUITheme.colors.seslRippleColor
): RadioButtonColors = RadioButtonColors(
    color,
    unselectedColor,
    disabledColor,
    disabledUnselectedColor,
    ripple
)
