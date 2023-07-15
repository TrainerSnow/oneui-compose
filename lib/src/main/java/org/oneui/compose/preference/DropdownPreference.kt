package org.oneui.compose.preference

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.oneui.compose.base.Icon
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.widgets.menu.PopupMenu
import org.oneui.compose.widgets.menu.SelectableMenuItem


/**
 * Composable for a oneui-style preference that lets the user select one of many choices through
 *     a dropdown menu. Alternative for [SingleSelectPreference]
 *
 * @param T The type associated with the [items]
 * @param modifier The [Modifier] applied to the container
 * @param title The title of the preference
 * @param icon The icon applied to the preference
 * @param interactionSource The [MutableInteractionSource] applied
 * @param item The currently selected item
 * @param items All possible items
 * @param nameFor A conversion lambda to convert a [T] to its' string-representation
 * @param onItemSelected The callback invoked when the user made its' choice
 *
 * @see SingleSelectPreference
 */
@Composable
fun <T> DropdownPreference(
    modifier: Modifier = Modifier,
    title: String,
    icon: Icon? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    item: T,
    items: List<T>,
    nameFor: (T) -> String,
    onItemSelected: (T) -> Unit
) {
    assert(item in items) { "The selected item must be in the possible items" }

    var showDropdown by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        BasePreference(
            title = {
                Text(
                    text = title
                )
            },
            summary = {
                Text(
                    text = nameFor(item),
                    style = OneUITheme.types.preferenceSummary.copy(
                        color = OneUITheme.colors.seslPrimaryColor
                    )
                )
            },
            icon = icon,
            onClick = { showDropdown = true },
            interactionSource = interactionSource
        )

        if (showDropdown) {
            PopupMenu(
                onDismissRequest = { showDropdown = false }
            ) {
                items.forEach {
                    SelectableMenuItem(
                        label = nameFor(it),
                        onSelect = {
                            onItemSelected(it)
                            showDropdown = false
                        },
                        selected = it == item
                    )
                }
            }
        }
    }
}