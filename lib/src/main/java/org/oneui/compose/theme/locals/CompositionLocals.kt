package org.oneui.compose.theme.locals

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import org.oneui.compose.theme.color.OneUIColorTheme
import org.oneui.compose.theme.color.LightSeslColorTheme
import org.oneui.compose.theme.dimensions.IDynamicDimensions
import org.oneui.compose.theme.type.RobotoTypographyTheme

internal val LocalColors: ProvidableCompositionLocal<OneUIColorTheme> =
    staticCompositionLocalOf { LightSeslColorTheme.Instance }

internal val LocalDynamicDimensions: ProvidableCompositionLocal<IDynamicDimensions> =
    staticCompositionLocalOf {
        IDynamicDimensions.Default
    }

internal val LocalTypography = staticCompositionLocalOf {
    RobotoTypographyTheme.create(
        LightSeslColorTheme.Instance,
        IDynamicDimensions.Default
    )
}