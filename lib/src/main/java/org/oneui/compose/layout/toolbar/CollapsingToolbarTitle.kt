package org.oneui.compose.layout.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import org.oneui.compose.theme.OneUITheme

/**
 * The title- and subtitle section that gets visible when swiping down on a CllapsingToolbarlayout.
 * TODO: Add preview image
 *
 * @param modifier The modifier to be applied to the Contained
 * @param title The title composable, preferably a [Text]
 * @param subtitle The subtitle composable, preferably a [Text]
 * @param titleStyle The style for the title
 * @param subtitleStyle The style for the subtitle
 */
@Composable
fun CollapsingToolbarTitle(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
    titleStyle: TextStyle = OneUITheme.types.appbarTitleExtended,
    subtitleStyle: TextStyle = OneUITheme.types.appbarSubtitle,
    textAlpha: Float = 1F
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProvideTextStyle(
                titleStyle.copy(
                    color = titleStyle.color.copy(
                        alpha = textAlpha
                    )
                )
            ) {
                title()
            }
            subtitle?.let {
                ProvideTextStyle(
                    subtitleStyle.copy(
                        color = titleStyle.color.copy(
                            alpha = textAlpha
                        )
                    )
                ) {
                    it()
                }
            }
        }
    }
}