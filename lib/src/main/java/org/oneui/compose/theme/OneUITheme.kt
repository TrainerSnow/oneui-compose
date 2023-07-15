package org.oneui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import org.oneui.compose.theme.color.OneUIColorTheme
import org.oneui.compose.theme.dimensions.DynamicDimensionsImpl
import org.oneui.compose.theme.dimensions.IDynamicDimensions
import org.oneui.compose.theme.locals.LocalColors
import org.oneui.compose.theme.locals.LocalDynamicDimensions
import org.oneui.compose.theme.locals.LocalTypography
import org.oneui.compose.theme.type.OneUITypographyTheme
import org.oneui.compose.theme.type.RobotoSeslTypographyTheme

@Composable
fun SeslTheme(
    colorTheme: OneUIColorTheme = OneUIColorTheme.getTheme(isSystemInDarkTheme()),
    dynamicDimensions: IDynamicDimensions = DynamicDimensionsImpl(
        context = LocalContext.current
    ),
    typeTheme: OneUITypographyTheme = RobotoSeslTypographyTheme.create(
        colorTheme,
        dynamicDimensions
    ),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides colorTheme,
        LocalTypography provides typeTheme,
        LocalDynamicDimensions provides dynamicDimensions
    ) {
        content()
    }
}

object OneUITheme {

    val colors: OneUIColorTheme
        @Composable get() = LocalColors.current

    val types: OneUITypographyTheme
        @Composable get() = LocalTypography.current

    val dimensions: IDynamicDimensions
        @Composable get() = LocalDynamicDimensions.current

}