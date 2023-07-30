package org.oneui.compose.widgets.text

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.oneui.compose.theme.OneUITheme
import org.oneui.compose.util.enabledAlpha


/**
 * Composable for a ouneui-style text-list-separator
 * TODO: Preview image
 *
 * @param modifier The modifier to be applied to the container
 * @param text The text to be displayed
 */
@Composable
fun TextSeparator(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true
) {
    Text(
        modifier = modifier
            .padding(TextSeparatorDefaults.padding)
            .enabledAlpha(enabled),
        text = text,
        style = OneUITheme.types.textSeparator
    )
}

/**
 * Contains default values for a [TextSeparator]
 */
object TextSeparatorDefaults {

    val padding = PaddingValues(
        top = 11.75.dp,
        bottom = 5.dp,
        start = 18.dp
    )

}