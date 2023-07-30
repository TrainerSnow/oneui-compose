package org.oneui.compose.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.oneui.compose.preference.internal.RelativeCardLink
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha


/**
 * Composable for a oneui-style preference link card. This should be shown at the bottom of
 *     a preference screen and link to similar preferences to help the user navigate
 *
 * @param modifier The [Modifier] applied to the container
 * @param title The title of the card
 * @param colors The [RelativeCardColors] to apply
 * @param links The links that will be shown vertically in a [Column]. Preferably [RelativeCardLink]s
 */
@Composable
fun RelativeCard(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    colors: RelativeCardColors = relativeCardColors(),
    enabled: Boolean = true,
    links: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = colors.background.enabledAlpha(enabled),
                shape = RelativeCardDefaults.shape
            )
            .clip(RelativeCardDefaults.shape)
            .padding(RelativeCardDefaults.padding)
    ) {
        Column {
            ProvideTextStyle(OneUITheme.types.relativeCardTitle.enabledAlpha(enabled)) {
                title()
            }
            Spacer(
                modifier = Modifier
                    .height(RelativeCardDefaults.titleLinksSpacing)
            )
            Column {
                links()
            }
        }
    }
}


/**
 * Contains default values for a [RelativeCard]
 */
object RelativeCardDefaults {

    val padding = PaddingValues(
        horizontal = 24.dp,
        vertical = 20.dp
    )

    val shape = RoundedCornerShape(
        size = 26.dp
    )

    val titleLinksSpacing = 10.dp

}


/**
 * COntains the colors needed for a [RelativeCard]
 */
data class RelativeCardColors(

    val background: Color

)


/**
 * Constructs the default [RelativeCardColors]
 *
 * @param background The background color of the card
 */
@Composable
fun relativeCardColors(
    background: Color = OneUITheme.colors.seslPreferenceRelativeCardBackground
): RelativeCardColors = RelativeCardColors(
    background = background
)