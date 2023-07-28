package org.oneui.compose.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.animateScrollToPrevious() = animateScrollToPage(currentPage - 1)

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.animateScrollToNext() = animateScrollToPage(currentPage + 1)