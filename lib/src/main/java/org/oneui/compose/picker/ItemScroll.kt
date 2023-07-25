package org.oneui.compose.picker

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import org.oneui.compose.theme.locals.LocalBackgroundColor
import org.oneui.compose.util.isEven

/**
 * Composable that arranges its' children vertically, and animates their scroll behaviour.
 *
 * TODO: Max-width calculation is **very** inefficient and should be handled differently.
 * TODO: Scrolling is not smooth, somehow the items lick in shortly before going to the next position. May be debug-mode related
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param state The [ItemScrollState] to control the component
 * @param onIndexChange The callback invoked when the index of the selected item changes. This is also called when the composable is still scrolling.
 * @param item The composable used to display an index that is currently not selected
 * @param activeItem The composable used to display the index that is currently selected
 */
@SuppressLint("AutoboxingStateValueProperty")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemScroll(
    modifier: Modifier = Modifier,
    state: ItemScrollState,
    onIndexChange: (Int) -> Unit,
    item: @Composable (index: Int) -> Unit,
    activeItem: @Composable (index: Int) -> Unit
) {
    val density = LocalDensity.current

    fun normalizeIndex(index: Int): Int = index % state.itemAmount
    fun middleIndexForFirst(firstVisibleItemIndex: Int): Int =
        normalizeIndex(firstVisibleItemIndex + state.visibleItemsCount / 2)

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state.listState)

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = with(LocalDensity.current) { itemHeightPixels.intValue.toDp() }

    LaunchedEffect(state.listState) {
        snapshotFlow { state.listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect {
                onIndexChange(middleIndexForFirst(it))
            }
    }

    Box(modifier = modifier) {
        val overlayAlpha =
            if (state.isScrolling) ItemScrollDefaults.overlayAlphaScroll else ItemScrollDefaults.overlayAlpha
        val overlayColor = LocalBackgroundColor.current.copy(
            alpha = overlayAlpha
        )

        var maxWidth by remember {
            mutableStateOf(0.dp)
        }

        //We render all the items ontop of each other, in order to calculate the maximum width
        Box(
            modifier = modifier
                .wrapContentWidth()
                .onGloballyPositioned {
                    with(density) {
                        maxWidth = it.size.width.toDp()
                    }
                }
                .graphicsLayer {
                    alpha = 0F
                }
        ) {
            (0 until state.itemAmount).forEach {
                item(it)
            }
        }

        LazyColumn(
            state = state.listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(maxWidth)
                .height(itemHeightDp * state.visibleItemsCount)
        ) {
            items(state.listSize) { _index ->
                val index = normalizeIndex(_index)
                Box(
                    modifier = Modifier
                        .onSizeChanged { size ->
                            itemHeightPixels.value = size.height
                        }
                        .padding(
                            vertical = ItemScrollDefaults.textSpacing / 2
                        )
                ) {
                    if (index == state.currentIndex) {
                        activeItem(normalizeIndex(index))
                    } else {
                        item(normalizeIndex(index))
                    }
                }
            }
        }

        ItemScrollOverlay(
            modifier = Modifier
                .width(maxWidth)
                .height(itemHeightDp * state.visibleItemsCount),
            color = overlayColor,
            windowHeight = itemHeightDp + ItemScrollDefaults.textSpacing / 2
        )
    }
}

@Composable
private fun ItemScrollOverlay(
    modifier: Modifier = Modifier,
    color: Color,
    windowHeight: Dp
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(color)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(windowHeight)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(color)
        )
    }
}


data class ItemScrollState(
    val itemAmount: Int,
    val initialIndex: Int = 0,
    val visibleItemsCount: Int
) {
    init {
        assert(!visibleItemsCount.isEven) { "visibleItemsCount must be an odd number!" }
    }

    val listSize = Int.MAX_VALUE

    val listState: LazyListState = LazyListState(
        firstVisibleItemIndex = listSize / 2 + (itemAmount - (listSize / 2 % itemAmount)) + initialIndex - visibleItemsCount / 2
    )

    var currentIndex by mutableIntStateOf(initialIndex)

    val isScrolling: Boolean
        get() = listState.isScrollInProgress

    suspend fun scrollToItem(index: Int) =
        listState.scrollToItem(index - visibleItemsCount / 2 + listSize / 2 + (itemAmount - (listSize / 2 % itemAmount)))
}

@Composable
fun rememberItemScrollState(
    initialIndex: Int = 0,
    itemAmount: Int,
    visibleItemsCount: Int = 3
) = remember {
    ItemScrollState(
        initialIndex = initialIndex,
        itemAmount = itemAmount,
        visibleItemsCount = visibleItemsCount
    )
}

object ItemScrollDefaults {

    val textSpacing = 20.dp

    val fontSize = 40.sp

    const val overlayAlphaScroll = 0.6F

    const val overlayAlpha = 0.9F

}