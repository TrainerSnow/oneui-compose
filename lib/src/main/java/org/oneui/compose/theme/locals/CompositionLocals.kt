package org.oneui.compose.theme.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.oneui.compose.theme.color.LightColorTheme
import org.oneui.compose.theme.color.OneUIColorTheme
import org.oneui.compose.theme.dimensions.IDynamicDimensions
import org.oneui.compose.theme.type.RobotoTypographyTheme

internal val LocalColors: ProvidableCompositionLocal<OneUIColorTheme> =
    staticCompositionLocalOf { LightColorTheme.Instance }

internal val LocalDynamicDimensions: ProvidableCompositionLocal<IDynamicDimensions> =
    staticCompositionLocalOf {
        IDynamicDimensions.Default
    }

internal val LocalTypography = staticCompositionLocalOf {
    RobotoTypographyTheme.create(
        LightColorTheme.Instance,
        IDynamicDimensions.Default
    )
}

/**
 * CompositionLocal which holds the current background color.
 */
val LocalBackgroundColor = staticCompositionLocalOf {
    Color.Transparent
}

@Composable
fun ProvideBackgroundColor(
    color: Color,
    content: @Composable () -> Unit
) = CompositionLocalProvider(
    LocalBackgroundColor provides color,
    content = content
)