package org.oneui.compose.preference

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.widgets.HorizontalSeekbar


/**
 * Composable for a oneui-style preference, that allows the user to select a number in a range
 *
 * @param modifier The [Modifier] applied to the container
 * @param title The title of the preference
 * @param summary The description of the preference
 * @param icon The icon to apply to the preference
 * @param value The currently selected value
 * @param onValueChange The callback invoked when the current value is changed
 */
@Composable
fun SeekbarPreference(
    modifier: Modifier = Modifier,
    title: String,
    summary: String? = null,
    icon: Icon? = null,
    value: Float = 0.5F,
    onValueChange: ((Float) -> Unit)? = null,
    enabled: Boolean = true,
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
        enabled = enabled,
        contentLocation = ContentLocation.Bottom
    ) {
        HorizontalSeekbar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SeekbarPreferenceDefaults.seekbarPadding),
            value = value,
            enabled = enabled,
            onValueChange = { value -> onValueChange?.let { it(value) } }
        )
    }
}


/**
 * Contains default values for a [SeekbarPreference]
 */
object SeekbarPreferenceDefaults {

    val seekbarPadding = PaddingValues(
        vertical = 15.dp
    )

}