package org.oneui.compose.util

/**
 * @return Whether all of the values in [this] are contained in [c]
 */
fun <T> List<T>.allIn(c: Collection<T>): Boolean = all { it in c }