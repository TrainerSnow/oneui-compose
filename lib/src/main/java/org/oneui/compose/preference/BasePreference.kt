package org.oneui.compose.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import org.oneui.compose.base.Icon
import org.oneui.compose.base.IconView
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha


/**
 * Base composable for a oneui-style preference. This is not supposed to be used standalone,
 *     but rather as a base for other preferences.
 *
 * @param modifier The [Modifier] to be applied to the container
 * @param icon The icon to be displayed in front of the preference
 * @param title The title of the preference, preferably a Text-composable
 * @param summary The summary of the preference, preferably a Text-composable
 * @param onClick The callback invoked when this preference is clicked
 * @param interactionSource The [MutableInteractionSource] that is used
 * @param colors The [BasePreferenceColors] that are used
 * @param contentLocation Changes where the [content] is placed.
 * @param content The content put at the end of the preference
 */
@Composable
fun BasePreference(
    modifier: Modifier = Modifier,
    icon: Icon? = null,
    title: @Composable ColumnScope.() -> Unit,
    summary: (@Composable ColumnScope.() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: BasePreferenceColors = basePreferenceColors(),
    enabled: Boolean = true,
    contentLocation: ContentLocation = ContentLocation.End,
    content: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                enabled = enabled,
                onClick = { onClick?.let { it() } },
                indication = rememberRipple(
                    color = colors.ripple
                ),
                role = Role.DropdownList
            )
            .padding(BasePreferenceDefaults.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        icon?.let {
            IconView(
                icon = icon,
                modifier = Modifier
                    .size(BasePreferenceDefaults.iconSize)
            )
        }
        Column(
            modifier = Modifier
                .weight(1F)
        ) {
            ProvideTextStyle(OneUITheme.types.preferenceTitle.enabledAlpha(enabled)) {
                title(this)
            }
            ProvideTextStyle(OneUITheme.types.preferenceSummary.enabledAlpha(enabled)) {
                summary?.let {
                    it(this)
                }
            }
            if (contentLocation == ContentLocation.Bottom) {
                content?.let { it() }
            }
        }
        if (contentLocation == ContentLocation.End) {
            content?.let { it() }
        }
    }
}

/**
 * Collection of ways the content can be placed in a [BasePreference]
 */
enum class ContentLocation {
    End,
    Bottom
}


/**
 * Contains default values for a [BasePreference]
 */
object BasePreferenceDefaults {

    //Padding from RoundedCornerBox is added here to click animation is shown for whole item without padding.
    val padding = PaddingValues(
        start = 15.dp + 12.dp,
        end = 6.dp + 12.dp,
        top = 6.dp + 6.dp,
        bottom = 6.dp + 6.dp
    )

    val iconSize = DpSize(
        width = 48.dp,
        height = 48.dp
    )

}

/**
 * Contains colors for a [BasePreference]
 */
data class BasePreferenceColors(
    val ripple: Color
)

/**
 * Constructs the default colors for the [BasePreferenceColors]
 *
 * @param ripple The ripple color used for the click animation
 */
@Composable
fun basePreferenceColors(
    ripple: Color = OneUITheme.colors.seslListRippleColor
): BasePreferenceColors = BasePreferenceColors(
    ripple = ripple
)