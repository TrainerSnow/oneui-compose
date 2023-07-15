package org.oneui.compose.preference.internal

import androidx.compose.foundation.background
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
import org.oneui.compose.theme.OneUITheme

@Composable
fun TipsCardPreferenceButton(
    modifier: Modifier = Modifier,
    colors: TipsCardPreferenceButtonColors = tipsCardPreferenceButtonColors(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(colors.background)
            .clip(TipsCardPreferenceButtonDefaults.shape)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                role = Role.Button,
                onClick = onClick
            )
            .padding(TipsCardPreferenceButtonDefaults.padding)
    ) {
        ProvideTextStyle(OneUITheme.types.tipsCardPreferenceButtonLabel) {
            label()
        }
    }
}

object TipsCardPreferenceButtonDefaults {

    val padding = PaddingValues(
        horizontal = 12.dp,
        vertical = 4.dp
    )

    val shape = RoundedCornerShape(
        size = 26.dp
    )

}

data class TipsCardPreferenceButtonColors(

    val ripple: Color,

    val background: Color

)

@Composable
fun tipsCardPreferenceButtonColors(
    ripple: Color = OneUITheme.colors.seslRippleColor,
    background: Color = Color.Transparent
): TipsCardPreferenceButtonColors = TipsCardPreferenceButtonColors(
    ripple = ripple,
    background = background
)