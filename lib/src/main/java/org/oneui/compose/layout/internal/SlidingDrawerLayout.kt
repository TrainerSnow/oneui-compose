package org.oneui.compose.layout.internal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
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
 * Composable for a Drawerlayout that doesnt overlay the main content but slides it out of the screen.
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param drawerContent The content to be shown inside the Drawer
 * @param contentOverlay The overlay for the main content, depending on the progress. E.g. a scrim.
 * @param state The [DrawerState] to control the drawer
 * @param maxWidth The width the drawer has when opened
 * @param minWidth The width the drawer has when it is closed
 * @param content The content to be put besides the drawer
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlidingDrawerLayout(
    modifier: Modifier = Modifier,
    drawerContent: @Composable () -> Unit,
    contentOverlay: @Composable (Float) -> Unit,
    velocityThreshold: Float = with(LocalDensity.current) { 100.dp.toPx() },
    state : DrawerState = remember {
        DrawerState(
            initial = SlidingDrawerOpenedState.CLOSED,
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
                .anchoredDraggable(
                    state = state.draggableState,
                    orientation = Orientation.Horizontal
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


@Preview
@Composable
fun SlidingDrawerLayoutPreview() {
    SlidingDrawerLayout(
        drawerContent = {
            Box(modifier = Modifier.fillMaxSize().background(Color.Blue))
        },
        content = {
            Box(modifier = Modifier.fillMaxSize().background(Color.Red))
        },
        contentOverlay = { },
        maxWidth = 200.dp,
        minWidth = 30.dp
    )
}