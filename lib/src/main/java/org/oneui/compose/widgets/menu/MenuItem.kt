package org.oneui.compose.widgets.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.IconView
import org.oneui.compose.base.iconColors
import org.oneui.compose.theme.OneUITheme


/**
 * Composable for a oneui-style menu item, to be used in combination with a [PopupMenu] in a spinner.
 *
 * TODO: Add support for start-icons
 *
 * @param modifier The modifier to apply
 * @param colors The [MenuItemColors] to apply
 * @param onSelect The callback for when an item is selected/clicked
 * @param label The string-label
 * @param selected Whether the item is selected. Only for spinners
 * @param labelStyle The [TextStyle] of the string-label
 * @param interactionSource The [MutableInteractionSource]
 * @param padding The [PaddingValues] to apply
 */
@Composable
fun SelectableMenuItem(
    modifier: Modifier = Modifier,
    colors: MenuItemColors = menuItemColors(),
    onSelect: (() -> Unit)? = null,
    enabled: Boolean = true,
    label: String,
    selected: Boolean = false,
    labelStyle: TextStyle = with(OneUITheme.types) {
        if (!enabled) menuLabelDisabled else if (selected) menuLabelSelected else menuLabel
    },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                role = Role.DropdownList,
                onClick = { onSelect?.let { it() } },
                enabled = enabled
            )
            .padding(MenuItemDefaults.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = labelStyle
        )
        Spacer(
            modifier = Modifier
                .width(MenuItemDefaults.iconSpacing)
        )
        IconView(
            icon = org.oneui.compose.base.Icon.Resource(
                dev.oneuiproject.oneui.R.drawable.ic_oui_selected
            ),
            colors = iconColors(
                tint = if (selected) labelStyle.color else Color.Transparent
            )
        )
    }
}


/**
 * Composable for a oneui-style menu item, to be used in combination with a [PopupMenu] in a menu.
 *
 * TODO: Add support for start-icons
 *
 * @param modifier The modifier to apply
 * @param label The string-label
 * @param onClick The callback for when an item is clicked
 * @param labelStyle The [TextStyle] of the string-label
 * @param interactionSource The [MutableInteractionSource]
 * @param colors The [MenuItemColors] to apply
 * @param padding The [PaddingValues] to apply
 */
@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    colors: MenuItemColors = menuItemColors(),
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    label: String,
    labelStyle: TextStyle = with(OneUITheme.types) {
        if (!enabled) menuLabelDisabled else menuLabel
    },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                role = Role.DropdownList,
                onClick = { onClick?.let { it() } },
                enabled = enabled
            )
            .padding(MenuItemDefaults.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = labelStyle
        )
    }
}

/**
 * Contains the colors that define a [SelectableMenuItem]
 */
data class MenuItemColors(

    val ripple: Color

)

/**
 * Constructs the default [MenuItemColors]
 *
 * @param ripple The ripple color when clicking an item
 * @return The [MenuItemColors]
 */
@Composable
fun menuItemColors(
    ripple: Color = OneUITheme.colors.seslRippleColor
): MenuItemColors = MenuItemColors(
    ripple = ripple
)

/**
 * Contains default values for a [SelectableMenuItem]
 */
object MenuItemDefaults {

    val padding = PaddingValues(
        top = 13.dp,
        end = 24.dp,
        bottom = 13.dp,
        start = 24.dp
    )

    val iconSpacing = 8.dp

}