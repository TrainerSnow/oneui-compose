package org.oneui.compose.widgets.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha

@Composable
fun Button(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    padding: PaddingValues = ButtonDefaults.padding,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: (@Composable () -> Unit)? = null,
    colors: ButtonColors = defaultButtonColors()
) {
    Row(
        modifier = modifier
            .background(
                color = if (enabled) colors.background else colors.backgroundDisabled,
                shape = shape
            )
            .clip(
                shape = shape
            )
            .clickable(
                enabled = enabled,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                interactionSource = interactionSource,
                role = Role.Button
            ) { onClick?.let { it() } }
            .padding(padding),
        horizontalArrangement = Arrangement
            .spacedBy(ButtonDefaults.leadingIconSpacing, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let {
            leadingIcon()
        }
        label()
    }
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    label: String,
    padding: PaddingValues = ButtonDefaults.padding,
    textOverflow: TextOverflow = ButtonDefaults.textOverflow,
    maxLines: Int = ButtonDefaults.maxLines,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    textStyle: TextStyle = OneUITheme.types.button,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: (@Composable () -> Unit)? = null,
    colors: ButtonColors = defaultButtonColors()
) = Button(
    modifier,
    label = {
        BasicText(
            text = label,
            style = textStyle.copy(
                color = colors.text
            ).enabledAlpha(enabled),
            overflow = textOverflow,
            maxLines = maxLines
        )
    },
    padding, enabled, shape, onClick, interactionSource, leadingIcon, colors
)

/**
 * Stores the colors that are needed to define a button
 */
data class ButtonColors(

    /**
     * The color of the touch feedback on a button
     */
    val ripple: Color,

    /**
     * Background color
     */
    val background: Color,

    /**
     * Disabled background color
     */
    val backgroundDisabled: Color,

    /**
     * Color of the text label for
     */
    val text: Color
)

/**
 * Constructs the button colors for default variant
 */
@Composable
fun defaultButtonColors(
    ripple: Color = OneUITheme.colors.seslRippleColor,
    background: Color = OneUITheme.colors.buttonDefaultBackground,
    backgroundDisabled: Color = background.copy(
        alpha = ButtonDefaults.disabledAlpha
    ),
    text: Color = OneUITheme.colors.seslPrimaryTextColor
): ButtonColors = ButtonColors(
    ripple = ripple,
    background = background,
    backgroundDisabled = backgroundDisabled,
    text = text
)

/**
 * Constructs the button colors for colored variant
 */
@Composable
fun coloredButtonColors(
    ripple: Color = OneUITheme.colors.seslRippleColor,
    background: Color = OneUITheme.colors.seslPrimaryColor,
    backgroundDisabled: Color = background.copy(
        alpha = ButtonDefaults.disabledAlpha
    ),
    text: Color = OneUITheme.colors.seslWhite
): ButtonColors = ButtonColors(
    ripple = ripple,
    background = background,
    backgroundDisabled = backgroundDisabled,
    text = text
)

/**
 * Constructs the button colors for transparent variant
 */
@Composable
fun transparentButtonColors(
    ripple: Color = OneUITheme.colors.seslRippleColor,
    background: Color = Color.Transparent,
    backgroundDisabled: Color = Color.Transparent,
    text: Color = OneUITheme.colors.seslPrimaryTextColor
): ButtonColors = ButtonColors(
    ripple = ripple,
    background = background,
    backgroundDisabled = backgroundDisabled,
    text = text
)

/**
 * Stores default values for the button specs
 */
object ButtonDefaults {

    /**
     * Padding to be applied from the button to the text label
     */
    val padding = PaddingValues(
        horizontal = 16.dp,
        vertical = 8.dp
    )

    val leadingIconSpacing = 16.dp

    /**
     * How text overflow should be handled
     */
    val textOverflow = TextOverflow.Ellipsis

    /**
     * How many lines the text label can contain
     */
    const val maxLines = 2

    /**
     * The buttons shape
     */
    val shape = RoundedCornerShape(18.dp)

    /**
     * The alpha value for the background of disabled buttons
     */
    const val disabledAlpha: Float = 0.4F
}