package org.oneui.compose.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha


/**
 * Composable for a button on a oneui-style dialog.
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param colors The [DialogButtonColors] to apply
 * @param onClick The callback invoked on click
 * @param label The text-composable to be used as a label
 * @param threeButton Whether the button is in three button configuration. Affects the padding and font size
 * @param interactionSource The [MutableInteractionSource] to use
 */
@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    colors: DialogButtonColors = dialogButtonColors(),
    onClick: (() -> Unit)? = null,
    label: @Composable () -> Unit,
    enabled: Boolean = true,
    threeButton: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = modifier
            .defaultMinSize(
                minWidth = DialogButtonDefaults.midWidth,
                minHeight = DialogButtonDefaults.minHeight
            )
            .clip(
                shape = DialogButtonDefaults.shape
            )
            .clickable(
                enabled = enabled,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                interactionSource = interactionSource,
                role = Role.Button,
                onClick = { onClick?.let { it() } }
            )
            .enabledAlpha(enabled)
            .padding(
                if (threeButton)
                    DialogButtonDefaults.paddingThreeButtons
                else
                    DialogButtonDefaults.padding
            ),
        contentAlignment = Alignment.Center
    ) {

        ProvideTextStyle(
            if (threeButton) OneUITheme.types.dialogButtonLabelThreeButtons
            else
                OneUITheme.types.dialogButtonLabel
        ) {
            label()
        }

    }
}


/**
 * Overload for [DialogButton] that takes a string as label
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param label The text to be used as a label
 * @param onClick The callback invoked on click
 * @param colors The [DialogButtonColors] to apply
 * @param interactionSource The [MutableInteractionSource] to use
 * @param threeButton Whether the button is in three button configuration. Affects the padding and font size
 */
@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    colors: DialogButtonColors = dialogButtonColors(),
    onClick: (() -> Unit)? = null,
    label: String,
    enabled: Boolean = true,
    threeButton: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    DialogButton(
        modifier = modifier,
        colors = colors,
        onClick = onClick,
        label = {
            Text(
                text = label,
                overflow = TextOverflow.Ellipsis
            )
        },
        enabled = enabled,
        threeButton = threeButton,
        interactionSource = interactionSource
    )
}


/**
 * Contains the colors used for a [DialogButton]
 */
data class DialogButtonColors(

    val ripple: Color

)


/**
 * Constructs the default colors for a [DialogButton]
 *
 * @param ripple The ripple color used for the onclick animation
 */
@Composable
fun dialogButtonColors(
    ripple: Color = OneUITheme.colors.seslRippleColor
): DialogButtonColors = DialogButtonColors(
    ripple = ripple
)


/**
 * Contains default values to construct a [DialogButton]
 */
object DialogButtonDefaults {

    val shape = RoundedCornerShape(
        size = 26.dp
    )

    val midWidth = 42.dp

    val minHeight = 36.dp

    val padding = PaddingValues(
        horizontal = 18.dp,
        vertical = 4.dp
    )

    val paddingThreeButtons = PaddingValues(
        horizontal = 15.dp,
        vertical = 4.dp
    )

}