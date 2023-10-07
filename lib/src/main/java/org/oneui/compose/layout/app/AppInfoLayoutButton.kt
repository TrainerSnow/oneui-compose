package org.oneui.compose.layout.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha


/**
 * Composable for a oneui-style button to be used on a [AppInfoLayout] to trigger a contextual action, such as checking for new versions
 *
 * @param modifier The [Modifier] to apply
 * @param colors The [AppInfoLayoutButtonColors] to apply
 * @param onClick The callback invoked when the button is clicked
 * @param enabled Whether this button is enabled or not
 * @param interactionSource The [MutableInteractionSource] to control the button
 * @param label The label to show in the center of the button. Preferably a [Text]
 */
@Composable
fun AppInfoLayoutButton(
    modifier: Modifier = Modifier,
    colors: AppInfoLayoutButtonColors = appInfoLayoutButtonColors(),
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    label: @Composable () -> Unit
) {
    val width = LocalContext.current.resources.run {
        displayMetrics.widthPixels / configuration.orientation
    } * 0.61F
    val widthDp = with(LocalDensity.current) { width.toDp() }

    Box(
        modifier = modifier
            .padding(AppInfoLayoutButtonDefaults.margin),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .heightIn(min = AppInfoLayoutButtonDefaults.minHeight)
                .width(widthDp)
                .clip(AppInfoLayoutButtonDefaults.shape)
                .background(
                    colors.background.enabledAlpha(enabled),
                    AppInfoLayoutButtonDefaults.shape
                )
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                    interactionSource = interactionSource,
                    role = Role.Button,
                    indication = rememberRipple(
                        color = colors.ripple
                    )
                )
                .padding(AppInfoLayoutButtonDefaults.padding),
            contentAlignment = Alignment.Center
        ) {
            ProvideTextStyle(
                OneUITheme.types.appInfoLayoutButton
                    .copy(color = colors.labelTextColor)
                    .enabledAlpha(enabled)
            ) {
                label()
            }
        }
    }
}

/**
 * Contains default values for a [AppInfoLayoutButton]
 */
object AppInfoLayoutButtonDefaults {

    val minHeight = 44.dp

    val margin = PaddingValues(
        top = 15.dp
    )

    val padding = PaddingValues(
        horizontal = 16.dp,
        vertical = 8.dp
    )

    val shape = RoundedCornerShape(22.dp)

}

/**
 * Contains the colors used in an [AppInfoLayoutButton]
 */
data class AppInfoLayoutButtonColors(

    val background: Color,

    val ripple: Color,

    val labelTextColor: Color

)


/**
 * Constructs the default [AppInfoLayoutButtonColors]
 *
 * @param background The button background color
 * @param ripple The ripple color for the onclick animation
 */
@Composable
fun appInfoLayoutButtonColors(
    background: Color = OneUITheme.colors.appInfoLayoutButtonBackground,
    ripple: Color = OneUITheme.colors.seslRippleColor,
    labelTextColor: Color = OneUITheme.colors.appInfoLayoutButtonText
) = AppInfoLayoutButtonColors(background, ripple, labelTextColor)

@Preview
@Composable
private fun AppInfoLayoutButtonPreview() {
    AppInfoLayoutButton(
        onClick = { }
    ) {
        Text(
            text = "Check for new version"
        )
    }
}