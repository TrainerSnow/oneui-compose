package org.oneui.compose.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.buttons.TransparentButton
import org.oneui.compose.widgets.menu.PopupMenu
import org.oneui.compose.widgets.menu.SelectableMenuItem
import org.oneui.compose.widgets.text.TextSeparator

@Composable
fun DropdownShowcase(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
) {
    val items = listOf("Item One", "Item Two", "Item Three", "Item Four")
    var shown by remember {
        mutableStateOf(false)
    }
    var selected by remember {
        mutableStateOf<String?>(null)
    }

    TextSeparator(text = "Dropdown")
    RoundedCornerBox(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TransparentButton(
            label = "Show",
            onClick = {
                shown = !shown
            }
        )
    }

    if (shown) {
        PopupMenu(
            onDismissRequest = { shown = false }
        ) {
            items.forEach {
                SelectableMenuItem(
                    label = it,
                    onSelect = {
                        selected = it
                        shown = false
                    },
                    selected = selected == it
                )
            }
        }
    }
}