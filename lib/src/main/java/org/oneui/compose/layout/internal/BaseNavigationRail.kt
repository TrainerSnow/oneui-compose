package org.oneui.compose.layout.internal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.oneui.compose.util.mapRange

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseNavigationRail(
    modifier: Modifier = Modifier,
    minWidth: Dp,
    maxWidth: Dp,
    state: DrawerState = rememberSlidingDrawerState(
        initial = SlidingDrawerOpenedState.CLOSED,
        velocityThreshold = with(LocalDensity.current) { 100.dp.toPx() },
    ),
    railContent: @Composable (progress: Float) -> Unit,
    content: @Composable () -> Unit
) {
    @Composable
    fun <T> withDensity(
        block: Density.() -> T
    ) = LocalDensity.current.block()

    val leftmost = withDensity { minWidth.toPx() }
    val rightmost = withDensity { maxWidth.toPx() } //Need px for anchors

    state.setAnchors(
        mapOf(
            SlidingDrawerOpenedState.CLOSED to leftmost,
            SlidingDrawerOpenedState.OPEN to rightmost
        )
    )

    Row(
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
        ).run { if(isNaN()) 0F else this }
        val offsetDp = withDensity { offset.toDp() }
        val progress = mapRange(
            value = offset,
            origStart = leftmost,
            origEnd = rightmost,
            targetStart = 0F,
            targetEnd = 1F
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(offsetDp)
        ) {
            railContent(progress)
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
        ) {
            content()
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun BaseNavigationRailPreview() {
    BaseNavigationRail(
        minWidth = 80.dp,
        maxWidth = 250.dp,
        railContent = {
            when (it) {
                0F -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                    )
                }
                1F -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Blue)
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Green.copy(alpha = it))
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
        )
    }
}