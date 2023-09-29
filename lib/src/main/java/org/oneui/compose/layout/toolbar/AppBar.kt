package org.oneui.compose.layout.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.LayoutDirection
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.theme.locals.ProvideBackgroundColor
import org.oneui.compose.widgets.buttons.IconButtonDefaults

/**
 * Composable for a oui-style AppBar
 * TODO: Add preview image
 *
 * @param modifier The modifier to be applied to the container
 * @param colors The [AppBarColors]
 * @param windowInsets The [WindowInsets] to apply to the layout
 * @param title The title of the appbar, preferably a [Text]
 * @param startAction The action icon on the start side
 * @param actions The other actions on the end side
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OUIAppBar(
    modifier: Modifier = Modifier,
    colors: AppBarColors = appBarColors(),
    windowInsets: WindowInsets = WindowInsets.statusBars,
    title: @Composable () -> Unit,
    startAction: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null
) {
    val textStyle = OneUITheme.types.appbarTitleCollapsed

    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colors.background
            ),
        windowInsets = windowInsets,
        title = {
            ProvideTextStyle(textStyle) {
                ProvideBackgroundColor(colors.background) {
                    title()
                }
            }
        },
        navigationIcon = {
            startAction?.let { it() }
        },
        actions = {
            AppBarIconsRow(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                actions?.let { it() }
            }
        },
        colors = topAppBarColors(
            containerColor = colors.background,
            scrolledContainerColor = colors.background,
            navigationIconContentColor = textStyle.color,
            titleContentColor = textStyle.color,
            actionIconContentColor = textStyle.color
        )
    )
}

/**
Overlaps its children by the padding amount of the Icon button.
 */
@Composable
private fun AppBarIconsRow(
    modifier: Modifier = Modifier,
    children: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = children
    ) { measurables, constraints ->
        val overlap =
            IconButtonDefaults.padding.calculateStartPadding(LayoutDirection.Ltr).toPx().toInt()

        val childConstraints = Constraints(maxHeight = constraints.maxHeight)

        val placeables = measurables.map { measurable ->
            measurable.measure(childConstraints)
        }

        val rowHeight = placeables.maxOfOrNull { it.height } ?: 0

        val width = placeables.sumOf { it.width - overlap }

        layout(width + overlap, rowHeight) {
            var xPosition = 0

            for (placeable in placeables) {
                placeable.placeRelative(x = xPosition, y = 0)
                xPosition += placeable.width - overlap
            }
        }
    }
}

/**
 * Contains the colors that define a [OUIAppBar]
 */
data class AppBarColors(

    val background: Color
)

/**
 * Constructs the default colors for a [OUIAppBar]
 *
 * @param background
 * @return
 */
@Composable
fun appBarColors(
    background: Color = Color.Transparent,
): AppBarColors = AppBarColors(
    background = background
)