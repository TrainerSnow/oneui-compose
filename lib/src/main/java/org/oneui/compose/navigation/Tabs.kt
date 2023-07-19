package org.oneui.compose.navigation

import androidx.compose.foundation.Canvas
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


/**
 * Base composable for a group of multiple tabs, to be used as primary or secondary navigation utility.
 * Is actually only a wrapped [Row]
 *
 * @param modifier
 * @param tabs
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
 * @param text The text to be shown on the tab
 * @param selected Whether this tab is selected or not
 * @param interactionSource The [MutableInteractionSource]
 * @param colors The [TabsColors] to apply
 * @param onClick The callback invoked when the [TabItem] is clicked
 */
@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TabsColors = tabsColors(),
    onClick: () -> Unit
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
                onClick = onClick
            )
            .padding(TabsDefaults.itemPadding),
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
 * Composable for a custom tab item, that could contain e.g. an Icon to open an additional menu
 * Note: For proper usage, every [TabItem] should have a weight of 1, to be applied via [Modifier.weight()]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param interactionSource The [MutableInteractionSource]
 * @param colors The [TabsColors] to apply
 * @param onClick The callback invoked when the item is clicked
 * @param content The content to be put inside the item
 */
@Composable
fun CustomTabItem(
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TabsColors = tabsColors(),
    onClick: () -> Unit,
    content: @Composable () -> Unit
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
                onClick = onClick
            ),
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

    val itemIndicator: Color

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
    itemIndicator: Color = OneUITheme.colors.tabsItemIndicator
): TabsColors = TabsColors(
    itemRipple = itemRipple,
    itemIndicator = itemIndicator
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

    val itemPadding = PaddingValues(
        start = 10.dp,
        end = 10.dp,
        top = 14.dp,
        bottom = 14.dp - itemIndicatorSpacing - itemIndicatorHeight
    )

    val customButtonHeight = 43.dp

}