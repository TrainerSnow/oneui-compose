package org.oneui.compose.navigation.rail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.theme.OneUITheme
import dev.oneuiproject.oneui.R as IconR

/**
 * Overload for [NavigationRailItem] that takes strings and icons
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param icon The icon to show at the start of the item
 * @param label The label to show in the center of the item
 * @param endLabel The label to optionally show at the end of the item
 * @param onClick The callback invoked when the item is selected
 * @param selected Whether the item is currently selected
 * @param progress The opening progress of the [NavigationRail]
 */
@Composable
fun NavigationRailItem(
    modifier: Modifier = Modifier,
    icon: Icon,
    label: String,
    endLabel: String? = null,
    onClick: () -> Unit,
    selected: Boolean = false,
    progress: Float
) = NavigationRailItem(
    modifier,
    icon = { IconView(icon = icon) },
    label = { Text(text = label) },
    endLabel = endLabel?.let { { Text(text = it) } },
    onClick = onClick,
    selected = selected,
    progress = progress
)

/**
 * Composable for a oneui-style Navigation rail item, to be used a a child inside a [NavigationRail].
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param icon The composable to show at the start of the item, preferably an [IconView]
 * @param label The label to show in the center of the item, preferably a [Text]
 * @param endLabel The label to optionally show at the end of the item, preferably a [Text]
 * @param onClick The callback invoked when the item is selected
 * @param selected Whether the item is currently selected
 * @param progress The opening progress of the [NavigationRail]
 */
@Composable
fun NavigationRailItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    endLabel: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
    selected: Boolean = false,
    progress: Float
) {
    Box(
        modifier = Modifier
            .height(NavigationRailItemDefaults.openedSize.height)
    ) {
        if (progress == 0F) {
            ClosedNavigationRailItem(
                modifier = modifier,
                icon = icon,
                selected = selected,
                onClick = onClick
            )
        } else {
            OpenedNavigationRailItem(
                modifier = modifier,
                icon = icon,
                label = label,
                endLabel = endLabel,
                selected = selected,
                onClick = onClick,
                progress = progress
            )
        }
    }
}

@Composable
private fun ClosedNavigationRailItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val selMod = if (selected) Modifier
        .background(OneUITheme.colors.seslRippleColor)
    else Modifier
    Box(
        modifier = Modifier
            .padding(NavigationRailItemDefaults.closedMargin),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .size(NavigationRailItemDefaults.closedSize)
                .clip(NavigationRailItemDefaults.shape)
                .then(selMod)
                .clickable(
                    enabled = true,
                    role = Role.Button,
                    indication = rememberRipple(),
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(NavigationRailItemDefaults.closedPadding),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(NavigationRailItemDefaults.closedIconSize),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
        }
    }
}

@Composable
private fun OpenedNavigationRailItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    endLabel: (@Composable () -> Unit)? = null,
    selected: Boolean = false,
    progress: Float,
    onClick: () -> Unit
) {
    val selMod = if (selected) Modifier
        .background(OneUITheme.colors.seslRippleColor.copy(OneUITheme.colors.seslRippleColor.alpha * progress))
    else Modifier

    Box(
        modifier = modifier
            .padding(NavigationRailItemDefaults.openedMargin)
    ) {
        Row(
            modifier = Modifier
                .size(NavigationRailItemDefaults.openedSize)
                .clip(NavigationRailItemDefaults.shape)
                .then(selMod)
                .clickable(
                    enabled = progress == 1F,
                    role = Role.Button,
                    indication = rememberRipple(),
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(NavigationRailItemDefaults.openedPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val titleStyle = OneUITheme.types.run {
                if (selected) navigationRailItemLabelSelected
                else navigationRailItemLabel
            }.let {
                it.copy(
                    color = it.color.copy(it.color.alpha * progress)
                )
            }
            val labelEndStyle = OneUITheme.types.navigationRailItemEndLabel.let {
                it.copy(color = it.color.copy(it.color.alpha * progress))
            }

            Box(
                modifier = Modifier
                    .size(NavigationRailItemDefaults.openedIconSize),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(
                modifier = Modifier.width(NavigationRailItemDefaults.openedIconTextSpacing)
            )
            ProvideTextStyle(
                titleStyle,
                content = label
            )
            Spacer(
                modifier = Modifier
                    .weight(1F)
            )
            endLabel?.let {
                ProvideTextStyle(
                    labelEndStyle,
                    content = endLabel
                )
            }
        }
    }
}

/**
 * Contains the colors used in a [NavigationRailItem]
 */
data class NavigationRailItemColors(

    val ripple: Color

)

/**
 * Constructs the default [NavigationRailItemColors]
 *
 * @param ripple The color for the ripple animation
 */
@Composable
fun navigationRailItemColors(
    ripple: Color = OneUITheme.colors.seslRippleColor
) = NavigationRailItemColors(ripple)


/**
 * Contains default values for a [NavigationRailItem]
 */
object NavigationRailItemDefaults {

    val closedSize = DpSize(46.dp, 46.dp)
    val closedPadding = PaddingValues(11.dp)
    val closedMargin = PaddingValues(vertical = 5.5.dp)
    val closedIconSize = DpSize(24.dp, 24.dp)

    val shape = RoundedCornerShape(8.dp)

    val openedSize = DpSize(
        width = NavigationRailDefaults.openedSize - 24.dp,
        height = 46.dp + 11.dp
    )
    val openedPadding = PaddingValues(
        horizontal = 11.dp,
        vertical = 15.5.dp
    )
    val openedMargin = PaddingValues()
    val openedIconSize = DpSize(24.dp, 24.dp)
    val openedIconTextSpacing = 12.dp

}


@Preview
@Composable
private fun NavigationRailPreview() {
    NavigationRail(
        modifier = Modifier.fillMaxSize(),
        railHeader = { },
        railContent = {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                NavigationRailItem(
                    label = { Text("Folder") },
                    endLabel = { Text("514") },
                    onClick = { },
                    progress = it,
                    icon = { IconView(icon = Icon.Resource(IconR.drawable.ic_oui_folder_outline)) }
                )
                NavigationRailItem(
                    label = { Text("Contact") },
                    endLabel = { Text("321") },
                    onClick = { },
                    progress = it,
                    icon = { IconView(icon = Icon.Resource(IconR.drawable.ic_oui_contact_outline)) },
                    selected = true
                )
                NavigationRailItem(
                    label = { Text("Delete") },
                    endLabel = { Text("274") },
                    onClick = { },
                    progress = it,
                    icon = { IconView(icon = Icon.Resource(IconR.drawable.ic_oui_delete_outline)) }
                )
            }
        },
        content = { }
    )
}