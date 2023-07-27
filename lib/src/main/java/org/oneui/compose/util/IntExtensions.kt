package org.oneui.compose.util

val Int.isEven
    get() = this % 2 == 0

fun Int.steppedRangeTo(other: Int, step: Int): List<Int> {
    val list = emptyList<Int>().toMutableList()
    var cur = this

    while (cur <= other) {
        list.add(cur)
        cur += step
    }

    return list
}