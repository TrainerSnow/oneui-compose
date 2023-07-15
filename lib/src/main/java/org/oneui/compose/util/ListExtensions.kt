package org.oneui.compose.util

fun <T> List<T>.allIn(c: Collection<T>): Boolean = all { it in c }