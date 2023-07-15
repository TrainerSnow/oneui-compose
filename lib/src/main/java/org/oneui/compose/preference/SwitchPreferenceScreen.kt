package org.oneui.compose.preference

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.widgets.buttons.Switch
import kotlinx.coroutines.launch


/**
 * Composable for a oneui-style SwitchPreferenceScreen.
 *     This is a SwitchPreference, which allows for a nested preferences screen, preferably
 *     toggled by [onClick].
 *
 * @param modifier The [Modifier] to apply to the container
 * @param title The title of the preference
 * @param icon The icon to apply to the preference
 * @param summary The summary of the preference
 * @param clickInteractionSource The [MutableInteractionSource] for the Preference
 * @param switchInteractionSource The [MutableInteractionSource] for the Switch
 * @param onClick The callback invoked when the preference is clicked. This should optimally
 *     open the nested preference screen
 * @param onSwitch The callback invoked when the switch is clicked.
 * @param switched Whether this preference is on or off
 * @param colors The [SwitchPreferenceScreenColors]
 */
@Composable
fun SwitchPreferenceScreen(
    modifier: Modifier = Modifier,
    title: String,
    icon: Icon? = null,
    summary: String? = null,
    clickInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    switchInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    onSwitch: (Boolean) -> Unit,
    switched: Boolean = false,
    colors: SwitchPreferenceScreenColors = switchPreferenceScreenColors()
) {
    val scope = rememberCoroutineScope()

    BasePreference(
        modifier = modifier,
        title = {
            Text(
                text = title
            )
        },
        icon = icon,
        summary = summary?.let {
            {
                Text(
                    text = summary
                )
            }
        },
        interactionSource = clickInteractionSource,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    role = Role.Switch,
                    onClick = {
                        scope.launch {
                            val p = PressInteraction.Press(Offset.Zero)
                            switchInteractionSource.emit(p)
                            switchInteractionSource.emit(PressInteraction.Release(p))
                        }
                    }
                )
        ) {
            SwitchPreferenceScreenDivider(
                modifier = modifier
                    .height(SwitchPreferenceScreenDefaults.dividerHeight),
                color = colors.switchDivider,
                strokeWidth = OneUITheme.dimensions.preferenceSwitchScreenDividerWidth
            )
            Switch(
                modifier = modifier
                    .padding(SwitchPreferenceScreenDefaults.switchPadding),
                switched = switched,
                onSwitchedChange = onSwitch,
                interactionSource = switchInteractionSource
            )
        }
    }
}

/**
 * Divider between the preference title & summary and the switch itself
 */
@Composable
internal fun SwitchPreferenceScreenDivider(
    modifier: Modifier,
    color: Color,
    strokeWidth: Float
) {
    Canvas(
        modifier = modifier
    ) {
        val xCenter = size.width / 2

        drawLine(
            start = Offset(
                x = xCenter,
                y = 0F
            ),
            end = Offset(
                x = xCenter,
                y = size.height
            ),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

/**
 * Contains default values for a [SwitchPreferenceScreen]
 */
object SwitchPreferenceScreenDefaults {

    val dividerHeight = 22.dp

    val switchPadding = PaddingValues(
        start = 9.dp
    )

}

/**
 * Contains the colors for a [SwitchPreferenceScreen]
 */
data class SwitchPreferenceScreenColors(

    val switchDivider: Color

)


/**
 * Constructs the default [SwitchPreferenceScreenColors]
 *
 * @param switchDivider The color of the divider
 */
@Composable
fun switchPreferenceScreenColors(
    switchDivider: Color = OneUITheme.colors.seslSwitchDividerColor
): SwitchPreferenceScreenColors = SwitchPreferenceScreenColors(
    switchDivider = switchDivider
)