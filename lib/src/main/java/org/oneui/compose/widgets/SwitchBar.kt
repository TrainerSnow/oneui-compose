package org.oneui.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha
import org.oneui.compose.widgets.buttons.Switch


/**
 * Composable for a oneui-style SwitchBar. This is used when a [Switch] acts as a primary action
 *     of a screen, and toggles a major function on or off, e.g. Wifi.
 *
 * @param modifier The [Modifier] applied to the container
 * @param colors The [SwitchBarColors] to apply
 * @param onSwitchedChange The callback invoked when the [switched] state is changed
 * @param enabled Whether this [SwitchBar] is enabled and interactable
 * @param switched Whether this switchbar is switched on
 * @param label The string label. Has platform default values
 * @param interactionSource The [MutableInteractionSource] to apply
 */
@Composable
fun SwitchBar(
    modifier: Modifier = Modifier,
    colors: SwitchBarColors = switchBarColors(),
    onSwitchedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    switched: Boolean = false,
    label: String = stringResource(
        id = if (switched)
            R.string.sesl_switchbar_label_on
        else
            R.string.sesl_switchbar_label_off
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SwitchBarDefaults.height)
            .clip(SwitchBarDefaults.shape)
            .background(
                color = if (switched) colors.background else colors.backgroundOff
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                role = Role.Button,
                onClick = { onSwitchedChange?.let { it(!switched) } }
            )
            .padding(
                horizontal = OneUITheme.dimensions.switchBarMarginHorizontal
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProvideTextStyle(
            with(OneUITheme.types) {
                if (switched) switchBarLabel else switchBarLabelOff
            }.enabledAlpha(enabled)
        ) {
            Text(
                text = label
            )
        }
        Switch(
            enabled = enabled,
            switched = switched,
            onSwitchedChange = { onSwitchedChange?.let { it(!switched) } }
        )
    }
}


/**
 * Contains default values for a [SwitchBar]
 */
object SwitchBarDefaults {

    val shape = RoundedCornerShape(29.dp)

    val height = 64.dp
}


/**
 * Contains the colors that a [SwitchBar] utilizes
 */
data class SwitchBarColors(

    val ripple: Color,

    val background: Color,

    val backgroundOff: Color

)


/**
 * Creates the default [SwitchBarColors]
 *
 * @param ripple The ripple color for the onClick animation
 * @param background The background color of the composable
 * @param backgroundOff The background color for when it is toggled off
 */
@Composable
fun switchBarColors(
    ripple: Color = OneUITheme.colors.seslRippleColor,
    background: Color = OneUITheme.colors.seslSwitchbarOnBackgroundColor,
    backgroundOff: Color = OneUITheme.colors.seslSwitchbarOffBackgroundColor
): SwitchBarColors = SwitchBarColors(
    ripple = ripple,
    background = background,
    backgroundOff = backgroundOff
)