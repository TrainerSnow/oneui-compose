package org.oneui.compose.progress

import androidx.annotation.FloatRange

/**
 * Defines the appearance and function of a [ProgressIndicator]
 */
sealed class ProgressIndicatorType {

    /**
     * Circular indicator for showing exact, quantifiable progress.
     *
     * @property size The [CircularProgressIndicatorSize]
     * @property progress The progress of the indicator
     */
    data class CircularDeterminate(
        val size: CircularProgressIndicatorSize,
        @FloatRange(
            from = 0.0,
            to = 1.0,
            toInclusive = true,
            fromInclusive = true
        )
        val progress: Float
    ) : ProgressIndicatorType()


    /**
     * Circular indicator for showing unknown progress.
     *
     * @property size The [CircularProgressIndicatorSize]
     */
    data class CircularIndeterminate(
        val size: CircularProgressIndicatorSize
    ) : ProgressIndicatorType()


    /**
     * Horizontal indicator for showing exact, quantifiable progress.
     *
     * @property progress The progress of the indicator
     */
    data class HorizontalDeterminate(
        @FloatRange(
            from = 0.0,
            to = 1.0,
            toInclusive = true,
            fromInclusive = true
        )
        val progress: Float
    ) : ProgressIndicatorType()

    /**
     * Horizontal indicator for showing unknwon progress.
     */
    object HorizontalIndeterminate : ProgressIndicatorType()

}