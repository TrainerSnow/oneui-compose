package org.oneui.compose.util

val String.isInt: Boolean
    get() {
        return try {
            toInt()
            true
        } catch (_: Exception) {
            false
        }
    }