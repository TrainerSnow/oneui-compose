package org.oneui.compose.preference

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.oneui.compose.base.Icon
import org.oneui.compose.widgets.buttons.Checkbox


/**
 * Composable for a oneui-style preference that allows the user to enable/disable a setting.
 *
 * @param modifier The [Modifier] to apply to the container
 * @param icon The [Icon] to apply
 * @param title The string title of the preference
 * @param summary The description of this preference
 * @param checked Whether this preference is on/off
 * @param onCheckedChange The callback invoked when this preferences value is changed
 * @param interactionSource The [MutableInteractionSource] to apply
 */
@Composable
fun CheckboxPreference(
    modifier: Modifier = Modifier,
    icon: Icon? = null,
    title: String,
    summary: String? = null,
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BasePreference(
        modifier = modifier,
        icon = icon,
        title = {
            Text(
                text = title
            )
        },
        summary = summary?.let {
            {
                Text(
                    text = summary
                )
            }
        },
        onClick = { onCheckedChange?.let { it(!checked) } },
        interactionSource = interactionSource
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange?.let { it(!checked) } }
        )
    }
}