package org.oneui.compose.widgets.menu

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.theme.locals.ProvideBackgroundColor


/**
 * Composable for a oneui-style popup emnu for selecting different kind of items.
 * Can be used in spinners or other menus
 *
 * TODO: Exit animation is not playing due to implementation struggles
 *
 * @param modifier The [Modifier] to apply to the container
 * @param colors The [MenuColors] to apply
 * @param visible Whether the menu is currently visible
 * @param onDismissRequest Callback for when the menu is dismissed
 * @param properties The [PopupProperties] to apply
 * @param content The content to put inside the Menu. Preferably [SelectableMenuItem]s. Arranged along the y-Axis
 */
@Composable
fun PopupMenu(
    modifier: Modifier = Modifier,
    colors: MenuColors = menuColors(),
    visible: Boolean = true,
    onDismissRequest: () -> Unit,
    properties: PopupProperties = PopupProperties(
        focusable = true
    ),
    content: @Composable ColumnScope.() -> Unit
) {
    Popup(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        PopupContent(
            modifier = modifier,
            visible = visible,
            colors = colors
        ) {
            content()
        }
    }
}


/**
 * Overload that takes in a [PopupPositionProvider]
 * Can be used in spinners or other menus
 *
 * TODO: Exit animation is not playing due to implementation struggles
 *
 * @param modifier The [Modifier] to apply to the container
 * @param colors The [MenuColors] to apply
 * @param visible Whether the menu is currently visible
 * @param onDismissRequest Callback for when the menu is dismissed
 * @param properties The [PopupProperties] to apply
 * @param popupPositionProvider The [PopupPositionProvider] to position the popup
 * @param content The content to put inside the Menu. Preferably [SelectableMenuItem]s. Arranged along the y-Axis
 */
@Composable
fun PopupMenu(
    modifier: Modifier = Modifier,
    colors: MenuColors = menuColors(),
    visible: Boolean = true,
    onDismissRequest: () -> Unit,
    properties: PopupProperties = PopupProperties(
        focusable = true
    ),
    popupPositionProvider: PopupPositionProvider,
    content: @Composable ColumnScope.() -> Unit
) {
    Popup(
        onDismissRequest = onDismissRequest,
        properties = properties,
        popupPositionProvider = popupPositionProvider
    ) {
        PopupContent(
            modifier = modifier,
            visible = visible,
            colors = colors
        ) {
            content()
        }
    }
}

@Composable
private fun PopupContent(
    modifier: Modifier = Modifier,
    visible: Boolean,
    colors: MenuColors,
    content: @Composable ColumnScope.() -> Unit
) {
    val expandedState = remember { MutableTransitionState(false) }
    expandedState.targetState = visible

    val transition = updateTransition(expandedState, "Menu fade in/out")

    val size by transition.animateFloat(
        transitionSpec = {
            tween(MenuDefaults.animDuration)
        },
        label = "Menu fade in/out size"
    ) {
        if (it) 1F else MenuDefaults.animSizeMin
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(MenuDefaults.animDuration)
        },
        label = "Menu fade in/out size"
    ) {
        if (it) 1F else 0F
    }

    Box(
        modifier = modifier
            .alpha(alpha)
            .graphicsLayer {
                scaleX = size
                scaleY = size
            }
            .width(IntrinsicSize.Max)
            .padding(MenuDefaults.margin)
            .shadow(
                elevation = MenuDefaults.elevation * alpha,
                shape = MenuDefaults.shape
            )
            .background(
                colors.background,
                shape = MenuDefaults.shape
            )
            .clip(
                shape = MenuDefaults.shape
            )
            .border(
                width = with(LocalDensity.current) { MenuDefaults.strokeWidthPx.toDp() },
                color = colors.stroke,
                shape = MenuDefaults.shape
            ),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            ProvideBackgroundColor(colors.background) {
                content(this)
            }
        }
    }
}

/**
 * Contains the colors that define a [PopupMenu]
 */
data class MenuColors(

    val background: Color,

    val stroke: Color

)

/**
 * Constructs the default colors for a [PopupMenu]
 *
 * @param background The color used for the background of the menu
 * @param stroke The color used to outline the popup
 * @return The [MenuColors]
 */
@Composable
fun menuColors(
    background: Color = OneUITheme.colors.seslMenuPopupBackgroundColor,
    stroke: Color = OneUITheme.colors.seslMenuPopupBackgroundStrokeColor
): MenuColors = MenuColors(
    background = background,
    stroke = stroke
)

/**
 * Contains default values for a [PopupMenu]
 */
object MenuDefaults {

    val shape = RoundedCornerShape(
        size = 26.dp
    )

    val elevation = 5.dp

    val margin = 16.dp

    const val animDuration = 500

    const val animSizeMin = 0.75F

    const val strokeWidthPx = 2

}