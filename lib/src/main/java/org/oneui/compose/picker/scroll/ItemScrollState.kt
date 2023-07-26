package org.oneui.compose.picker.scroll

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable

interface ItemScrollState {

    /**
     * The total amount of different items to show. Not to be confused with [listSize]
     */
    val itemAmount: Int

    /**
     * The initial index that is selected
     */
    val initialIndex: Int

    /**
     * The amount of items that are visible simultaneously
     */
    val visibleItemsCount: Int

    /**
     * The amount of items that will be portrayed inside the Item Scroll
     */
    val listSize: Int

    /**
     * The [LazyListState], for further control
     */
    val listState: LazyListState

    /**
     * The index that is currently selected
     */
    var currentIndex: Int

    /**
     * Whether the component is currently scrolling
     */
    val isScrolling: Boolean

    /**
     * Scroll to the item at [index]
     */
    suspend fun scrollToItem(index: Int)

}

@Composable
fun rememberItemScrollState(
    itemAmount: Int,
    visibleItemsCount: Int = 3,
    initialIndex: Int = 0,
    repeating: Boolean = true
): ItemScrollState = if (repeating) RepeatingItemScrollState(
    itemAmount = itemAmount,
    initialIndex = initialIndex,
    visibleItemsCount = visibleItemsCount
) else SimpleItemScrollState(
    itemAmount = itemAmount,
    initialIndex = initialIndex,
    visibleItemsCount = visibleItemsCount
)