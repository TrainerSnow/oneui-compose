package org.oneui.compose.navigation.rail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.widgets.buttons.IconButton
import dev.oneuiproject.oneui.R as IconR

/**
 * Overload for [NavigationRailHeader] which only takes in callbacks and constructs the buttons which callbacks were provided for.
 *
 * @param modifier The [Modifier] to apply
 * @param onNavigateClick The callback invoked when the navigation button is clicked
 * @param onSettingsClick The callback invoked when the settings button is clicked
 */
@Composable
fun NavigationRailHeader(
    modifier: Modifier = Modifier,
    onNavigateClick: (() -> Unit)? = null,
    onSettingsClick: (() -> Unit)? = null
) = NavigationRailHeader(
    modifier = modifier,
    navigationIconButton = {
        onNavigateClick?.let { click ->
            IconButton(
                icon = Icon.Resource(IconR.drawable.ic_oui_drawer),
                onClick = onNavigateClick
            )
        }
    },
    settingsIconButton = {
        onSettingsClick?.let { click ->
            IconButton(
                icon = Icon.Resource(IconR.drawable.ic_oui_settings_outline),
                onClick = onSettingsClick
            )
        }
    },
)

/**
 * Composable for a oneui-style Navigation drawer header. This displays (if configured) one navigation icon (always visible) and a settings icon (visible when opened)
 *
 * @param modifier The [Modifier] to apply
 * @param padding The [PaddingValues] to apply
 * @param navigationIconButton The optional button to be shown on the left-hand side, preferably an [IconButton]
 * @param settingsIconButton The optional button to be shown on the right-hand side, preferably an [IconButton]
 */
@Composable
fun NavigationRailHeader(
    modifier: Modifier = Modifier,
    padding: PaddingValues = NavigationRailHeaderDefaults.padding,
    navigationIconButton: (@Composable () -> Unit)? = null,
    settingsIconButton: (@Composable () -> Unit)? = null
) {
    if (navigationIconButton == null && settingsIconButton == null) return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationIconButton?.let { icon ->
            Box(
                modifier = Modifier
                    .size(NavigationRailHeaderDefaults.iconButtonSize),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides OneUITheme.colors.seslPrimaryTextColor
                ) {
                    icon()
                }
            }
        }
        Spacer(
            modifier = Modifier
                .weight(1F)
        )
        settingsIconButton?.let { icon ->
            Box(
                modifier = Modifier
                    .size(NavigationRailHeaderDefaults.iconButtonSize),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides OneUITheme.colors.seslSecondaryTextColor
                ) {
                    icon()
                }
            }
        }
    }
}


/**
 * Contains default values for a [NavigationRailHeader]
 */
object NavigationRailHeaderDefaults {

    val padding = PaddingValues(
        vertical = 8.dp
    )

    val iconButtonSize = DpSize(46.dp, 46.dp)

}

@Preview
@Composable
private fun NavigationRailHeaderPPreview() {
    NavigationRail(
        modifier = Modifier.fillMaxSize(),
        railHeader = {
            NavigationRailHeader(
                onSettingsClick = { },
                onNavigateClick = { }
            )
        },
        railContent = { },
        content = { }
    )
}