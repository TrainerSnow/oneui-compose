package org.oneui.compose.layout.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import org.oneui.compose.theme.OneUITheme

/**
 * Contains the colors that define a [OUIAppBar]
 */
data class AppBarColors(

    val background: Color
)

/**
 * Constructs the default colors for a [OUIAppBar]
 *
 * @param background
 * @return
 */
@Composable
fun appBarColors(
    background: Color = Color.Transparent,
): AppBarColors = AppBarColors(
    background = background
)

/**
 * Composable for a oui-style Topappbar
 * TODO: Add preview image
 *
 * @param modifier The modifier to be applied to the container
 * @param title The title of the appbar, preferably a [Text]
 * @param titleTextAlpha The alpha value of the title textstyles color
 * @param titleTextStyle The textstyle of the title
 * @param colors The [AppBarColors]
 * @param startAction The action icon on the start side
 * @param actions The other actions on the end side
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OUIAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    titleTextAlpha: Float = 1F,
    titleTextStyle: TextStyle = OneUITheme.types.appbarTitleCollapsed.copy(
        color = OneUITheme.types.appbarTitleCollapsed.color.copy(
            alpha = titleTextAlpha
        )
    ),
    colors: AppBarColors = appBarColors(),
    startAction: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null
) = TopAppBar(
    modifier = modifier
        .fillMaxWidth()
        .background(
            color = colors.background
        ),
    title = {
        ProvideTextStyle(titleTextStyle) {
            title()
        }
    },
    navigationIcon = {
        startAction?.let { it() }
    },
    actions = { actions?.let { it(this) } },
    colors = topAppBarColors(
        containerColor = colors.background,
        scrolledContainerColor = colors.background,
        navigationIconContentColor = titleTextStyle.color,
        titleContentColor = titleTextStyle.color,
        actionIconContentColor = titleTextStyle.color
    )
)