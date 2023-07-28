package org.oneui.compose.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToPrevious() = scrollToPage(currentPage - 1)

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToNext() = scrollToPage(currentPage + 1)