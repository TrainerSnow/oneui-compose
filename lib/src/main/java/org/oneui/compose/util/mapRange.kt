package org.oneui.compose.util

/**
 * Maps a value in a range to the equivalent of another range
 *
 * @param value The value
 * @param origStart The start of the original range
 * @param origEnd The end of the original range
 * @param targetStart The start of the targeted range
 * @param targetEnd The end of the targeted range
 * @return The value mapped to the target range
 */
fun mapRange(
    value: Float,
    origStart: Float,
    origEnd: Float,
    targetStart: Float,
    targetEnd: Float
): Float = (value - origStart) / (origEnd - origStart) * (targetEnd - targetStart) + targetStart