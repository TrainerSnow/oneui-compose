package org.oneui.compose.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.oneui.compose.R
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.base.iconColors
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.OneUIPreview
import org.oneui.compose.util.enabledAlpha


/**
 * Composable for a oneui-style searchview. This is to be used at the top of a search-screen
 *
 * @param modifier The [Modifier] to apply to the container
 * @param query The search query entered by the user
 * @param hint The hint to show when [query] is empty
 * @param onQueryChange The callback invoked when the user input is adjusted
 * @param backButton The composable for the back button
 * @param closeButton The composable for the button that removes the input
 * @param voiceButton The composable for the button that activates voice input
 * @param extraButton The composable for an extra button
 */
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    query: String,
    hint: String = stringResource(R.string.abc_search_hint),
    onQueryChange: (String) -> Unit,
    enabled: Boolean = true,
    backButton: (@Composable () -> Unit)? = null,
    closeButton: (@Composable () -> Unit)? = null,
    voiceButton: (@Composable () -> Unit)? = null,
    extraButton: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier
            .height(SearchViewDefaults.height)
            .padding(SearchViewDefaults.margin)
            .fillMaxWidth()
            .enabledAlpha(enabled)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            backButton?.let {
                it()
                Spacer(
                    modifier = Modifier
                        .width(SearchViewDefaults.backButtonSpacing)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1F)
                    .widthIn(
                        min = SearchViewDefaults.editTextMinWidth
                    )
                    .padding(SearchViewDefaults.editTextPadding)
            ) {
                EditText(
                    modifier = Modifier
                        .fillMaxSize(),
                    style = OneUITheme.types.searchEdit,
                    hintStyle = OneUITheme.types.searchHint,
                    value = query,
                    hint = hint,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    enabled = enabled
                )
            }

            if (query.isNotEmpty()) {
                closeButton?.let {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(SearchViewDefaults.buttonPadding)
                    ) {
                        closeButton()
                    }
                }
            } else {
                voiceButton?.let {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(SearchViewDefaults.buttonPadding)
                    ) {
                        voiceButton()
                    }
                }
            }

            extraButton?.let {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(SearchViewDefaults.buttonPadding)
                ) {
                    extraButton()
                }
            }
        }
    }
}


/**
 * Overload for [SearchView] that constructs the buttons itself, and takes the callback
 *
 * @param modifier The [Modifier] to apply to the container
 * @param query The search query entered by the user
 * @param hint The hint to show when [query] is empty
 * @param onQueryChange The callback invoked when the user input is adjusted
 * @param onBackClick The callback invoked when the back button is clicked
 * @param onVoiceClick The callback invoked when the voice button is clicked
 * @param extraButton The composable for an optional additional button
 */
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    query: String,
    hint: String = stringResource(R.string.abc_search_hint),
    enabled: Boolean = true,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onVoiceClick: () -> Unit,
    extraButton: (@Composable () -> Unit)? = null
) {
    SearchView(
        modifier = modifier,
        query = query,
        hint = hint,
        onQueryChange = onQueryChange,
        enabled = enabled,
        backButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_back),
                onClick = onBackClick
            )
        },
        closeButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_close),
                onClick = { onQueryChange("") }
            )
        },
        voiceButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_voice),
                onClick = onVoiceClick
            )
        },
        extraButton = extraButton
    )
}


/**
 * Composable for an icon button to use inside of a [SearchView]
 *
 * @param modifier The [Modifier] to apply to the container
 * @param icon Tje [Icon] to use in the icon button
 * @param onClick The callback invoked when the button is clicked
 * @param colors The [SearchViewButtonColors] to apply
 * @param interactionSource The [MutableInteractionSource]
 * @param contentDescription The optional content descripton for the button
 */
@Composable
fun SearchViewButton(
    modifier: Modifier = Modifier,
    icon: Icon,
    onClick: () -> Unit,
    colors: SearchViewButtonColors = searchViewButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentDescription: String? = null
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = colors.ripple,
                    radius = SearchViewDefaults.buttonRippleRadius,
                    bounded = false
                ),
                role = Role.Image,
                onClick = onClick
            )
    ) {
        IconView(
            modifier = Modifier
                .size(SearchViewDefaults.buttonSize),
            icon = icon,
            colors = iconColors(
                tint = colors.icon
            ),
            contentDescription = contentDescription
        )
    }
}

/**
 * Contains the colors needed for a [SearchViewButton]
 */
data class SearchViewButtonColors(

    val ripple: Color,

    val icon: Color

)

/**
 * Constructs the default [SearchViewButtonColors]
 *
 * @param ripple The color used for the onclick animation
 * @param icon The color used for the icon
 */
@Composable
fun searchViewButtonColors(
    ripple: Color = OneUITheme.colors.seslRippleColor,
    icon: Color = OneUITheme.colors.seslSearchViewIconColor
): SearchViewButtonColors = SearchViewButtonColors(
    ripple = ripple,
    icon = icon
)

/**
 * Contains default values for a [SearchView] and [SearchViewButton]
 */
object SearchViewDefaults {

    val height = 44.dp

    val margin = PaddingValues(
        top = 10.dp,
        bottom = 10.dp,
        start = 20.dp,
        end = 10.dp
    )

    val backButtonSpacing = 12.dp

    val editTextMinWidth = 160.dp

    val editTextPadding = PaddingValues(
        start = 4.dp
    )

    val buttonPadding = PaddingValues(
        horizontal = 10.dp
    )

    val buttonRippleRadius = 22.dp

    val buttonSize = DpSize(
        width = 24.dp,
        height = 24.dp
    )

}

/**
 * Preview for a [SearchView] with no user input
 */
@Preview
@Composable
private fun SearchView_Hint() = OneUIPreview("SearchView") {
    SearchView(
        query = "",
        onQueryChange = { },
        backButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_back),
                onClick = { }
            )
        },
        closeButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_close),
                onClick = { }
            )
        },
        voiceButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_voice),
                onClick = { }
            )
        },
    )
}

/**
 * Preview for a [SearchView] with user input
 *
 */
@Preview
@Composable
private fun SearchView_NoHint() = OneUIPreview("SearchView") {
    SearchView(
        query = "SearchQuery",
        onQueryChange = { },
        backButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_back),
                onClick = { }
            )
        },
        closeButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_close),
                onClick = { }
            )
        },
        voiceButton = {
            SearchViewButton(
                icon = Icon.Resource(dev.oneuiproject.oneui.R.drawable.ic_oui_voice),
                onClick = { }
            )
        },
    )
}
