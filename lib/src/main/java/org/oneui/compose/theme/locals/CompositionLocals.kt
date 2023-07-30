package org.oneui.compose.theme.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.oneui.compose.theme.color.LightColorTheme
import org.oneui.compose.theme.color.OneUIColorTheme
import org.oneui.compose.theme.dimensions.IDynamicDimensions
import org.oneui.compose.theme.type.OneUITypographyTheme
import org.oneui.compose.theme.type.RobotoTypographyTheme

/**
 * Contains the currently applicable [OneUIColorTheme]
 */
internal val LocalColors: ProvidableCompositionLocal<OneUIColorTheme> =
    staticCompositionLocalOf { LightColorTheme }

/**
 * Contains the currently applicable [IDynamicDimensions]
 */
internal val LocalDynamicDimensions: ProvidableCompositionLocal<IDynamicDimensions> =
    staticCompositionLocalOf {
        IDynamicDimensions.Default
    }

/**
 * Contains the currently applicable [OneUITypographyTheme]
 */
internal val LocalTypography = staticCompositionLocalOf {
    RobotoTypographyTheme.create(
        LightColorTheme,
        IDynamicDimensions.Default
    )
}

/**
 * Contains the currently applicable background [Color]. Updated depending on the currently composed component.
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