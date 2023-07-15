package org.oneui.compose.layout.internal

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * TODOThe states a drawer can be in
 *
 */
enum class SlidingDrawerOpenedState {

    OPEN,

    CLOSED,
}

/**
 * The state to control the sliding Drawer
 */
@OptIn(ExperimentalFoundationApi::class)
data class SlidingDrawerState(
    val initial: SlidingDrawerOpenedState,
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

    fun setAnchors(
        dragAnchors: Map<SlidingDrawerOpenedState, Float>
    ) {
        draggableState.updateAnchors(
            DraggableAnchors {
                dragAnchors.forEach {
                    it.key at it.value
                }
            }
        )
    }

    /**
     * Whether the drawer is opened, or whether it was opened before the animation started
     */
    val isOpened: Boolean
        get() = draggableState.currentValue == SlidingDrawerOpenedState.OPEN

    /**
     * Whether the drawer is closed, or whether it was closed before the animation started
     */
    val isClosed: Boolean
        get() = draggableState.currentValue == SlidingDrawerOpenedState.CLOSED

    /**
     * Whether the drawer is being swiped at the moment
     */
    val isAnimating: Boolean
        get() = draggableState.isAnimationRunning

    /**
     * Open drawer with animation
     */
    suspend fun openAnimate() = animate(SlidingDrawerOpenedState.OPEN)

    /**
     * Close drawer with animation
     */
    suspend fun closeAnimate() = animate(SlidingDrawerOpenedState.CLOSED)

    /**
     * Open drawer without animation
     */
    suspend fun open() = snap(SlidingDrawerOpenedState.OPEN)

    /**
     * Close drawer without animation
     */
    suspend fun close() = snap(SlidingDrawerOpenedState.CLOSED)

    /**
     * The progress in pixels the Drawer has made
     */
    val pixelProgress: Float
        get() = draggableState.offset

    private suspend fun animate(
        target: SlidingDrawerOpenedState
    ) = draggableState
        .animateTo(target)

    private suspend fun snap(
        target: SlidingDrawerOpenedState
    ) = draggableState
        .snapTo(target)

}

/**
 * Composable for a Drawerlayout that doesnt overlay the main content but slides it out of the screen.
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param drawerContent The content to be shown inside the Drawer
 * @param contentOverlay The overlay for the main content, depending on the progress. E.g. a scrim.
 * @param state The [SlidingDrawerState] to control the drawer
 * @param maxWidth The width the drawer has when opened
 * @param minWidth The width the drawer has when it is closed
 * @param snapThreshold When to snap to the side (right or left)
 * @param content The content to be put besides the drawer
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlidingOutDrawerLayout(
    modifier: Modifier = Modifier,
    drawerContent: @Composable () -> Unit,
    contentOverlay: @Composable (Float) -> Unit,
    velocityThreshold: Float = with(LocalDensity.current) { 100.dp.toPx() },
    state : SlidingDrawerState = remember {
        SlidingDrawerState(
            SlidingDrawerOpenedState.CLOSED,
            velocityThreshold = velocityThreshold
        )
    },
    maxWidth: Dp,
    minWidth: Dp,
    content: @Composable () -> Unit
) {
    val leftmost = with(LocalDensity.current) { minWidth.toPx() }
    val rightmost = with(LocalDensity.current) { maxWidth.toPx() } //Need px for anchors

    state.setAnchors(
        mapOf(
            SlidingDrawerOpenedState.CLOSED to leftmost,
            SlidingDrawerOpenedState.OPEN to rightmost
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .anchoredDraggable(
                state = state.draggableState,
                orientation = Orientation.Horizontal
            )
    ) {
        //We don't want any "overpull" and no negative offset, so we restrict it
        val offset = state.draggableState.offset.coerceIn(
            leftmost,
            rightmost
        )
        val progress = offset / rightmost

        Box(
            modifier = Modifier
                .sizeIn(
                    maxWidth = maxWidth
                )
                .offset(
                    x = -with(LocalDensity.current) {
                        (rightmost - offset).toDp()
                    }
                )
        ) {
            drawerContent()
        }
        Box(
            modifier = Modifier
                .offset(
                    x = with(LocalDensity.current) { offset.toDp() }
                )
        ) {
            Box {
                content()
                contentOverlay(progress)
            }
        }
    }
}