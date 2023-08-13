package org.oneui.compose.util

/**
 * Different positions an item can take in a list of items. Useful for different styling
 *  depending on the list position
 */
enum class ListPosition {

    /**
     * First list entry
     */
    First,

    /**
     * Entry in the middle of the list
     */
    Middle,

    /**
     * Last list entry
     */
    Last,

    /**
     * The only entry in a list
     */
    Single

    ;

    companion object {

        fun <T> get(item: T, list: Collection<T>): ListPosition {
            require(item in list) { "The item $item is not inside the list $list" }

            return if (list.size == 1) Single
            else when (list.indexOf(item)) {
                0 -> First
                list.indices.last -> Last
                else -> Middle
            }
        }

    }

}