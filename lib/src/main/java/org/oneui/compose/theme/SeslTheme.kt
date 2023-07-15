package org.oneui.compose.theme

import android.util.Log.d
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import org.oneui.compose.theme.color.ISeslColorTheme
import org.oneui.compose.theme.dimensions.DynamicDimensionsImpl
import org.oneui.compose.theme.dimensions.IDynamicDimensions
import org.oneui.compose.theme.locals.LocalColors
import org.oneui.compose.theme.locals.LocalDynamicDimensions
import org.oneui.compose.theme.locals.LocalTypography
import org.oneui.compose.theme.type.ISeslTypographyTheme
import org.oneui.compose.theme.type.RobotoSeslTypographyTheme

@Composable
fun SeslTheme(
    colorTheme: ISeslColorTheme = ISeslColorTheme.getTheme(isSystemInDarkTheme()),
    dynamicDimensions: IDynamicDimensions = DynamicDimensionsImpl(
        context = LocalContext.current
    ),
    typeTheme: ISeslTypographyTheme = RobotoSeslTypographyTheme.create(
        colorTheme,
        dynamicDimensions
    ),
    content: @Composable () -> Unit
) {
    d("SeslTheme", "For label size got ${dynamicDimensions.switchBarLabelTextSize}")
    CompositionLocalProvider(
        LocalColors provides colorTheme,
        LocalTypography provides typeTheme,
        LocalDynamicDimensions provides dynamicDimensions
    ) {
        content()
    }
}

object SeslTheme {

    val colors: ISeslColorTheme
        @Composable get() = LocalColors.current

    val types: ISeslTypographyTheme
        @Composable get() = LocalTypography.current

    val dimensions: IDynamicDimensions
        @Composable get() = LocalDynamicDimensions.current

}