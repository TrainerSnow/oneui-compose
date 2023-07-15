package org.oneui.compose.preference.misc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.text.TextSeparator


/**
 * Composable for a oneui-style preference category.
 * This is basically a [RoundedCornerBox], whose paddings was applied to the children so the click animation is constant.
 *
 * TODO: Make [preferences] a single composable, not a list
 *
 * @param modifier The [Modifier] to be applied
 * @param preferences A list of composables, that will be displayed as a list. Can't be a single composable,
 *     as the divider needs to be dynamically added. Not optimal. Better with a custom layout.
 * @param title The composable for the title of the [PreferenceCategory], preferably a [TextSeparator] or a [Text]
 */
@Composable
fun PreferenceCategory(
    modifier: Modifier = Modifier,
    preferences: List<@Composable () -> Unit>,
    title: (@Composable () -> Unit)? = null
) {
    title?.let { it() }
    RoundedCornerBox(
        modifier = modifier
            .fillMaxWidth(),
        padding = PaddingValues(0.dp)
    ) {
        Column {
            preferences.forEachIndexed { index, composable ->
                val doDivider = index != preferences.indices.last

                composable()
                if (doDivider) {
                    PreferenceListDivider()
                }
            }
        }
    }
}