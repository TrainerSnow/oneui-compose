package org.oneui.compose.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.base.iconColors
import org.oneui.compose.theme.OneUITheme

/**
 * Composable for a oneui-style bottom navigation bar. This is basically just a wrapper for a [Row], and acts as a primary or secondary navigation component
 *
 * @param modifier The [Modifier] to apply to the container
 * @param items The content of the BNB, preferably multiple [BottomNavigationBarItem]
 */
@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(
                space = BottomNavigationBarDefaults.spacing,
                alignment = Alignment.Start
            )
    ) {
        items()
    }
}

/**
 * Composable for a oneui-style item to be used inside a [BottomNavigationBar]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param label The string label to apply
 * @param icon The icon to apply
 * @param onClick The callback invoked when the item is clicked
 * @param interactionSource The [MutableInteractionSource]
 * @param colors The [BottomNavigationBarColors] to apply
 */
@Composable
fun BottomNavigationBarItem(
    modifier: Modifier = Modifier,
    label: String,
    icon: Icon,
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: BottomNavigationBarColors = bottomNavigationBarColors()
) {
    Column(
        modifier = modifier
            .widthIn(
                min = BottomNavigationBarDefaults.itemMinWidth,
                max = BottomNavigationBarDefaults.itemMaxWidth
            )
            .clip(
                shape = BottomNavigationBarDefaults.itemShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple
                ),
                role = Role.Tab,
                onClick = onClick
            )
            .padding(BottomNavigationBarDefaults.itemPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconView(
            modifier = Modifier
                .size(BottomNavigationBarDefaults.itemIconSize),
            icon = icon,
            colors = iconColors(
                tint = colors.icon
            )
        )
        Text(
            modifier = Modifier
                .padding(BottomNavigationBarDefaults.labelPadding),
            text = label,
            style = OneUITheme.types.bnbLabel
        )
    }
}

/**
 * Contains the colors used in a [BottomNavigationBar]
 */
data class BottomNavigationBarColors(

    val background: Color,

    val ripple: Color,

    val icon: Color

)

/**
 * Constructs the default [BottomNavigationBarColors]
 *
 * @param background The background color
 * @param ripple The ripple color for the onclick animation
 * @param icon The items icon tint
 */
@Composable
fun bottomNavigationBarColors(
    background: Color = OneUITheme.colors.bnbBackground,
    ripple: Color = OneUITheme.colors.bnbRipple,
    icon: Color = OneUITheme.colors.bnbIcon
): BottomNavigationBarColors = BottomNavigationBarColors(
    background = background,
    ripple = ripple,
    icon = icon
)

/**
 * Contains default values for a [BottomNavigationBar]
 */
object BottomNavigationBarDefaults {

    val padding = PaddingValues(
        horizontal = 5.dp
    )

    val spacing = 4.dp

    val height = 60.dp

    val itemMinWidth = 56.dp

    val itemMaxWidth = 96.dp

    val labelPadding = PaddingValues(
        vertical = 4.dp,
        horizontal = 6.dp
    )

    val itemPadding = PaddingValues(
        top = 4.dp,
        bottom = 2.dp
    )

    val itemIconSize = DpSize(
        width = 24.dp,
        height = 24.dp
    )

    val itemShape = RoundedCornerShape(
        size = 8.dp
    )
}