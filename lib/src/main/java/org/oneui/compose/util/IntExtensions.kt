package org.oneui.compose.util

/**
 * @return true is is even, false if is odd
 */
val Int.isEven
    get() = this % 2 == 0

/**
 * Creates a list that starts at [this] going to [other], with intervals of [step]
 */
fun Int.steppedRangeTo(other: Int, step: Int): List<Int> {
    val list = emptyList<Int>().toMutableList()
    var cur = this

    while (cur <= other) {
        list.add(cur)
        cur += step
    }

    return list
}