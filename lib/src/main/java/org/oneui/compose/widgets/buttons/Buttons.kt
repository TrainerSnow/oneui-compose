package org.oneui.compose.widgets.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import org.oneui.compose.theme.SeslTheme

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
    ripple: Color = SeslTheme.colors.seslRippleColor,
    background: Color = SeslTheme.colors.buttonDefaultBackground,
    backgroundDisabled: Color = background.copy(
        alpha = ButtonDefaults.disabledAlpha
    ),
    text: Color = SeslTheme.colors.buttonDefaultText
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
    ripple: Color = SeslTheme.colors.seslRippleColor,
    background: Color = SeslTheme.colors.buttonColoredBackground,
    backgroundDisabled: Color = background.copy(
        alpha = ButtonDefaults.disabledAlpha
    ),
    text: Color = SeslTheme.colors.buttonColoredText
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
    ripple: Color = SeslTheme.colors.seslRippleColor,
    background: Color = SeslTheme.colors.buttonTransparentBackground,
    backgroundDisabled: Color = SeslTheme.colors.buttonTransparentBackground,
    text: Color = SeslTheme.colors.buttonTransparentText
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

/**
 * Composable for a oui-style colored button
 *
 * @param modifier The [Modifier] to be applied to this button
 * @param label The string-label shown on this button
 * @param padding The padding to be applied
 * @param textOverflow The strategy on how to deal with overflowing text
 * @param maxLines The maximum amount of lines of text on this button
 * @param enabled Whether this button is enabled or not
 * @param shape The buttons [Shape]
 * @param textStyle The [TextStyle] of the string-label
 * @param onClick The onclick callback
 * @param interactionSource
 * @param colors The buttons color config
 */
@Composable
fun ColoredButton(
    modifier: Modifier = Modifier,
    label: String,
    padding: PaddingValues = ButtonDefaults.padding,
    textOverflow: TextOverflow = ButtonDefaults.textOverflow,
    maxLines: Int = ButtonDefaults.maxLines,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    textStyle: TextStyle = SeslTheme.types.button,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: ButtonColors = coloredButtonColors(),
) = BaseButton(
    modifier = modifier,
    label = label,
    padding = padding,
    textOverflow = textOverflow,
    maxLines = maxLines,
    enabled = enabled,
    shape = shape,
    textStyle = textStyle,
    onClick = onClick,
    interactionSource = interactionSource,
    backgroundColor = colors.background,
    disabledBackgroundColor = colors.backgroundDisabled,
    rippleColor = colors.ripple,
    textColor = colors.text
)

/**
 * Composable for a oui-style transparent button
 *
 * @param modifier The [Modifier] to be applied to this button
 * @param label The string-label shown on this button
 * @param padding The padding to be applied
 * @param textOverflow The strategy on how to deal with overflowing text
 * @param maxLines The maximum amount of lines of text on this button
 * @param enabled Whether this button is enabled or not
 * @param shape The buttons [Shape]
 * @param textStyle The [TextStyle] of the string-label
 * @param onClick The onclick callback
 * @param interactionSource
 * @param colors The buttons color config
 */
@Composable
fun TransparentButton(
    modifier: Modifier = Modifier,
    label: String,
    padding: PaddingValues = ButtonDefaults.padding,
    textOverflow: TextOverflow = ButtonDefaults.textOverflow,
    maxLines: Int = ButtonDefaults.maxLines,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    textStyle: TextStyle = SeslTheme.types.button,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: ButtonColors = transparentButtonColors()
) = BaseButton(
    modifier = modifier,
    label = label,
    padding = padding,
    textOverflow = textOverflow,
    maxLines = maxLines,
    enabled = enabled,
    shape = shape,
    textStyle = textStyle,
    onClick = onClick,
    interactionSource = interactionSource,
    backgroundColor = colors.background,
    disabledBackgroundColor = colors.backgroundDisabled,
    rippleColor = colors.ripple,
    textColor = colors.text
)

/**
 * Composable for a oui-style default button
 *
 * @param modifier The [Modifier] to be applied to this button
 * @param label The string-label shown on this button
 * @param padding The padding to be applied
 * @param textOverflow The strategy on how to deal with overflowing text
 * @param maxLines The maximum amount of lines of text on this button
 * @param enabled Whether this button is enabled or not
 * @param shape The buttons [Shape]
 * @param textStyle The [TextStyle] of the string-label
 * @param onClick The onclick callback
 * @param interactionSource
 * @param colors The buttons color config
 */
@Composable
fun Button(
    modifier: Modifier = Modifier,
    label: String,
    padding: PaddingValues = ButtonDefaults.padding,
    textOverflow: TextOverflow = ButtonDefaults.textOverflow,
    maxLines: Int = ButtonDefaults.maxLines,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    textStyle: TextStyle = SeslTheme.types.button,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: ButtonColors = defaultButtonColors()
) = BaseButton(
    modifier = modifier,
    label = label,
    padding = padding,
    textOverflow = textOverflow,
    maxLines = maxLines,
    enabled = enabled,
    shape = shape,
    textStyle = textStyle,
    onClick = onClick,
    interactionSource = interactionSource,
    backgroundColor = colors.background,
    disabledBackgroundColor = colors.backgroundDisabled,
    rippleColor = colors.ripple,
    textColor = colors.text
)

@Composable
internal fun BaseButton(
    modifier: Modifier,
    label: String,
    padding: PaddingValues,
    textOverflow: TextOverflow,
    maxLines: Int,
    enabled: Boolean,
    shape: Shape,
    textStyle: TextStyle,
    onClick: (() -> Unit)?,
    interactionSource: MutableInteractionSource,
    backgroundColor: Color,
    disabledBackgroundColor: Color,
    rippleColor: Color,
    textColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = if (enabled) backgroundColor else disabledBackgroundColor,
                shape = shape
            )
            .clip(
                shape = shape
            )
            .clickable(
                enabled = enabled,
                indication = rememberRipple(
                    color = rippleColor
                ),
                interactionSource = interactionSource,
                role = Role.Button
            ) { onClick?.let { it() } }
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = label,
            style = textStyle.copy(
                color = textColor
            ),
            overflow = textOverflow,
            maxLines = maxLines
        )
    }
}