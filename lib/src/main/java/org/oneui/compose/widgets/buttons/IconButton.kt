package org.oneui.compose.widgets.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.base.iconColors
import org.oneui.compose.theme.OneUITheme

object IconButtonDefaults {

    val rippleRadius = 640.dp

    val padding = PaddingValues(
        horizontal = 12.dp,
        vertical = 12.dp
    )

}

data class IconButtonColors(

    val tint: Color,

    val ripple: Color

)

@Composable
fun iconButtonColors(
    tint: Color = OneUITheme.colors.seslPrimaryTextColor,
    ripple: Color = OneUITheme.colors.seslRippleColor
): IconButtonColors = IconButtonColors(
    tint = tint,
    ripple = ripple
)

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    rippleRadius: Dp = IconButtonDefaults.rippleRadius,
    padding: PaddingValues = IconButtonDefaults.padding,
    colors: IconButtonColors = iconButtonColors(),
    onClick: (() -> Unit)? = null,
    icon: Icon,
    contentDescription: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                onClick = { onClick?.let { it() } },
                indication = rememberRipple(
                    color = colors.ripple,
                    radius = rippleRadius,
                    bounded = false
                )
            )
            .padding(padding)
    ) {
        IconView(
            icon = icon,
            colors = iconColors(
                tint = colors.tint
            ),
            contentDescription = contentDescription
        )
    }
}