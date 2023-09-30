package org.oneui.compose.layout.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.oneui.compose.layout.internal.SlidingDrawerState
import org.oneui.compose.layout.internal.SlidingOutDrawerLayout
import org.oneui.compose.layout.internal.modifier.overlay
import org.oneui.compose.layout.internal.rememberSlidingDrawerState
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.theme.locals.ProvideBackgroundColor
import org.oneui.compose.util.mapRange
import org.oneui.compose.util.plus

/**
 * Composable for a oui-style drawer layout
 *
 * @param modifier The modifier to be applied to the layout
 * @param colors The colors to apply to the drawer
 * @param state The [SlidingDrawerState] to control the drawer
 * @param shape The shape the drawer is made of
 * @param layoutPadding The padding to be applied to the layout behind the drawer
 * @param drawerPadding The padding to apply to the drawer content
 * @param headerIcon The header icon to be shown in the top right hand part of the drawer
 * @param maxWidth The width of the drawer at opened state
 * @param drawerContent The content to put inside the drawer, preferably [DrawerItem]'s
 * @param content The content to put besides the drawer
 */
@Composable
fun DrawerLayout(
    modifier: Modifier = Modifier,
    colors: DrawerColors = drawerColors(),
    state: SlidingDrawerState = rememberSlidingDrawerState(),
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    shape: Shape = RoundedCornerShape(
        topEnd = DrawerDefaults.cornerRadius,
        bottomEnd = DrawerDefaults.cornerRadius
    ),
    layoutPadding: PaddingValues = DrawerDefaults.layoutPadding,
    drawerPadding: PaddingValues = DrawerDefaults.drawerPadding,
    headerIcon: (@Composable () -> Unit)? = null,
    maxWidth: Dp = DrawerDefaults.maxWidthCompact,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    val leftmost = 0F
    val rightmost = with(LocalDensity.current) { maxWidth.toPx() }

    val offset = state.pixelProgress.coerceIn(
        leftmost,
        rightmost
    ).let {
        if (it.isNaN()) 0F else it
    }
    val progress = offset / rightmost

    val scrim = colors.scrim.copy(
        alpha = mapRange(
            value = progress,
            origStart = 0F,
            origEnd = 1F,
            targetStart = 0F,
            targetEnd = colors.scrim.alpha * progress
        )
    )
    val darkenedBackground = scrim.compositeOver(
        colors.background
    )

    Box(
        modifier = modifier
            .background(
                color = darkenedBackground
            )
            .padding(
                layoutPadding + windowInsets.asPaddingValues()
            )
    ) {
        SlidingOutDrawerLayout(
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = colors.drawerBackground,
                            shape = shape
                        )
                        .clip(
                            shape = shape
                        )
                        .padding(
                            drawerPadding
                        )
                ) {
                    headerIcon?.let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            it()
                        }
                    }

                    ProvideBackgroundColor(colors.drawerBackground) {
                        drawerContent(this@Column)
                    }
                }
            },
            contentOverlay = {
                if (it == 0F) return@SlidingOutDrawerLayout
                Box(
                    //Empty click listener to catch click events and disallow from interacting with children
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = { },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                )
            },
            maxWidth = maxWidth,
            minWidth = 0.dp,
            state = state
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colors.background
                    )
                    .overlay {
                        drawRect(
                            color = scrim
                        )
                    }
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = state.isOpened
                    ) {
                        scope.launch {
                            state.closeAnimate()
                        }
                    }
            ) {
                ProvideBackgroundColor(colors.background) {
                    content()
                }
            }
        }
    }
}

/**
 * Stores the colors that are needed to define a drawer layout
 */
data class DrawerColors(

    val scrim: Color,

    val drawerBackground: Color,

    val background: Color

)

/**
 * Constructs the default layout colors
 *
 * @param scrim The color to aim for when applying scrim to the main content
 * @param drawerBackground The background of the drawer itself
 * @param background The background of the layout behind the drawer
 */
@Composable
fun drawerColors(
    scrim: Color = OneUITheme.colors.drawerScrim,
    drawerBackground: Color = OneUITheme.colors.seslBackgroundColor,
    background: Color = OneUITheme.colors.seslRoundAndBgcolor
) = DrawerColors(
    scrim = scrim,
    drawerBackground = drawerBackground,
    background = background
)

/**
 * Stores default values for the button specs
 */
object DrawerDefaults {

    val cornerRadius: Dp = 15.dp

    val layoutPadding = PaddingValues(
        top = 16.dp
    )

    val drawerPadding = PaddingValues(
        horizontal = 8.dp
    )

    val maxWidthCompact = 320.dp
}