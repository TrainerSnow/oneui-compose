package org.oneui.compose.layout.toolbar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.layout.internal.modifier.NestedScrollConnection
import org.oneui.compose.util.mapRange
import org.oneui.compose.widgets.buttons.IconButton

object CollapsingToolbarLayoutDefaults {

    const val snapThreshold: Float = 0.5F

}

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
     * State for controling the swipeable modifier
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
    initial: CollapsingToolbarCollapsedState,
    velocityThreshold: Float
): CollapsingToolbarState = remember {
    CollapsingToolbarState(
        initial = initial,
        velocityThreshold = velocityThreshold
    )
}

/**
 * Composable for a oneui-style Collapsing toolbar layout
 *
 * TODO: Add preview picture
 * TODO: When the initial state is Expanded, the offset variable is defaulted to 0, so it takes a moment until it snaps to Expanded.
 * TODO: The swipe velocity is too high
 * TODO: The layout change is higher than the swipe gesture on the screen
 * TODO: When the [appbarActions] are [IconButton]'s, the spacing between them is too big. In normal OUI, the hitboxes overlap, here they act as margin.
 *
 * @param modifier The modifier to be applied to the container
 * @param state The [CollapsingToolbarState] for controlling the layout
 * @param snapThreshold The threshold at which to snap to the next state
 * @param toolbarTitle The title
 * @param toolbarSubtitle The subtitle
 * @param toolbarHeight The height of the toolbar when expanded
 * @param appbarActions The actions shown on the appbar
 * @param appbarNavAction The navigation action shown at the start of the appbar
 * @param content The content to be put inside the layout, arranged in a vertically in a [Column]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsingToolbarLayout(
    modifier: Modifier = Modifier,
    velocityThreshold: Float = with(LocalDensity.current) { 100.dp.toPx() },
    state: CollapsingToolbarState = rememberCollapsingToolbarState(
        CollapsingToolbarCollapsedState.EXTENDED,
        velocityThreshold
    ),
    snapThreshold: Float = CollapsingToolbarLayoutDefaults.snapThreshold,
    toolbarTitle: String,
    toolbarSubtitle: String? = null,
    toolbarHeight: Dp = 280.dp,
    appbarActions: (@Composable RowScope.() -> Unit)? = null,
    appbarNavAction: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    state.setAnchors(
        mapOf(
            CollapsingToolbarCollapsedState.COLLAPSED to 0F ,
            CollapsingToolbarCollapsedState.EXTENDED to toolbarHeight.value
        )
    )

    val offset = state.pixelProgress.let {
        if(it.isNaN()) 0F else it
    }
    val progress = offset / toolbarHeight.value

    val appbarAlpha = if (progress > 0.5) 0F else 1 - progress * 2
    val toolbarAlpha = if (progress < 0.5) 0F else mapRange(
        value = progress,
        origStart = 0.5F,
        origEnd = 1F,
        targetStart = 0F,
        targetEnd = 1F
    )

    val scrollstate = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .anchoredDraggable(
                state = state.draggableState,
                orientation = Orientation.Vertical
            )
            .nestedScroll(
                state.draggableState.NestedScrollConnection
            )
    ) {
        CollapsingToolbarTitle(
            modifier = Modifier
                .height(
                    height = offset.dp
                ),
            title = {
                Text(
                    text = toolbarTitle
                )
            },
            subtitle = toolbarSubtitle?.let {
                {
                    Text(
                        text = it
                    )
                }
            },
            textAlpha = toolbarAlpha.coerceIn(0F, 1F)
        )

        OUIAppBar(
            title = {
                Text(
                    text = toolbarTitle
                )
            },
            startAction = appbarNavAction?.let {
                {
                    it()
                }
            },
            actions = appbarActions?.let {
                {
                    it(this)
                }
            },
            titleTextAlpha = appbarAlpha.coerceIn(0F, 1F)
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollstate)
                .fillMaxWidth()
        ) {
            content(this)
        }
    }
}