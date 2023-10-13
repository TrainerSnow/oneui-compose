package org.oneui.compose.layout.toolbar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.layout.internal.modifier.NestedScrollConnection
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.isEven
import org.oneui.compose.util.mapRange
import org.oneui.compose.widgets.box.RoundedCornerBox
import org.oneui.compose.widgets.buttons.IconButton
import org.oneui.compose.widgets.menu.MenuItem
import org.oneui.compose.widgets.menu.PopupMenu

/**
 * Composable for a oneui-style Collapsing toolbar layout
 *
 * TODO: Add preview picture
 *
 * @param modifier The modifier to be applied to the container
 * @param state The [CollapsingToolbarState] for controlling the layout
 * @param toolbarTitle The composable drawing the title of the layout. Preferably a [Text]
 * @param toolbarSubtitle The composable drawing the title of the layout. Preferably a [Text]
 * @param toolbarHeight The height of the toolbar when expanded
 * @param appbarActions The actions shown on the appbar. Expected to be [IconButton]s, other could lead to undefined behaviour.
 * @param appbarNavAction The navigation action shown at the start of the appbar
 * @param contentPadding The padding to apply between the [content] and the layout
 * @param content The content to be put inside the layout, arranged in a vertically in a [Column]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsingToolbarLayout(
    modifier: Modifier = Modifier,
    state: CollapsingToolbarState = rememberCollapsingToolbarState(),
    expandable: Boolean = true,
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    toolbarTitle: (@Composable (CollapsingToolbarCollapsedState) -> Unit)? = null,
    toolbarSubtitle: (@Composable () -> Unit)? = null,
    toolbarHeight: Dp = 280.dp,
    appbarActions: (@Composable () -> Unit)? = null,
    appbarNavAction: (@Composable () -> Unit)? = null,
    menu: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = CollapsingToolbarLayoutDefaults.contentPadding,
    content: @Composable ColumnScope.() -> Unit
) {
    val density = LocalDensity.current

    state.setAnchors(
        mapOf(
            CollapsingToolbarCollapsedState.COLLAPSED to 0F,
            CollapsingToolbarCollapsedState.EXTENDED to with(density) { toolbarHeight.toPx() }
        )
    )

    val offsetDp = state.pixelProgress.let {
        if (it.isNaN()) 0F.dp else with(density) { it.toDp() }
    }
    val progress = offsetDp / toolbarHeight

    val appbarAlpha = if (progress > 0.5) 0F else 1 - progress * 2
    val toolbarAlpha = if (progress < 0.5) 0F else mapRange(
        value = progress,
        origStart = 0.5F,
        origEnd = 1F,
        targetStart = 0F,
        targetEnd = 1F
    )

    val mod = if (expandable) Modifier
        .anchoredDraggable(
            state = state.draggableState,
            orientation = Orientation.Vertical
        )
        .nestedScroll(
            state.draggableState.NestedScrollConnection
        ) else Modifier

    val titleTextStyle = OneUITheme.types.appbarTitleExtended.copy(
        color = OneUITheme.types.appbarTitleExtended.color.copy(
            toolbarAlpha.coerceIn(0F, 1F)
        )
    )
    val titleCollapsedTextStyle = OneUITheme.types.appbarTitleCollapsed.copy(
        color = OneUITheme.types.appbarTitleCollapsed.color.copy(
            alpha = appbarAlpha.coerceIn(0F, 1F)
        )
    )
    val subtitleTextStyle = OneUITheme.types.appbarSubtitle.copy(
        color = OneUITheme.types.appbarSubtitle.color.copy(
            toolbarAlpha.coerceIn(0F, 1F)
        )
    )

    Column(
        modifier = modifier
            .then(mod)
            .padding(windowInsets.asPaddingValues())
    ) {
        CollapsingToolbarTitle(
            modifier = Modifier
                .height(
                    height = offsetDp
                ),
            title = {
                ProvideTextStyle(titleTextStyle) {
                    toolbarTitle?.let { it(CollapsingToolbarCollapsedState.EXTENDED) }
                }
            },
            subtitle = {
                ProvideTextStyle(subtitleTextStyle) {
                    toolbarSubtitle?.let { it() }
                }
            }
        )

        OUIAppBar(
            windowInsets = WindowInsets(0, 0, 0, 0), //Are applied via parent
            title = {
                ProvideTextStyle(titleCollapsedTextStyle) {
                    toolbarTitle?.let { it(CollapsingToolbarCollapsedState.EXTENDED) }
                }
            },
            startAction = appbarNavAction?.let {
                {
                    it()
                }
            },
            actions = appbarActions?.let {
                {
                    it()
                    menu?.let { it() }
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            content(this)
        }
    }
}


/**
 * Overload for [CollapsingToolbarLayout] that takes in raw strings
 *
 * TODO: Add preview picture
 *
 * @param modifier The modifier to be applied to the container
 * @param state The [CollapsingToolbarState] for controlling the layout
 * @param toolbarTitle The composable drawing the title of the layout. Preferably a [Text]
 * @param toolbarSubtitle The composable drawing the title of the layout. Preferably a [Text]
 * @param toolbarHeight The height of the toolbar when expanded
 * @param appbarActions The actions shown on the appbar. Expected to be [IconButton]s, other could lead to undefined behaviour.
 * @param appbarNavAction The navigation action shown at the start of the appbar
 * @param contentPadding The padding to apply between the [content] and the layout
 * @param content The content to be put inside the layout, arranged in a vertically in a [Column]
 */
@Composable
fun CollapsingToolbarLayout(
    modifier: Modifier = Modifier,
    state: CollapsingToolbarState = rememberCollapsingToolbarState(),
    expandable: Boolean = true,
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    toolbarTitle: String? = null,
    toolbarSubtitle: String? = null,
    toolbarHeight: Dp = 280.dp,
    appbarActions: (@Composable () -> Unit)? = null,
    appbarNavAction: (@Composable () -> Unit)? = null,
    menu: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = CollapsingToolbarLayoutDefaults.contentPadding,
    content: @Composable ColumnScope.() -> Unit
) = CollapsingToolbarLayout(
    modifier, state, expandable, windowInsets,
    toolbarTitle = {
        toolbarTitle?.let {
            Text(it)
        }
    },
    toolbarSubtitle = {
        toolbarSubtitle?.let {
            Text(it)
        }
    },
    toolbarHeight,
    appbarActions,
    appbarNavAction,
    menu,
    contentPadding,
    content
)

enum class CollapsingToolbarCollapsedState {

    /**
     * State for when the title is shown in its container
     */
    EXTENDED,

    /**
     * State for when the title is shown in the appbar
     */
    COLLAPSED

}

/**
 * The state to control the sliding Drawer
 */
@OptIn(ExperimentalFoundationApi::class)
data class CollapsingToolbarState(
    val initial: CollapsingToolbarCollapsedState,
    val velocityThreshold: Float
) {

    /**
     * State for controlling the swipeable modifier
     */
    internal val draggableState = AnchoredDraggableState(
        initialValue = initial,
        positionalThreshold = { distance: Float ->
            distance / 2F
        },
        velocityThreshold = {
            velocityThreshold
        },
        animationSpec = tween()
    )

    /**
     * Whether the toolbar is collapsed, or whether it was collapsed before the animation started
     */
    val isCollapsed: Boolean
        get() = draggableState.currentValue == CollapsingToolbarCollapsedState.COLLAPSED

    /**
     * Whether the toolbar is extended, or whether it was extended before the animation started
     */
    val isExtended: Boolean
        get() = draggableState.currentValue == CollapsingToolbarCollapsedState.EXTENDED

    /**
     * Whether the toolbar is being swiped at the moment
     */
    val isAnimating: Boolean
        get() = draggableState.isAnimationRunning

    /**
     * Extend toolbar with animation
     */
    suspend fun extendAnimate() = animate(CollapsingToolbarCollapsedState.EXTENDED)

    /**
     * Collapse toolbar with animation
     */
    suspend fun collapseAnimate() = animate(CollapsingToolbarCollapsedState.COLLAPSED)

    /**
     * Extend toolbar without animation
     */
    suspend fun extend() = snap(CollapsingToolbarCollapsedState.EXTENDED)

    /**
     * Collapse toolbar without animation
     */
    suspend fun collapse() = snap(CollapsingToolbarCollapsedState.COLLAPSED)

    /**
     * The progress in pixels the toolbar has made
     */
    val pixelProgress: Float
        get() = draggableState.offset

    private suspend fun animate(
        target: CollapsingToolbarCollapsedState
    ) = draggableState
        .animateTo(target)

    private suspend fun snap(
        target: CollapsingToolbarCollapsedState
    ) = draggableState
        .snapTo(target)

    fun setAnchors(
        dragAnchors: Map<CollapsingToolbarCollapsedState, Float>
    ) {
        draggableState.updateAnchors(
            DraggableAnchors {
                dragAnchors.forEach {
                    it.key at it.value
                }
            }
        )
    }

}

@Composable
fun rememberCollapsingToolbarState(
    initial: CollapsingToolbarCollapsedState = CollapsingToolbarCollapsedState.EXTENDED,
    velocityThreshold: Float = with(LocalDensity.current) { 100.dp.toPx() }
): CollapsingToolbarState = remember {
    CollapsingToolbarState(
        initial = initial,
        velocityThreshold = velocityThreshold
    )
}

object CollapsingToolbarLayoutDefaults {

    val contentPadding = PaddingValues()

}


@Preview
@Composable
fun CollapsingToolbarLayoutPreview() = RoundedCornerBox {
    CollapsingToolbarLayout(
        modifier = Modifier
            .fillMaxSize(),
        toolbarTitle = "Title",
        toolbarSubtitle = "Subtitle",
        appbarActions = {
            IconButton(icon = Icon.Vector(Icons.Default.MoreVert))
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            (1..100).forEach {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .background(if (it.isEven) Color.Red else Color.Blue)
                )
            }
        }
    }
}