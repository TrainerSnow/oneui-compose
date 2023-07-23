package org.oneui.compose.progress

sealed class ProgressIndicatorType {

    data class CircularDeterminate(
        val size: CircularProgressIndicatorSize,
        val progress: Float
    ) : ProgressIndicatorType()

    data class CircularIndeterminate(
        val size: CircularProgressIndicatorSize
    ) : ProgressIndicatorType()

    data class HorizontalDeterminate(
        val progress: Float
    ) : ProgressIndicatorType()

    object HorizontalIndeterminate : ProgressIndicatorType()

}