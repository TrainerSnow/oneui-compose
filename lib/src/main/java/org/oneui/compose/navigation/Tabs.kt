package org.oneui.compose.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha


/**
 * Base composable for a group of multiple tabs, to be used as primary or secondary navigation utility.
 * Is actually only a wrapped [Row]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param tabs The content, preferably multiple [TabItem]s or [CustomTabItem]s
 */
@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    tabs: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        tabs()
    }
}


/**
 * Composable for a one-ui style [TabItem], to be used in a [Tabs] row.
 * Note: For proper usage, every [TabItem] should have a weight of 1, to be applied via [Modifier.weight()]
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param colors The [TabsColors] to apply
 * @param onClick The callback invoked when the [TabItem] is clicked
 * @param text The text to be shown on the tab
 * @param selected Whether this tab is selected or not
 * @param interactionSource The [MutableInteractionSource]
 */
@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    colors: TabsColors = tabsColors(),
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    selected: Boolean,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Column(
        modifier = modifier
            .clip(TabsDefaults.itemShape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.itemRipple
                ),
                role = Role.Tab,
                onClick = onClick,
                enabled = enabled
            )
            .padding(TabsDefaults.itemPadding)
            .enabledAlpha(enabled),
        verticalArrangement = Arrangement
            .spacedBy(
                TabsDefaults.itemIndicatorSpacing,
                alignment = Alignment.CenterVertically
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var textWidth by remember {
            mutableIntStateOf(0)
        }

        Text(
            modifier = Modifier //We need to measure the width of the text at runtime
                .onGloballyPositioned {
                    textWidth = it.size.width
                },
            text = text,
            style = with(OneUITheme.types) { if (selected) tabItemSelected else tabItem }
        )


        Box(
            modifier = Modifier
                .width(with(LocalDensity.current) { textWidth.toDp() })
                .height(TabsDefaults.itemIndicatorHeight)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawLine(
                    color = if (selected) colors.itemIndicator else Color.Transparent,
                    start = Offset(
                        x = 0F,
                        y = size.height / 2F
                    ),
                    end = Offset(
                        x = size.width,
                        y = size.height / 2F
                    ),
                    strokeWidth = TabsDefaults.itemIndicatorHeight.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}


/**
 * Composable for a one-ui style [SubTabItem], to be used in a [Tabs] row. Unlike [TabItem], this is smaller and meant for secondary navigation.
 * Note: For proper usage, every [SubTabItem] should have a weight of 1, to be applied via [Modifier.weight()]
 *
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param colors The [TabsColors] to apply
 * @param onClick The callback invoked when the [TabItem] is clicked
 * @param text The text to be shown on the tab
 * @param selected Whether this tab is selected or not
 * @param interactionSource The [MutableInteractionSource]
 */
@Composable
fun SubTabItem(
    modifier: Modifier = Modifier,
    colors: TabsColors = tabsColors(),
    onClick: () -> Unit,
    text: String,
    selected: Boolean,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = modifier
            .clip(TabsDefaults.itemSubShape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.itemSubRipple
                ),
                role = Role.Tab,
                onClick = onClick,
                enabled = enabled
            )
            .background(
                color = if (selected) colors.itemSubIndicator else Color.Transparent
            )
            .padding(TabsDefaults.itemSubPadding)
            .enabledAlpha(enabled),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = with(OneUITheme.types) { if (selected) tabItemSelected else tabItem }
        )
    }
}


/**
 * Composable for a custom tab item, that could contain e.g. an Icon to open an additional menu
 * Note: For proper usage, every [TabItem] should have a weight of 1, to be applied via [Modifier.weight()]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param colors The [TabsColors] to apply
 * @param onClick The callback invoked when the item is clicked
 * @param interactionSource The [MutableInteractionSource]
 * @param content The content to be put inside the item
 */
@Composable
fun CustomTabItem(
    modifier: Modifier = Modifier,
    colors: TabsColors = tabsColors(),
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .height(TabsDefaults.customButtonHeight)
            .clip(TabsDefaults.itemShape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.itemRipple
                ),
                role = Role.Tab,
                onClick = onClick,
                enabled = enabled
            )
            .enabledAlpha(enabled),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


/**
 * Contains the colors needed to constructs a [TabItem]
 */
data class TabsColors(

    val itemRipple: Color,

    val itemIndicator: Color,

    val itemSubIndicator: Color,

    val itemSubRipple: Color

)


/**
 * Constructs the default [TabsColors]
 *
 * @param itemRipple Ripple color for the onclick-animation
 * @param itemIndicator Color of the selected item indicator
 */
@Composable
fun tabsColors(
    itemRipple: Color = OneUITheme.colors.seslRippleColor,
    itemIndicator: Color = OneUITheme.colors.seslTablayoutMainTabIndicatorColor,
    itemSubIndicator: Color = OneUITheme.colors.seslTablayoutSubtabIndicatorBackground,
    itemSubRipple: Color = OneUITheme.colors.seslRippleColor
): TabsColors = TabsColors(
    itemRipple = itemRipple,
    itemIndicator = itemIndicator,
    itemSubIndicator = itemSubIndicator,
    itemSubRipple = itemSubRipple
)

/**
 * Contains default values for the [TabItem]
 */
object TabsDefaults {

    val itemIndicatorHeight = 2.dp

    val itemIndicatorSpacing = 1.dp

    val itemShape = RoundedCornerShape(
        size = 26.dp
    )

    val itemSubShape = RoundedCornerShape(
        size = 23.dp
    )

    val itemPadding = PaddingValues(
        start = 10.dp,
        end = 10.dp,
        top = 14.dp,
        bottom = 14.dp - itemIndicatorSpacing - itemIndicatorHeight
    )

    val itemSubPadding = PaddingValues(
        all = 8.dp
    )

    val customButtonHeight = 43.dp

}