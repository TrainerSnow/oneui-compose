package org.oneui.compose.widgets.box

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme

/**
 * Contains the colors that define a [RoundedCornerBox]
 *
 * @property background
 * @property ripple
 */
data class RoundedCornerBoxColors(

    val background: Color,

    val ripple: Color

)


/**
 * Constructs the default [RoundedCornerBoxColors]
 *
 * @param background The background color of the box
 * @param ripple The animation color for the click animation
 * @return
 */
@Composable
fun roundedCornerBoxColors(
    background: Color = OneUITheme.colors.roundedCornerBoxBackground,
    ripple: Color = OneUITheme.colors.seslRippleColor
): RoundedCornerBoxColors = RoundedCornerBoxColors(
    background = background,
    ripple = ripple
)

/**
 * Contains default values to define a [RoundedCornerBox]
 *
 * @constructor Create empty Rounded corner box defaults
 */
object RoundedCornerBoxDefaults {

    val radius: Dp = 26.dp

    val padding: PaddingValues = PaddingValues(
        horizontal = 24.dp,
        vertical = 12.dp
    )

}


/**
 * Composable for a oneui-style rounded corner surface
 *
 * @param modifier The modifier to be applied to the container
 * @param cornerRadius The radius of the corners
 * @param colors The [RoundedCornerBoxColors] to use
 * @param onClick The callback to invoke when clicked. Also triggers the animation.
 * @param interactionSource The [MutableInteractionSource] to use
 * @param contentAlignment The [Alignment] to align the content by
 * @param padding The padding to use
 * @param content The content of the box
 */
@Composable
fun RoundedCornerBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = RoundedCornerBoxDefaults.radius,
    colors: RoundedCornerBoxColors = roundedCornerBoxColors(),
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentAlignment: Alignment = Alignment.Center,
    padding: PaddingValues = RoundedCornerBoxDefaults.padding,
    content: @Composable BoxScope.() -> Unit
) {
    val clickMod = onClick?.let {
        Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                onClick = it
            )
    } ?: Modifier

    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    size = cornerRadius
                )
            )
            .background(
                color = colors.background
            )
            .then(clickMod)
            .padding(padding),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}