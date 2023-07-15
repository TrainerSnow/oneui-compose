package org.oneui.compose.preference.internal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.oneui.compose.preference.RelativeCard
import org.oneui.compose.theme.OneUITheme


/**
 * Composable for a oneui-style link-button, to be used inside a [RelativeCard]
 *
 * @param modifier The [Modifier] applied to the container
 * @param interactionSource The [MutableInteractionSource] used
 * @param colors The [RelativeCardLinkColors] to apply
 * @param onClick The callback invoked when the link is clicked
 * @param text The label to be shown inside the button, preferably a [Text]
 */
@Composable
fun RelativeCardLink(
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: RelativeCardLinkColors = relativeCardLinkColors(),
    onClick: () -> Unit,
    text: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RelativeCardLinkDefaults.shape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                role = Role.Button,
                onClick = onClick
            )
            .padding(RelativeCardLinkDefaults.padding)
    ) {
        ProvideTextStyle(OneUITheme.types.relativeCardLink) {
            text()
        }
    }
}


/**
 * Contains default values for a [RelativeCardLink]
 */
object RelativeCardLinkDefaults {

    val padding = PaddingValues(
        horizontal = 16.dp,
        vertical = 6.dp
    )

    val shape = RoundedCornerShape(
        size = 26.dp
    )

}


/**
 * Contains the colors used for a [RelativeCardLink]
 *
 * @property ripple
 */
data class RelativeCardLinkColors(

    val ripple: Color

)


/**
 * Constructs the default [RelativeCardLinkColors]
 *
 * @param ripple The color used for the onclick animation
 */
@Composable
fun relativeCardLinkColors(
    ripple: Color = OneUITheme.colors.seslRippleColor
): RelativeCardLinkColors = RelativeCardLinkColors(
    ripple = ripple
)