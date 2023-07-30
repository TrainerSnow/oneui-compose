package org.oneui.compose.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

private const val ENABLED_ALPHA = 1F
private const val DISABLED_ALPHA = 0.4F

fun Modifier.enabledAlpha(enabled: Boolean = true): Modifier = this.then(
    Modifier
        .alpha(if (enabled) ENABLED_ALPHA else DISABLED_ALPHA)
)

fun Color.enabledAlpha(enabled: Boolean = true): Color = if(enabled) this else copy(
    alpha = alpha * DISABLED_ALPHA
)

fun TextStyle.enabledAlpha(enabled: Boolean = true): TextStyle = copy(
    color = color.enabledAlpha(enabled)
)