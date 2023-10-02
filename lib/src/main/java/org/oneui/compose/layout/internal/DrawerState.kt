package org.oneui.compose.layout.internal

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * The state to control the sliding Drawer
 */
@OptIn(ExperimentalFoundationApi::class)
data class DrawerState(
    val animDuration: Int = 600,
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
        animationSpec = tween(
            durationMillis = animDuration
        )
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



@Composable
fun rememberSlidingDrawerState(
    initial: SlidingDrawerOpenedState = SlidingDrawerOpenedState.CLOSED,
    velocityThreshold: Float = with(LocalDensity.current) { 100.dp.toPx() }
) = remember {
    DrawerState(
        initial = initial,
        velocityThreshold = velocityThreshold
    )
}