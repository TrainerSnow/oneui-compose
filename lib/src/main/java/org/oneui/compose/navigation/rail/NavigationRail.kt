package org.oneui.compose.navigation.rail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.oneui.compose.layout.internal.BaseNavigationRail
import org.oneui.compose.layout.internal.DrawerState
import org.oneui.compose.layout.internal.rememberSlidingDrawerState
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.theme.locals.ProvideBackgroundColor
import org.oneui.compose.util.plus

/**
 * Composable for a oneui-style Navigation rail. This acts as an alternative to a Drawer or a Bottom Navigation Bar on larger screens.
 *
 * @param modifier The [Modifier] to apply to the container
 * @param state The [DrawerState] to control the Rail
 * @param colors The [NavigationRailItemColors] to apply
 * @param shape The [Shape] of the rail
 * @param layoutPadding The padding to apply to the entire layout
 * @param railPadding The padding to apply to the rail
 * @param railHeader The section of the rail which includes e.g. a settings icon
 * @param railContent The section of the rail which includes the actual navigation destinations
 * @param content The actual content to be put next to the rail.
 */
@Composable
fun NavigationRail(
    modifier: Modifier = Modifier,
    state: DrawerState = rememberSlidingDrawerState(),
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    colors: NavigationRailColors = navigationRailColors(),
    shape: Shape = NavigationRailDefaults.shape,
    layoutPadding: PaddingValues = NavigationRailDefaults.layoutPadding,
    railPadding: PaddingValues = NavigationRailDefaults.railPadding,
    railHeader: @Composable ColumnScope.(progress: Float) -> Unit,
    railContent: @Composable ColumnScope.(progress: Float) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(layoutPadding + windowInsets.asPaddingValues())
    ) {
        BaseNavigationRail(
            modifier = Modifier
                .fillMaxSize(),
            state = state,
            minWidth = NavigationRailDefaults.closedSize,
            maxWidth = NavigationRailDefaults.openedSize,
            railContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape)
                        .background(colors.railBackground, shape)
                        .padding(railPadding)
                ) {
                    ProvideBackgroundColor(colors.railBackground) {
                        railHeader(it)
                        railContent(it)
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ProvideBackgroundColor(colors.background) {
                    content()
                }
            }
        }
    }
}

/**
 * Contains default values for a [NavigationRail]
 */
object NavigationRailDefaults {

    val layoutPadding = PaddingValues(
        top = 16.dp
    )

    val railPadding = PaddingValues(
        horizontal = 12.dp
    )
    
    val closedSize = 70.dp

    val openedSize = 350.dp

    val shape = RoundedCornerShape(
        topEnd = 15.dp,
        bottomEnd = 15.dp
    )

}

/**
 * Contains the colors used for a [NavigationRail]
 */
data class NavigationRailColors(

    val background: Color,

    val railBackground: Color

)

/**
 * Constructs the default [NavigationRailItemColors]
 *
 * @param background The color to be used as a background
 * @param railBackground The color to be used as a background for the actual rail
 */
@Composable
fun navigationRailColors(
    background: Color = OneUITheme.colors.seslRoundAndBgcolor,
    railBackground: Color = OneUITheme.colors.navigationRailBackground
) = NavigationRailColors(background, railBackground)


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun NavigationRailPreview() {
    OneUITheme {
        NavigationRail(
            modifier = Modifier.fillMaxSize(),
            railHeader = { },
            railContent = { },
            content = { }
        )
    }
}