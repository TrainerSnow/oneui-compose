package org.oneui.compose.layout.swiperefresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.oneui.compose.progress.CircularProgressIndicatorSize
import org.oneui.compose.progress.ProgressIndicator
import org.oneui.compose.progress.ProgressIndicatorType
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.theme.locals.LocalBackgroundColor

/**
 * Composable for a oneui-style pull-to-refresh layout. This allows the user to drag from top to bottom
 *      and request updates in the displayed content
 *
 * @param modifier The [Modifier] to apply to the whole layout. Should be [Modifier] [fillMaxSize] to fill the entire screen.
 * @param colors The [SwipeRefreshLayoutColors] to apply
 * @param onRefresh The callback invoked when the user drags down far enough
 * @param refreshing Whether the layout is currently refreshing
 * @param content The content to put below the refresh indicator
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeRefreshLayout(
    modifier: Modifier = Modifier,
    colors: SwipeRefreshLayoutColors = swipeRefreshColors(),
    onRefresh: () -> Unit,
    refreshing: Boolean = false,
    content: @Composable () -> Unit
) {
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh,
        refreshThreshold = SwipeRefreshLayoutDefaults.pullThreshold,
        refreshingOffset = SwipeRefreshLayoutDefaults.circleOffset
    )

    Box(
        modifier = modifier
            .pullRefresh(
                state = state
            )
    ) {
        //This needs to be here, otherwise the `pullRefresh` modifier doesn't work.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { }

        Box(
            modifier = Modifier
                .height(SwipeRefreshLayoutDefaults.progressIndicatorSize.size + SwipeRefreshLayoutDefaults.padding * 2)
                .fillMaxWidth()
                .offset(y = SwipeRefreshLayoutDefaults.circleOffset),
            contentAlignment = Alignment.Center
        ) {
            SwipeRefreshProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                refreshing = refreshing,
                colors = colors,
                progress = state.progress
            )
        }

        content()
    }
}

@Composable
internal fun SwipeRefreshProgressIndicator(
    modifier: Modifier = Modifier,
    refreshing: Boolean,
    colors: SwipeRefreshLayoutColors,
    progress: Float
) {
    if (refreshing) {
        Box(
            modifier = modifier
                .size(SwipeRefreshLayoutDefaults.progressIndicatorSize.size + SwipeRefreshLayoutDefaults.padding * 2)
                .shadow(SwipeRefreshLayoutDefaults.elevation, CircleShape)
                .clip(CircleShape)
                .background(colors.circle),
            contentAlignment = Alignment.Center
        ) {
            ProgressIndicator(
                type = ProgressIndicatorType
                    .CircularIndeterminate(
                        SwipeRefreshLayoutDefaults.progressIndicatorSize
                    )
            )

        }
    } else {
        if(progress == 0F) return
        if (progress <= SwipeRefreshLayoutDefaults.shrinkingAnimationStart) {
            val actProgress = progress / SwipeRefreshLayoutDefaults.shrinkingAnimationStart

            val size = SwipeRefreshLayoutDefaults.progressIndicatorSize.size * actProgress

            Box(
                modifier = modifier
                    .size(size + SwipeRefreshLayoutDefaults.padding * 2)
                    .shadow(SwipeRefreshLayoutDefaults.elevation, CircleShape)
                    .clip(CircleShape)
                    .background(
                        //Need to manually overlay over background color, otherwise the shadow would be visible through the alpha value of the color.
                        colors.circle
                            .copy(alpha = actProgress)
                            .compositeOver(LocalBackgroundColor.current)
                    )
                    .graphicsLayer(alpha = actProgress),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                        .background(colors.progress)
                )
            }
        } else {
            val actProgress =
                (progress - SwipeRefreshLayoutDefaults.shrinkingAnimationStart) / (1 - SwipeRefreshLayoutDefaults.shrinkingAnimationStart)

            Box(
                modifier = modifier
                    .size(SwipeRefreshLayoutDefaults.progressIndicatorSize.size + SwipeRefreshLayoutDefaults.padding * 2)
                    .shadow(SwipeRefreshLayoutDefaults.elevation, CircleShape)
                    .clip(CircleShape)
                    .background(colors.circle),
                contentAlignment = Alignment.Center
            ) {
                val size = SwipeRefreshLayoutDefaults.progressIndicatorSize.size - (actProgress * SwipeRefreshLayoutDefaults.progressIndicatorSize.size.value).dp
                Box(
                    modifier = Modifier
                        .size(size.coerceAtLeast(SwipeRefreshLayoutDefaults.minimumCircleSize))
                        .clip(CircleShape)
                        .background(colors.progress)
                )
            }
        }
    }

}

/**
 * Contains default colors for the [SwipeRefreshLayout]
 */
object SwipeRefreshLayoutDefaults {

    val elevation = 8.dp

    val pullThreshold = 100.dp

    val circleOffset = 50.dp

    const val shrinkingAnimationStart = 0.25F

    val progressIndicatorSize = CircularProgressIndicatorSize(30.dp)

    val minimumCircleSize = 8.dp

    val padding = 6.dp

}

/**
 * Contains the colors needed for a [SwipeRefreshLayout]
 */
data class SwipeRefreshLayoutColors(

    val progress: Color,

    val secondaryProgress: Color,

    val circle: Color

)

/**
 * Constructs the default [SwipeRefreshLayoutColors]
 */
@Composable
fun swipeRefreshColors(
    progress: Color = OneUITheme.colors.seslLoadingProgressColor1,
    secondaryProgress: Color = OneUITheme.colors.seslLoadingProgressColor2,
    circle: Color = OneUITheme.colors.seslSwipeRefreshBackground
): SwipeRefreshLayoutColors = SwipeRefreshLayoutColors(
    progress = progress,
    secondaryProgress = secondaryProgress,
    circle = circle
)