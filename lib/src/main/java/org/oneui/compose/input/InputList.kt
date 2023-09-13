package org.oneui.compose.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import dev.oneuiproject.oneui.R
import org.oneui.compose.base.Icon
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.ListPosition
import org.oneui.compose.widgets.buttons.IconButton
import org.oneui.compose.widgets.buttons.iconButtonColors
import org.oneui.compose.widgets.menu.MenuItem
import org.oneui.compose.widgets.menu.PopupMenu


/**
 * Composable for an Input-List. This allows the user to select an amount of items from a given collection, shown in a popup.
 *
 * @param T The type of items to select
 * @param modifier The [Modifier] to apply
 * @param popupItems The items that are selectable to the user. Shown in a popup window.
 * @param showPopup Whether the popup should be visible
 * @param onPopupDismiss The callback invoked when the popup should be closed
 * @param selectedItems The items that are currently selected by the user, and shown in the list.
 * @param item The composable invoked for every selected item shown in the list.
 * @param popupItem The composable invoked for every item that is currently selectable in the popup. Preferably a [MenuItem]
 * @param searchText The composable for the input field the user can specify their search query. This is preferably an [InputFormField] with [ListPosition] last. A change
 *                  in this query should affect the [popupItems], filtering only those that match the given query.
 */
@Composable
fun <T> InputList(
    modifier: Modifier = Modifier,
    popupItems: List<T>,
    showPopup: Boolean,
    onPopupDismiss: () -> Unit,
    selectedItems: List<T>,
    item: @Composable (item: T, index: Int) -> Unit,
    popupItem: @Composable (T) -> Unit,
    searchText: @Composable (selectedItems: List<T>) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        val posProvider = object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                val spaceTop = anchorBounds.top
                val popupHeight = popupContentSize.height

                return if (popupHeight <= spaceTop) {
                    IntOffset(
                        x = 0,
                        y = anchorBounds.top - popupHeight
                    )
                } else IntOffset(
                    x = 0,
                    y = anchorBounds.bottom
                )
            }
        }

        selectedItems.forEachIndexed { index, item ->
            item(item, index)
        }
        Box {
            searchText(selectedItems)

            if (showPopup) {
                PopupMenu(
                    modifier = Modifier
                        .heightIn(max = 200.dp),
                    onDismissRequest = onPopupDismiss,
                    properties = PopupProperties(),
                    popupPositionProvider = posProvider
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        popupItems.forEach { item ->
                            popupItem(item)
                        }
                    }
                }
            }
        }
    }
}


/**
 * Overload for [InputList]. Takes care of most customizable composables.
 *
 * @param T The type of items to select
 * @param modifier The [Modifier] to apply
 * @param popupItems The items to currently show in the popup selection
 * @param showPopup Whether the popup should be shown
 * @param onPopupDismiss The callback invoked when the popup should be closed
 * @param selectedItems The items currently selected by the user
 * @param nameOf Converts a [T] into a string representation to be shown to the user
 * @param onItemRemove The callback invoked when a currently selected item is removed from the selection
 * @param onItemAdd The callback invoked when an item is clicked on in the popup and added to the selected items
 * @param searchQuery The search query currently entered in the search bar
 * @param onSearchQueryChange The callback invoked when the search query changes. Should affect the [popupItems], showing only those that match the query
 */
@Composable
fun <T> InputList(
    modifier: Modifier = Modifier,
    popupItems: List<T>,
    showPopup: Boolean,
    onPopupDismiss: () -> Unit,
    selectedItems: List<T>,
    nameOf: (T) -> String = { it.toString() },
    onItemRemove: (T) -> Unit,
    onItemAdd: (T) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        val posProvider = object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                val spaceTop = anchorBounds.top
                val popupHeight = popupContentSize.height

                return if (popupHeight <= spaceTop) {
                    IntOffset(
                        x = 0,
                        y = anchorBounds.top - popupHeight
                    )
                } else IntOffset(
                    x = 0,
                    y = anchorBounds.bottom
                )
            }
        }

        selectedItems.forEach { item ->
            InputListItem(
                modifier = Modifier
                    .fillMaxWidth(),
                item = item,
                nameOf = nameOf,
                onRemoveClick = onItemRemove
            )
        }
        Box {
            MenuListTextInput(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchQuery,
                onValueChange = onSearchQueryChange
            )

            if (showPopup) {
                PopupMenu(
                    modifier = Modifier
                        .heightIn(max = 200.dp),
                    onDismissRequest = onPopupDismiss,
                    properties = PopupProperties(),
                    popupPositionProvider = posProvider
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        popupItems.forEach { item ->
                            MenuListItem(
                                item = item,
                                nameOf = nameOf,
                                onClick = onItemAdd
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun <T> InputListItem(
    modifier: Modifier = Modifier,
    item: T,
    nameOf: (T) -> String,
    onRemoveClick: (T) -> Unit
) {
    val nameStyle = TextStyle(
        fontSize = 13.sp
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .width(InputListDefaults.extraStartPadding)
        )

        Text(
            style = nameStyle,
            text = nameOf(item)
        )

        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        IconButton(
            icon = Icon.Resource(R.drawable.ic_oui_minus),
            colors = iconButtonColors(
                tint = OneUITheme.colors.seslFunctionalRed
            ),
            onClick = { onRemoveClick(item) }
        )
    }
}

@Composable
private fun <T> MenuListItem(
    modifier: Modifier = Modifier,
    item: T,
    nameOf: (T) -> String,
    onClick: (T) -> Unit
) {
    MenuItem(
        modifier = modifier,
        label = nameOf(item),
        onClick = {
            onClick(item)
        }
    )
}

@Composable
private fun MenuListTextInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    icon: Icon? = null
) {
    InputFormField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = icon
    )
}

object InputListDefaults {

    val extraStartPadding = 18.dp

}