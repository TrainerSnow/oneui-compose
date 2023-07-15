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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import org.oneui.compose.base.Icon
import org.oneui.compose.layout.internal.SlidingDrawerOpenedState
import org.oneui.compose.layout.internal.SlidingDrawerState
import org.oneui.compose.layout.internal.SlidingOutDrawerLayout
import org.oneui.compose.layout.internal.modifier.overlay
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.mapRange
import org.oneui.compose.widgets.buttons.OUIIconButton
import org.oneui.compose.widgets.buttons.ouiIconButtonColors
import kotlinx.coroutines.launch

/**
 * Stores default values for the button specs
 */
object DrawerDefaults {

    /**
     * Radius of the drawers corners
     */
    val cornerRadius: Dp = 15.dp

    val layoutPadding = PaddingValues(
        top = 16.dp
    )

    val drawerPadding = PaddingValues(
        horizontal = 8.dp
    )

    val headerIconMargin = PaddingValues(
        top = 8.dp,
        end = 8.dp
    )

    val maxWidthCompact = 320.dp

    val minWidthCompact = 0.dp

    const val scrimAlphaTarget: Float = 0.19F
}

/**
 * Stores the colors that are needed to define a drawer layout
 */
data class DrawerColors(

    /**
     * The color to aim for when applying scrim to the main content
     */
    val scrim: Color,

    /**
     * The background of the drawer itself
     */
    val drawerBackground: Color,

    /**
     * The background of the layout behind the drawer
     */
    val background: Color

)

/**
 * Constructs the default layout colors
 */
@Composable
fun drawerColors(
    scrim: Color = OneUITheme.colors.drawerScrim,
    drawerBackground: Color = OneUITheme.colors.drawerBackground,
    background: Color = OneUITheme.colors.drawerLayoutBackground
) = DrawerColors(
    scrim = scrim,
    drawerBackground = drawerBackground,
    background = background
)

/**
 * Composable for a oui-style drawer layout
 *
 * TODO: Preview picture
 * TODO: The velocity is too high
 * TODO: Scrim color in darkmode is not dark enough
 * TODO: User can still interact with [content] when drawer is opened
 *
 * @param modifier The modifier to be applied to the layout
 * @param shape The shape the drawer is made of
 * @param colors The colors to apply to the drawer
 * @param layoutPadding The padding to be applied to the whole layout
 * @param drawerPadding The padding to apply to the drawer itself
 * @param headerIconSize The side of the header icon
 * @param headerIconPadding The padding of the header icon
 * @param headerIconMargin The margin of the header icon
 * @param headerIconTint The tint of the header icon
 * @param headerIcon The header icon itself
 * @param maxWidth The width of the drawer at opened state
 * @param minWidth The width of the drawer at closed state
 * @param snapThreshold The threshold when to snap to open (=1) or closed (=0)
 * @param scrimAlphaTarget The alpha value of scrim to apply when the drawer is opened
 * @param drawerContent The content to put inside the drawer, preferably [DrawerItem]'s
 * @param content The content to put besides the drawer
 */
@Composable
fun DrawerLayoutCompact(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(
        topEnd = DrawerDefaults.cornerRadius,
        bottomEnd = DrawerDefaults.cornerRadius
    ),
    colors: DrawerColors = drawerColors(),
    layoutPadding: PaddingValues = DrawerDefaults.layoutPadding,
    drawerPadding: PaddingValues = DrawerDefaults.drawerPadding,
    headerIconMargin: PaddingValues = DrawerDefaults.headerIconMargin,
    headerIconTint: Color = OneUITheme.colors.drawerHeaderIconTint,
    headerIcon: Icon? = null,
    maxWidth: Dp = DrawerDefaults.maxWidthCompact,
    minWidth: Dp = DrawerDefaults.minWidthCompact,
    scrimAlphaTarget: Float = DrawerDefaults.scrimAlphaTarget,
    onHeaderIconClick: (() -> Unit)? = null,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val drawerState = remember {
        SlidingDrawerState(
            SlidingDrawerOpenedState.CLOSED,
            velocityThreshold = with(density) { 100.dp.toPx() }
        )
    }
    val leftmost = with(LocalDensity.current) { minWidth.toPx() }
    val rightmost = with(LocalDensity.current) { maxWidth.toPx() }

    val offset = drawerState.pixelProgress.coerceIn(
        leftmost,
        rightmost
    ).let {
        if(it.isNaN()) 0F else it
    }
    val progress = offset / rightmost

    val scrim = colors.scrim.copy(
        alpha = mapRange(
            value = progress,
            origStart = 0F,
            origEnd = 1F,
            targetStart = 0F,
            targetEnd = scrimAlphaTarget
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
                layoutPadding
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
                            OUIIconButton(
                                modifier = Modifier
                                    .padding(headerIconMargin),
                                icon = headerIcon,
                                onClick = onHeaderIconClick,
                                colors = ouiIconButtonColors(
                                    tint = headerIconTint
                                )
                            )
                        }
                    }

                    drawerContent(this@Column)
                }
            },
            contentOverlay = { },
            maxWidth = maxWidth,
            minWidth = minWidth,
            state = drawerState
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
                        enabled = drawerState.isOpened
                    ) {
                        scope.launch {
                            drawerState.closeAnimate()
                        }
                    }
            ) {
                content()
            }
        }
    }
}