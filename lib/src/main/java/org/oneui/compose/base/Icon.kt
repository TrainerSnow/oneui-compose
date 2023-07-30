package org.oneui.compose.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import org.oneui.compose.theme.OneUITheme

sealed class Icon {

    data class Resource(
        @DrawableRes val resId: Int
    ) : Icon()

    data class Vector(
        val vector: ImageVector
    ) : Icon()

}


/**
 * Composable for displaying an [Icon]
 *
 * @param modifier The modifier to apply to the container
 * @param colors The [IconColors]
 * @param icon The [IconView] to display
 * @param contentDescription The content-description for accessibility
 */
@Composable
fun IconView(
    modifier: Modifier = Modifier,
    colors: IconColors = iconColors(),
    icon: Icon,
    contentDescription: String? = null
) {
    when (icon) {
        is Icon.Resource -> {
            Image(
                modifier = modifier,
                painter = painterResource(icon.resId),
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(
                    color = colors.tint
                )
            )
        }

        is Icon.Vector -> Image(
            modifier = modifier,
            imageVector = icon.vector,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(
                color = colors.tint
            )
        )
    }
}

/**
 * Contains the colors that define an [IconView]
 */
data class IconColors(

    val tint: Color

)

/**
 * Creates the default [IconColors]
 */
@Composable
fun iconColors(
    tint: Color = OneUITheme.colors.seslPrimaryTextColor
): IconColors = IconColors(
    tint = tint
)